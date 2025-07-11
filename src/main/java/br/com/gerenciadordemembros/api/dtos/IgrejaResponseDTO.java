package br.com.gerenciadordemembros.api.dtos;

import java.time.LocalDate;

public record IgrejaResponseDTO(
        Long id,
        String nome,
        String nomeFantasia,
        String cidade,
        String pais,
        LocalDate dataFundacao,
        EnderecoDTO endereco
) {
}
