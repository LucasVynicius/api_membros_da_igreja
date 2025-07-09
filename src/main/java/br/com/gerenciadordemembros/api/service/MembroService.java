package br.com.gerenciadordemembros.api.service;

import br.com.gerenciadordemembros.api.dtos.MembroRequestDTO;
import br.com.gerenciadordemembros.api.dtos.MembroResponseDTO;
import br.com.gerenciadordemembros.api.model.Membro;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MembroService {

    MembroResponseDTO criarMembro(MembroRequestDTO dto);

    MembroResponseDTO buscarMembroPeloId(Long id);

    List<MembroResponseDTO> buscarTodosMembros();

    MembroResponseDTO atualizarMembro(Long id, MembroRequestDTO dto);

}
