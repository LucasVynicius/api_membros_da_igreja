package br.com.gerenciadordemembros.api.service;

import br.com.gerenciadordemembros.api.dtos.MembroRequestDTO;
import br.com.gerenciadordemembros.api.dtos.MembroResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface MembroService {

    MembroResponseDTO criarMembro(MembroRequestDTO membroRequestDTO);
}
