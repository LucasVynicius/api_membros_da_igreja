package br.com.gerenciadordemembros.api.dtos;

import br.com.gerenciadordemembros.api.enums.DocumentType;

public record DocumentRequestDTO(
        DocumentType documentType,
        Long idMember
) {
}
