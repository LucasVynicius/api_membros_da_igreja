package br.com.gerenciadordemembros.api.dtos;

public record CredentialResponseDTO(
        Long id,
        String fullName,
        String dateOfBirth,
        String cpf,
        String churchName,
        String position, // Este campo será nulo para membros
        String photoUrl
) {}
