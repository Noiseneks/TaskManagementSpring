package com.github.Noiseneks.taskManagementSpring.controllers.auth;

import com.github.Noiseneks.taskManagementSpring.core.auth.AuthModel;
import com.github.Noiseneks.taskManagementSpring.core.auth.JwtModel;
import com.github.Noiseneks.taskManagementSpring.domain.LoginResponse;
import com.github.Noiseneks.taskManagementSpring.domain.dtos.LoginUserDto;
import com.github.Noiseneks.taskManagementSpring.domain.dtos.RegisterUserDto;
import com.github.Noiseneks.taskManagementSpring.domain.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
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

    @Operation(summary = "Register new account",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful user registration",
                            content = @Content(
                                    schema = @Schema(implementation = User.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "User with given email already exists",
                            content = @Content(
                                    schema = @Schema(implementation = User.class)
                            )
                    )
            }
    )
    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authModel.signup(registerUserDto);
        return ResponseEntity.ok(registeredUser);
    }


    @Operation(summary = "Login into existing account",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful user login",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = LoginResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "The username or password is incorrect",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ProblemDetail.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "User with given email doesn't exists",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ProblemDetail.class)
                            )
                    )
            }
    )
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
