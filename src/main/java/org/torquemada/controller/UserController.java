package org.torquemada.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.torquemada.dto.UserDto;
import org.torquemada.dto.UserLoginDto;
import org.torquemada.dto.UserResponseDto;
import org.torquemada.service.UserService;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@RequestBody UserDto userDto) {
        Optional<UserResponseDto> response = userService.createUser(userDto);
        return response.map(created -> ResponseEntity.status(HttpStatus.CREATED).body(created)).orElseThrow(() ->
            new IllegalArgumentException("Email already in use"));
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> login(@RequestBody UserLoginDto loginDto) {
        return ResponseEntity.ok(userService.login(loginDto));
    }

    @GetMapping("/profile")
    public String getUserProfile(@AuthenticationPrincipal UserDetails userDetails) {
        return "Logged in user: " + userDetails.getUsername();
    }
}
