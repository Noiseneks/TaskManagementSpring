package com.github.Noiseneks.taskManagementSpring.core.repository;

import com.github.Noiseneks.taskManagementSpring.domain.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepositoryAccessor extends JpaRepository<Task, Long> {

    @Query("SELECT task from Task task WHERE task.authorId = :authorId")
    List<Task> getTaskListByAuthorId(@Param("authorId") Long id);

    @Query("SELECT task from Task task WHERE task.performerId = :performerId")
    List<Task> getTaskListByPerformerId(@Param("performerId") Long id);
}
