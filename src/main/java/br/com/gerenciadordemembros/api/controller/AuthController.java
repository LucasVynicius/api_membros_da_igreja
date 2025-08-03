package br.com.gerenciadordemembros.api.controller;

import br.com.gerenciadordemembros.api.dtos.AuthRequestDTO;
import br.com.gerenciadordemembros.api.dtos.AuthResponseDTO;
import br.com.gerenciadordemembros.api.service.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthServiceImpl authServiceImpl;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO request) {
        return ResponseEntity.ok(authServiceImpl.login(request));
    }
}