package org.generation.marify.services;

import lombok.AllArgsConstructor;
import org.generation.marify.dtos.LoginRequest;
import org.generation.marify.dtos.RegisterRequest;
import org.generation.marify.dtos.ResponseRequest;
import org.generation.marify.models.User;
import org.generation.marify.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public ResponseRequest registerUser(RegisterRequest registerRequest){
        User user = new User();
        user.setEmail(registerRequest.email());
        user.setName(registerRequest.name());
        user.setPassword(passwordEncoder.encode(registerRequest.password()));
        userRepository.save(user);
        String token = jwtService.generateToken(user.getEmail());
        return new ResponseRequest(token);
    }

    public ResponseRequest loginUser(LoginRequest loginRequest){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password())
        );
        String token = jwtService.generateToken(loginRequest.email());
        return new ResponseRequest(token);
    }
}
