package br.com.gerenciadordemembros.api.dtos;

import br.com.gerenciadordemembros.api.enums.CargoMinisterial;

import java.time.LocalDate;

public record MinistroResponseDTO(
        Long id,
        CargoMinisterial cargo,
        LocalDate dataConsagracao,
        Long idMembro,
        String nomeCompleto,
        String cpf,
        String telefone,
        String email,
        Long idIgreja,
        String igrejaNome,
        String igrejaNomeFantasia,
        String igrejaCidade,
        String igrejaPais,
        EnderecoDTO enderecoIgreja
) {
}
