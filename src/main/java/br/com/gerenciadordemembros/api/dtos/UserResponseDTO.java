package br.com.gerenciadordemembros.api.dtos;

import br.com.gerenciadordemembros.api.enums.Role;

public record UserResponseDTO (
        String username,
        String password,
        Role role
){

}
