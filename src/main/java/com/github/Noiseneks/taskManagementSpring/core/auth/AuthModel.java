package com.github.Noiseneks.taskManagementSpring.core.auth;

import com.github.Noiseneks.taskManagementSpring.core.repository.UserRepository;
import com.github.Noiseneks.taskManagementSpring.domain.dtos.LoginUserDto;
import com.github.Noiseneks.taskManagementSpring.domain.dtos.RegisterUserDto;
import com.github.Noiseneks.taskManagementSpring.domain.entity.User;
import io.micrometer.common.util.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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


        if (userRepository.getByUsername(input.getEmail()) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User with given email already exists");
        }

        String email = input.getEmail();
        String password = input.getPassword();

        if (StringUtils.isEmpty(email)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email can't be null");
        }

        if (StringUtils.isEmpty(password)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password can't be null");
        }

        User user = new User();
        user.setUsername(input.getEmail());
        user.setPassword(passwordEncoder.encode(input.getPassword()));

        return userRepository.saveAndFlush(user);
    }

    public User authenticate(LoginUserDto input) {

        if (userRepository.getByUsername(input.getEmail()) == null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User with given email doesn't exists");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return userRepository.getByUsername(input.getEmail());
    }
}