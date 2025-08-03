package br.com.gerenciadordemembros.api.controller;

import br.com.gerenciadordemembros.api.dtos.UserActivationRequest;
import br.com.gerenciadordemembros.api.dtos.UserPasswordResetRequest;
import br.com.gerenciadordemembros.api.dtos.UserRequestDTO;
import br.com.gerenciadordemembros.api.dtos.UserResponseDTO;
import br.com.gerenciadordemembros.api.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')") // Protege todos os endpoints da classe para ADMIN
public class AdminController {

    private final UserService userService;

    // Endpoint de criação, acessível por SECRETARY também (sobrescreve a segurança da classe)
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SECRETARY')")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody @Valid UserRequestDTO request) {
        UserResponseDTO createdUser = userService.createUser(request);
        return ResponseEntity.status(201).body(createdUser);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<Void> activateUser(@PathVariable Long id, @RequestBody UserActivationRequest request) {
        userService.activateUser(id, request);
        return ResponseEntity.noContent().build();
    }


    @PatchMapping("/{userId}/reset-password")
    public ResponseEntity<UserResponseDTO> resetUserPassword(
            @PathVariable Long userId,
            @RequestBody @Valid UserPasswordResetRequest request
    ) {
        UserResponseDTO updatedUser = userService.resetUserPassword(userId, request);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}