package br.com.gerenciadordemembros.api.dtos;

import java.time.LocalDate;

public record ChurchResponseDTO(
        Long id,
        String name,
        String tradeName,
        String city,
        String country,
        LocalDate foundationDate,
        Long pastorLocalId,
        String pastorLocalName,
        AddressDTO address
) {
}
