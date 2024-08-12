package com.github.Noiseneks.taskManagementSpring.core.auth;

import com.github.Noiseneks.taskManagementSpring.core.repository.UserRepository;
import com.github.Noiseneks.taskManagementSpring.domain.dtos.LoginUserDto;
import com.github.Noiseneks.taskManagementSpring.domain.dtos.RegisterUserDto;
import com.github.Noiseneks.taskManagementSpring.domain.entity.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthModel {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthModel(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User signup(RegisterUserDto input) {
        User user = new User();
        user.setUsername(input.getEmail());
        user.setPassword(passwordEncoder.encode(input.getPassword()));

        return userRepository.saveAndFlush(user);
    }

    public User authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return userRepository.getByUsername(input.getEmail());
    }
}