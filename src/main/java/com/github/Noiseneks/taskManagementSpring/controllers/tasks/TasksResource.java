package com.github.Noiseneks.taskManagementSpring.controllers.tasks;

import com.github.Noiseneks.taskManagementSpring.core.tasks.TasksModel;
import com.github.Noiseneks.taskManagementSpring.domain.dtos.CommentDto;
import com.github.Noiseneks.taskManagementSpring.domain.dtos.IdDto;
import com.github.Noiseneks.taskManagementSpring.domain.dtos.TaskDto;
import com.github.Noiseneks.taskManagementSpring.domain.entity.Comment;
import com.github.Noiseneks.taskManagementSpring.domain.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TasksResource {

    private final TasksModel tasksModel;

    @Autowired
    public TasksResource(TasksModel tasksModel) {
        this.tasksModel = tasksModel;
    }

    @PostMapping("/createTask")
    public ResponseEntity<Task> createTask(@RequestBody TaskDto taskDto) {
        Task task = tasksModel.createTask(taskDto);
        return ResponseEntity.ok(task);
    }

    @DeleteMapping("/deleteTask")
    public ResponseEntity<HashMap<String, String>> deleteTask(@RequestBody TaskDto taskDto) {

        tasksModel.deleteTask(taskDto);

        HashMap<String, String> body = new HashMap<>();
        body.put("message", "Task deleted successfully");
        return ResponseEntity.ok(body);
    }

    @PatchMapping("/editTask")
    public ResponseEntity<Task> editTask(@RequestBody TaskDto taskDto) {
        Task task = tasksModel.updateTask(taskDto);
        return ResponseEntity.ok(task);
    }

    @GetMapping("/getMyTasks")
    public ResponseEntity<List<Task>> getMyTasks() {
        List<Task> tasks = tasksModel.getTaskListForCurrentUser();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/getByAuthorId")
    public ResponseEntity<List<Task>> getByAuthorId(@RequestBody IdDto idDto) {
        List<Task> tasks = tasksModel.getByAuthorId(idDto);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/getByPerformerId")
    public ResponseEntity<List<Task>> getByPerformerId(@RequestBody IdDto idDto) {
        List<Task> tasks = tasksModel.getByPerformerId(idDto);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/getComments")
    public ResponseEntity<List<Comment>> getComments(@RequestBody IdDto taskIdDto) {
        List<Comment> comments = tasksModel.getCommentsByTaskId(taskIdDto);
        return ResponseEntity.ok(comments);
    }

    @PostMapping("/addComment")
    public ResponseEntity<Comment> addComment(@RequestBody CommentDto commentDto) {
        Comment comment = tasksModel.addComment(commentDto);
        return ResponseEntity.ok(comment);
    }

    @PatchMapping("/editComment")
    public ResponseEntity<Comment> editComment(@RequestBody CommentDto commentDto) {
        Comment comment = tasksModel.editComment(commentDto);
        return ResponseEntity.ok(comment);
    }

    @DeleteMapping("/deleteComment")
    public ResponseEntity<HashMap<String, String>> deleteComment(@RequestBody IdDto commentIdDto) {

        tasksModel.deleteComment(commentIdDto);

        HashMap<String, String> body = new HashMap<>();
        body.put("message", "Comment deleted successfully");
        return ResponseEntity.ok(body);
    }

}
