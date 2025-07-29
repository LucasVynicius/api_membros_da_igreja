package br.com.gerenciadordemembros.api.dtos;

public record AuthRequestDTO(
        String username,
        String password
) {
}
