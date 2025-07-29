package br.com.gerenciadordemembros.api.dtos;

import br.com.gerenciadordemembros.api.enums.Role;

public record RegisterRequestDTO(
        String username,
        String email,
        String password,
        Role role
) {
}
