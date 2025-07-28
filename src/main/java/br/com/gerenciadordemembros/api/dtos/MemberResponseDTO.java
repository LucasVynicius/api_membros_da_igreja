package br.com.gerenciadordemembros.api.dtos;

import java.time.LocalDate;

public record MemberResponseDTO(
        Long id,
        String fullName,
        String cpf,
        String rg,
        String telephone,
        String email,
        LocalDate dateOfBirth,
        LocalDate baptismDate,
        LocalDate entryDate,
        Boolean active,
        AddressDTO address,
        Long idChurch,
        String churchName,
        String churchTradeName,
        String churchCity,
        String churchCounty
) {
}
