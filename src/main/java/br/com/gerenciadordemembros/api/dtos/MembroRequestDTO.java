package br.com.gerenciadordemembros.api.dtos;

import br.com.gerenciadordemembros.api.enums.TipoMembroEnum;

import java.time.LocalDate;

public record MembroRequestDTO(

        String nome,
        String cpf,
        String nacionalidade,
        LocalDate dataNascimento,
        LocalDate dataBatismo,
        LocalDate dataConsagracao,
        TipoMembroEnum tipoMembro
) {
}
