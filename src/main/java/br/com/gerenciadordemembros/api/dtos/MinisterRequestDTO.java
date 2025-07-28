package br.com.gerenciadordemembros.api.dtos;

import br.com.gerenciadordemembros.api.enums.MinisterialPosition;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record MinisterRequestDTO(
        @NotNull(message = "O cargo ministerial é obrigatório")
        MinisterialPosition position,

        @NotNull(message = "A data de consagração é obrigatória")
        LocalDate consecrationDate,

        @NotNull(message = "O ID do membro é obrigatório")
        Long idMember,

        @NotNull(message = "O ID da igreja é obrigatório")
        Long idChurch
) {
}
