package org.generation.marify.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.generation.marify.dtos.LoginRequest;
import org.generation.marify.dtos.RegisterRequest;
import org.generation.marify.dtos.ResponseRequest;
import org.generation.marify.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api/auth")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {
    private AuthService authService;

    @PostMapping(path = "register")
    public ResponseEntity<ResponseRequest> registerUser(@Valid @RequestBody RegisterRequest registerRequest){
        ResponseRequest responseRequest = authService.registerUser(registerRequest);
        return ResponseEntity.ok(responseRequest);
    }

    @PostMapping(path="login")
    public ResponseEntity<ResponseRequest> loginUser(@Valid @RequestBody LoginRequest loginRequest){
        ResponseRequest responseRequest = authService.loginUser(loginRequest);
        return ResponseEntity.ok(responseRequest);
    }
}
