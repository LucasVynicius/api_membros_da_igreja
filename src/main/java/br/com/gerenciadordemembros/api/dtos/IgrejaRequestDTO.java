package br.com.gerenciadordemembros.api.dtos;

import java.time.LocalDate;

public record IgrejaRequestDTO(
        String nome,
        LocalDate dataFundacao,
        String nomeFantasia,
        Long pastorLocalId,
        EnderecoDTO endereco

) {
}
