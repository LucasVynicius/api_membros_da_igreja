// src/main/java/br/com/gerenciadordemembros/api/controller/AdminUserController.java
package br.com.gerenciadordemembros.api.controller;

import br.com.gerenciadordemembros.api.dtos.UserActivationRequest;
import br.com.gerenciadordemembros.api.dtos.UserPasswordResetRequest;
import br.com.gerenciadordemembros.api.dtos.UserResponseDTO;
import br.com.gerenciadordemembros.api.service.AdminUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin/users") // Caminho base para operações de admin em usuários
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')") // Garante que SOMENTE ADMINS acessem este controller
public class AdminUserController {

    private final AdminUserService adminUserService;

    /**
     * Lista todos os usuários do sistema.
     * Requer ROLE ADMIN.
     * GET /api/admin/users
     */
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return ResponseEntity.ok(adminUserService.getAllUsers());
    }

    /**
     * Ativa ou desativa um usuário.
     * Requer ROLE ADMIN.
     * PUT /api/admin/users/{userId}/enable
     */
    @PutMapping("/{userId}/enable")
    public ResponseEntity<UserResponseDTO> activateUser(
            @PathVariable Long userId,
            @RequestBody @Valid UserActivationRequest request
    ) {
        return ResponseEntity.ok(adminUserService.activateUser(userId, request));
    }

    /**
     * Reseta a senha de um usuário.
     * Requer ROLE ADMIN.
     * PUT /api/admin/users/{userId}/reset-password
     */
    @PutMapping("/{userId}/reset-password")
    public ResponseEntity<UserResponseDTO> resetUserPassword(
            @PathVariable Long userId,
            @RequestBody @Valid UserPasswordResetRequest request
    ) {
        return ResponseEntity.ok(adminUserService.resetUserPassword(userId, request));
    }

    /**
     * Deleta um usuário.
     * Requer ROLE ADMIN.
     * DELETE /api/admin/users/{userId}
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        adminUserService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}