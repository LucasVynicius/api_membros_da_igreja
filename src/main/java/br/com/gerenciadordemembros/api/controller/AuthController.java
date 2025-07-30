// src/main/java/br/com/gerenciadordemembros/api/controller/AuthenticationController.java
package br.com.gerenciadordemembros.api.controller;

import br.com.gerenciadordemembros.api.dtos.AuthRequestDTO; // Usando AuthRequestDTO
import br.com.gerenciadordemembros.api.dtos.AuthResponseDTO; // Usando AuthResponseDTO
import br.com.gerenciadordemembros.api.dtos.RegisterRequestDTO; // Usando RegisterRequestDTO
import br.com.gerenciadordemembros.api.dtos.UserResponseDTO; // Seu UsuarioResponseDTO renomeado para UserResponseDTO
import br.com.gerenciadordemembros.api.service.AuthService; // O AuthService (classe concreta)
import jakarta.validation.Valid; // Para validação de DTOs (boa prática, mesmo se não estiver no exemplo direto)
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus; // Para ResponseEntity.status(HttpStatus.CREATED)
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication; // Para SecurityContextHolder
import org.springframework.security.core.context.SecurityContextHolder; // Para SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
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
            @RequestBody @Valid AuthRequestDTO request // Usa @Valid para ativar as validações do DTO
    ) {
        AuthResponseDTO response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getMe() { // Renomeado para getMe conforme o modelo
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 401 Unauthorized
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails userDetails) {
            return ResponseEntity.ok(authService.getLoggedInUser(userDetails.getUsername()));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 401 Unauthorized
    }
}