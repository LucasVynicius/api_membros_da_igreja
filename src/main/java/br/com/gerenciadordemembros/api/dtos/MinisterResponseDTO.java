package br.com.gerenciadordemembros.api.dtos;

import br.com.gerenciadordemembros.api.enums.MinisterialPosition;

import java.time.LocalDate;

public record MinisterResponseDTO(
        Long id,
        MinisterialPosition position,
        LocalDate consecrationDate,
        Long idMember,
        String fullName,
        String cpf,
        String telephone,
        String email,
        Long idChurch,
        String churchName,
        String churchTradeName,
        String churchCity,
        String churchCounty,
        AddressDTO addressChurch
) {
}
