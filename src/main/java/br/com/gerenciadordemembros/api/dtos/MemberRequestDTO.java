package br.com.gerenciadordemembros.api.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

public record MemberRequestDTO(
        @NotBlank
        String fullName,
        @CPF
        String cpf,
        @NotBlank
        String rg,
        @NotBlank
        String telephone,
        @Email
        String email,
        @NotNull
        LocalDate dateOfBirth,
        @NotNull
        LocalDate baptismDate,
        @NotNull
        LocalDate entryDate,

        Boolean active,

        AddressDTO address,

        Long idChurch
) {
}
