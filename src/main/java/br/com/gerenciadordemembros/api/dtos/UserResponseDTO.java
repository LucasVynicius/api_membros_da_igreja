package br.com.gerenciadordemembros.api.dtos;

import br.com.gerenciadordemembros.api.enums.Role;


public record UserResponseDTO (
        Long id,
        String firstName,
        String lastName,
        String username,
        String email,
        Role role,
        Boolean enabled
){

}
