package br.com.gerenciadordemembros.api.service;

import br.com.gerenciadordemembros.api.dtos.IgrejaRequestDTO;
import br.com.gerenciadordemembros.api.dtos.IgrejaResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface IgrejaService {

    IgrejaResponseDTO criarIgreja (IgrejaRequestDTO dto);

    IgrejaResponseDTO atualizarIgreja(Long id, IgrejaRequestDTO dto);
}
