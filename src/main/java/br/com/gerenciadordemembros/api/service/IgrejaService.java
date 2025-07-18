package br.com.gerenciadordemembros.api.service;

import br.com.gerenciadordemembros.api.dtos.IgrejaRequestDTO;
import br.com.gerenciadordemembros.api.dtos.IgrejaResponseDTO;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface IgrejaService {

    IgrejaResponseDTO criarIgreja (IgrejaRequestDTO dto);

    IgrejaResponseDTO buscarIgrejaPeloId(Long id);

    List<IgrejaResponseDTO> BuscarTodasIgrejas();

    IgrejaResponseDTO atualizarIgreja(Long id, IgrejaRequestDTO dto);
}
