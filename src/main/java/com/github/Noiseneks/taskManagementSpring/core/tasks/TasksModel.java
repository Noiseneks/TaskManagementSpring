package com.github.Noiseneks.taskManagementSpring.core.tasks;

import com.github.Noiseneks.taskManagementSpring.core.repository.CommentRepository;
import com.github.Noiseneks.taskManagementSpring.core.repository.TaskRepository;
import com.github.Noiseneks.taskManagementSpring.core.repository.UserRepository;
import com.github.Noiseneks.taskManagementSpring.domain.dtos.CommentDto;
import com.github.Noiseneks.taskManagementSpring.domain.dtos.IdDto;
import com.github.Noiseneks.taskManagementSpring.domain.dtos.TaskDto;
import com.github.Noiseneks.taskManagementSpring.domain.entity.Comment;
import com.github.Noiseneks.taskManagementSpring.domain.entity.Task;
import com.github.Noiseneks.taskManagementSpring.domain.entity.User;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

@Component
public class TasksModel {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public TasksModel(TaskRepository taskRepository, UserRepository userRepository, CommentRepository commentRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }


    public Task createTask(TaskDto taskDto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.getByUsername(authentication.getName());

        Task task = new Task()
                .setAuthorId(user.getId())
                .setName(taskDto.getName())
                .setDescription(taskDto.getDescription());

        if (StringUtils.isNotEmpty(taskDto.getPriority())) {
            task.setPriority(castPriority(taskDto.getPriority()));
        }

        if (StringUtils.isNotEmpty(taskDto.getStatus())) {
            task.setStatus(castStatus(taskDto.getStatus()));
        }

        return taskRepository.saveAndFlush(task);
    }

    public boolean checkIfTaskExists(long id) {
        return taskRepository.getTaskById(id) != null;
    }

    public void deleteTask(TaskDto taskDto) {
        Long id = taskDto.getId();

        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Task id can't be null");
        }

        if (!checkIfTaskExists(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Task with given id doesn't exist");
        }

        taskRepository.deleteTaskById(id);
    }

    public Task updateTask(TaskDto taskDto) {
        Long id = taskDto.getId();

        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Task id can't be null");
        }

        Task task = taskRepository.getTaskById(id);

        if (task == null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Task with given id doesn't exist");
        }

        if (taskDto.getName() != null) {
            task.setName(taskDto.getName());
        }

        if (taskDto.getDescription() != null) {
            task.setDescription(taskDto.getDescription());
        }

        if (StringUtils.isNotEmpty(taskDto.getPriority())) {
            task.setPriority(castPriority(taskDto.getPriority()));
        }

        if (StringUtils.isNotEmpty(taskDto.getStatus())) {
            task.setStatus(castStatus(taskDto.getStatus()));
        }

        return taskRepository.saveAndFlush(task);
    }

    public Task getById(Task taskDto) {
        return taskRepository.getTaskById(taskDto.getId());
    }

    public List<Task> getByAuthorId(IdDto idDto) {

        Long id = idDto.getId();

        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Author id can't be null");
        }

        return taskRepository.getTasksByAuthorId(id);
    }

    public List<Task> getTaskListForCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.getByUsername(authentication.getName());

        return taskRepository.getTasksByPerformerId(user.getId());
    }

    public List<Task> getByPerformerId(IdDto idDto) {

        Long id = idDto.getId();

        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Performer id can't be null");
        }

        return taskRepository.getTasksByPerformerId(id);
    }

    public Comment addComment(CommentDto commentDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.getByUsername(authentication.getName());

        Comment comment = new Comment()
                .setAuthorId(user.getId())
                .setText(commentDto.getText());

        if (StringUtils.isEmpty(commentDto.getText())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Text of a comment can't be empty");
        }

        Long taskId = commentDto.getTaskId();

        if (taskId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Task id can't be null");
        }

        comment.setTaskId(taskId);

        return commentRepository.saveAndFlush(comment);
    }

    public Comment editComment(CommentDto commentDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.getByUsername(authentication.getName());

        Long commentId = commentDto.getId();
        Long userId = user.getId();

        if (commentId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Comment id can't be null");
        }

        Comment comment = commentRepository.getCommentById(commentId);

        if (comment == null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Comment with given id doesn't exist");
        }

        if (!userId.equals(comment.getAuthorId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Can't edit other's comments");
        }

        if (StringUtils.isNotEmpty(commentDto.getText())) {
            comment.setText(commentDto.getText());
        }

        return commentRepository.saveAndFlush(comment);
    }

    public void deleteComment(IdDto idDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.getByUsername(authentication.getName());

        Long commentId = idDto.getId();
        Long userId = user.getId();

        if (commentId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Comment id can't be null");
        }

        Comment comment = commentRepository.getCommentById(commentId);

        if (comment == null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Comment with given id doesn't exist");
        }

        if (!userId.equals(comment.getAuthorId()) && !user.isAdmin()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You don't have permissions to delete other's comments");
        }

        commentRepository.deleteCommentById(commentId);
    }

    public List<Comment> getCommentsByTaskId(IdDto idDto) {
        Long id = idDto.getId();

        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Task id can't be null");
        }

        return commentRepository.getCommentsByTaskId(id);
    }

    private Task.Priority castPriority(String priorityString) {
        try {
            return Task.Priority.valueOf(priorityString);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Priority can only be " + Arrays.toString(Task.Priority.values()));
        }
    }

    private Task.Status castStatus(String statusString) {
        try {
            return Task.Status.valueOf(statusString);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status can only be " + Arrays.toString(Task.Status.values()));
        }
    }


}
