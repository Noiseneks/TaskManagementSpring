package com.github.Noiseneks.taskManagementSpring.core.repository;

import com.github.Noiseneks.taskManagementSpring.domain.entity.Comment;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommentRepository {

    private final CommentRepositoryAccessor commentRepositoryAccessor;

    @Autowired
    public CommentRepository(CommentRepositoryAccessor commentRepositoryAccessor) {
        this.commentRepositoryAccessor = commentRepositoryAccessor;
    }

    @Nullable
    public Comment getCommentById(@NotNull Long id) {
        return commentRepositoryAccessor
                .findById(id)
                .orElse(null);

    }

    public void deleteCommentById(@NotNull Long id) {
        commentRepositoryAccessor.deleteById(id);
    }

    public Comment saveAndFlush(Comment comment) {
        return commentRepositoryAccessor.saveAndFlush(comment);
    }

    public List<Comment> getCommentsByTaskId(Long id) {
        return commentRepositoryAccessor.getCommentsListByTaskId(id);
    }
}
