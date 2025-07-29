package br.com.gerenciadordemembros.api.dtos;

public record AuthResponseDTO (
        String accessToken,
        String refreshToken
){
}
