package br.com.gerenciadordemembros.api.service;

import br.com.gerenciadordemembros.api.dtos.MinistroRequestDTO;
import br.com.gerenciadordemembros.api.dtos.MinistroResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface MinistroService {

    MinistroResponseDTO consagrarMinistro(MinistroRequestDTO dto);
}
