package com.github.Noiseneks.taskManagementSpring.controllers.auth;

import com.github.Noiseneks.taskManagementSpring.core.auth.AuthModel;
import com.github.Noiseneks.taskManagementSpring.core.auth.JwtModel;
import com.github.Noiseneks.taskManagementSpring.domain.LoginResponse;
import com.github.Noiseneks.taskManagementSpring.domain.dtos.LoginUserDto;
import com.github.Noiseneks.taskManagementSpring.domain.dtos.RegisterUserDto;
import com.github.Noiseneks.taskManagementSpring.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
public class AuthResource {

    private final JwtModel jwtModel;
    private final AuthModel authModel;

    @Autowired
    public AuthResource(JwtModel jwtModel, AuthModel authModel) {
        this.jwtModel = jwtModel;
        this.authModel = authModel;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authModel.signup(registerUserDto);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authModel.authenticate(loginUserDto);

        String jwtToken = jwtModel.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtModel.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }

}
