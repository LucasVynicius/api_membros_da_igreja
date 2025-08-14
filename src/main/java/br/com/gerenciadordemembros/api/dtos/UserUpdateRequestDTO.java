package br.com.gerenciadordemembros.api.dtos;

import br.com.gerenciadordemembros.api.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserUpdateRequestDTO(
        @NotNull
        Long id,
        @NotBlank
        @Size(min = 3, max = 50)
        String username,
        @NotBlank
        @Size(min = 3, max = 50)
        String firstName,
        @NotBlank
        @Size(min = 3, max = 50)
        String lastName,
        @NotBlank
        @Email
        String email,
        @NotNull
        Role role,
        @NotNull
        Boolean enabled
) {}