package br.com.gerenciadordemembros.api.dtos;

import br.com.gerenciadordemembros.api.enums.RegistryType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ChurchRequestDTO(
        @NotBlank(message = "O nome da igreja é obrigatório")
        String name,

        @NotBlank(message = "O nome fantasia da igreja é obrigatório")
        String tradeName,

        @Valid
        @NotNull(message = "O registro da igreja é obrigatório")
        RegistryType registryType,

        @NotNull(message = "O número do registro da igreja é obrigatório")
        String registryNumber,

        Long pastorLocalId,

        @NotNull(message = "A data de fundação é obrigatória")
        LocalDate foundationDate,

        @Valid
        @NotNull(message = "O endereço é obrigatório")
        AddressDTO address

) {
}
