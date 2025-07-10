package br.com.gerenciadordemembros.api.dtos;

import br.com.gerenciadordemembros.api.enums.TipoMembroEnum;

import java.time.LocalDate;

public record MembroRequestDTO(
        String nomeCompleto,
        String cpf,
        String rg,
        String telefone,
        LocalDate dataNascimento,
        LocalDate dataBatismo,
        LocalDate dataEntrada,
        Boolean ativo,
        EnderecoDTO endereco
) {
}
