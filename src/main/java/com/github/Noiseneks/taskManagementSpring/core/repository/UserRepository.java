package com.github.Noiseneks.taskManagementSpring.core.repository;

import com.github.Noiseneks.taskManagementSpring.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserRepository {
    private final UserRepositoryAccessor userRepositoryAccessor;

    @Autowired
    public UserRepository(UserRepositoryAccessor userRepositoryAccessor) {
        this.userRepositoryAccessor = userRepositoryAccessor;
    }

    public User getByUsername(String username) {
        return userRepositoryAccessor.getOneByUsername(username);
    }

    public User saveAndFlush(User user) {
        return userRepositoryAccessor.saveAndFlush(user);
    }
}
