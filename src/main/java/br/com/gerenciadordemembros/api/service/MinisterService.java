package br.com.gerenciadordemembros.api.service;

import br.com.gerenciadordemembros.api.dtos.MinisterRequestDTO;
import br.com.gerenciadordemembros.api.dtos.MinisterResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MinisterService {

    MinisterResponseDTO consecrateMinister(MinisterRequestDTO dto);

    MinisterResponseDTO searchMinisterById(Long id);

    List<MinisterResponseDTO> listMinister();

    MinisterResponseDTO updateMinister(Long id, MinisterRequestDTO dto);

    void deletarMinistro(Long id);
}
