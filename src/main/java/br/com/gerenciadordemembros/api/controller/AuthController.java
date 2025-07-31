package br.com.gerenciadordemembros.api.controller;

import br.com.gerenciadordemembros.api.dtos.AuthRequestDTO;
import br.com.gerenciadordemembros.api.dtos.AuthResponseDTO;
import br.com.gerenciadordemembros.api.dtos.RegisterRequestDTO;
import br.com.gerenciadordemembros.api.dtos.UserResponseDTO;
import br.com.gerenciadordemembros.api.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(
            @RequestBody @Valid RegisterRequestDTO request
    ) {
        AuthResponseDTO response = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(
            @RequestBody @Valid AuthRequestDTO request
    ) {
        AuthResponseDTO response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getMe() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails userDetails) {
            return ResponseEntity.ok(authService.getLoggedInUser(userDetails.getUsername()));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}