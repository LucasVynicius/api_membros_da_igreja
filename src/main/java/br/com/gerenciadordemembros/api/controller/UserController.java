package br.com.gerenciadordemembros.api.controller;

import br.com.gerenciadordemembros.api.dtos.UserResponseDTO;
import br.com.gerenciadordemembros.api.mapper.UserMapper;
import br.com.gerenciadordemembros.api.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserMapper userMapper;

    @GetMapping("/me") // O endpoint completo será GET /api/users/me
    public ResponseEntity<UserResponseDTO> getLoggedInUser(@AuthenticationPrincipal User userDetails) {
        // @AuthenticationPrincipal injeta o usuário que vem do token JWT
        return ResponseEntity.ok(userMapper.toDTO(userDetails));
    }
}