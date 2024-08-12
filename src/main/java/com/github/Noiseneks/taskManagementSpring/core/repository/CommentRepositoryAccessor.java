package com.github.Noiseneks.taskManagementSpring.core.repository;

import com.github.Noiseneks.taskManagementSpring.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepositoryAccessor extends JpaRepository<Comment, Long> {

    @Query("SELECT comment from Comment comment WHERE comment.taskId = :taskId")
    List<Comment> getCommentsListByTaskId(@Param("taskId") Long id);

}
