package com.github.Noiseneks.taskManagementSpring.core.repository;

import com.github.Noiseneks.taskManagementSpring.domain.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaskRepository {
    private final TaskRepositoryAccessor taskRepositoryAccessor;

    @Autowired
    public TaskRepository(TaskRepositoryAccessor taskRepositoryAccessor) {
        this.taskRepositoryAccessor = taskRepositoryAccessor;
    }

    public Task saveAndFlush(Task task) {
        return taskRepositoryAccessor.saveAndFlush(task);
    }

    public Task getTaskById(Long id) {
        return taskRepositoryAccessor
                .findById(id)
                .orElse(null);

    }

    public void deleteTaskById(Long id) {
        taskRepositoryAccessor.deleteById(id);
    }

    public List<Task> getTasksByAuthorId(Long id) {
        return taskRepositoryAccessor.getTaskListByAuthorId(id);
    }

    public List<Task> getTasksByPerformerId(Long id) {
        return taskRepositoryAccessor.getTaskListByPerformerId(id);
    }

}
