package com.github.Noiseneks.taskManagementSpring.core.repository;

import com.github.Noiseneks.taskManagementSpring.domain.entity.User;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepositoryAccessor extends JpaRepository<User, Long> {

    /**
     * @param username
     * @return user object
     */
    @Nullable
    @Query("SELECT user from User user WHERE user.username = :username")
    User getOneByUsername(@Param("username") String username);
}
