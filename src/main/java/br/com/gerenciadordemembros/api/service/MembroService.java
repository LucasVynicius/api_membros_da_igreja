package br.com.gerenciadordemembros.api.service;

import br.com.gerenciadordemembros.api.dtos.MembroRequestDTO;
import br.com.gerenciadordemembros.api.dtos.MembroResponseDTO;
import br.com.gerenciadordemembros.api.model.Membro;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MembroService {

    MembroResponseDTO criarMembro(MembroRequestDTO membroRequestDTO);

    Membro buscarMembroPeloId(Long id);

    List<Membro> buscarTodosMembros();

}
