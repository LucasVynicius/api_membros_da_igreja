package br.com.gerenciadordemembros.api.dtos;

import java.time.LocalDate;

public record MembroResponseDTO(
        Long id,
        String nomeCompleto,
        String cpf,
        String rg,
        String telefone,
        String email,
        LocalDate dataNascimento,
        LocalDate dataBatismo,
        LocalDate dataEntrada,
        Boolean ativo,
        EnderecoDTO endereco,
        Long idIgreja,
        String igrejaNome,
        String igrejaNomeFantasia,
        String igrejaCidade,
        String igrejaPais
) {
}
