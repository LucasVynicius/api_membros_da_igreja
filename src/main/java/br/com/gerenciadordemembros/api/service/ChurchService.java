package br.com.gerenciadordemembros.api.service;

import br.com.gerenciadordemembros.api.dtos.ChurchRequestDTO;
import br.com.gerenciadordemembros.api.dtos.ChurchResponseDTO;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface ChurchService {

    ChurchResponseDTO registerChurch (ChurchRequestDTO dto);

    ChurchResponseDTO searchChurchById(Long id);

    List<ChurchResponseDTO> searchAllChurches();

    ChurchResponseDTO updateChurch(Long id, ChurchRequestDTO dto);

    void deletarIgreja(Long id);
}
