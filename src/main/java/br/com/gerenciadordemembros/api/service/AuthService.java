package br.com.gerenciadordemembros.api.service;

import br.com.gerenciadordemembros.api.dtos.AuthRequestDTO;
import br.com.gerenciadordemembros.api.dtos.AuthResponseDTO;

public interface AuthService {
    AuthResponseDTO login(AuthRequestDTO request);
}
