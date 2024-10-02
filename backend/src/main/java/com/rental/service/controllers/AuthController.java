package com.rental.service.controllers;

import com.rental.service.controllers.dto.auth.AuthRequest;
import com.rental.service.controllers.dto.auth.TokenResponse;
import com.rental.service.services.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public TokenResponse login(@RequestBody @Valid AuthRequest req) {
        var user = new UsernamePasswordAuthenticationToken(req.email(), req.password());
        var auth = authenticationManager.authenticate(user);

        var token = tokenService.generateToken(auth.getName());

        return new TokenResponse(token);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        SecurityContextHolder.clearContext();

        return ResponseEntity.ok().body("Successfully logged out.");
    }
}
