package br.com.gerenciadordemembros.api.dtos;

import jakarta.validation.constraints.NotBlank;

public record RegistryDTO(
        @NotBlank(message = "O tipo de registro é obrigatório")
        String registryType,

        @NotBlank(message = "O número de registro é obrigatório")
        String registryNumber
) {

}
