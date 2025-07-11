package br.com.gerenciadordemembros.api.dtos;

import br.com.gerenciadordemembros.api.enums.CargoMinisterial;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record MinistroRequestDTO(
        @NotNull(message = "O cargo ministerial é obrigatório")
        CargoMinisterial cargo,

        @NotNull(message = "A data de consagração é obrigatória")
        LocalDate dataConsagracao,

        @NotNull(message = "O ID do membro é obrigatório")
        Long idMembro,

        @NotNull(message = "O ID da igreja é obrigatório")
        Long idIgreja
) {
}
