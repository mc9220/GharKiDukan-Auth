package com.gkd.auth.controller;

import com.gkd.auth.model.AuthResponse;
import com.gkd.auth.model.User;
import com.gkd.auth.service.JwtService;
import com.gkd.auth.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class GkdAuthController {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public AuthResponse register(@RequestBody AuthRequest request) {
        User user = userService.saveUser(request.getUsername(), request.getPassword(), "ROLE_USER");
        return  AuthResponse.builder().accessToken(jwtService.generateToken(user.getUsername())).build();
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        return AuthResponse.builder().accessToken(jwtService.generateToken(request.username)).build();
    }

    @Getter
    @Setter
    public static class AuthRequest {
        private String username;
        private String password;
    }


}
