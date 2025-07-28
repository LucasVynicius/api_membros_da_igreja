package br.com.gerenciadordemembros.api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ChurchRequestDTO(
        @NotBlank(message = "Nome de igreja incorreto")
        String name,
        @NotNull
        LocalDate foundationDate,
        @NotBlank
        String tradeName,

        Long pastorLocalId,
        @NotNull
        AddressDTO address

) {
}
