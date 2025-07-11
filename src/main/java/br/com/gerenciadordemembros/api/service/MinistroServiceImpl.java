package br.com.gerenciadordemembros.api.service;

import br.com.gerenciadordemembros.api.dtos.MinistroRequestDTO;
import br.com.gerenciadordemembros.api.dtos.MinistroResponseDTO;
import br.com.gerenciadordemembros.api.mapper.MinistroMapper;
import br.com.gerenciadordemembros.api.model.Igreja;
import br.com.gerenciadordemembros.api.model.Membro;
import br.com.gerenciadordemembros.api.model.Ministro;
import br.com.gerenciadordemembros.api.repository.IgrejaRepository;
import br.com.gerenciadordemembros.api.repository.MembroRepository;
import br.com.gerenciadordemembros.api.repository.MinistroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class MinistroServiceImpl implements MinistroService{

    private final MinistroRepository ministroRepository;
    private final MinistroMapper ministroMapper;
    private final IgrejaRepository igrejaRepository;
    private final MembroRepository membroRepository;



    @Override
    public MinistroResponseDTO consagrarMinistro(MinistroRequestDTO dto) {

        Membro membro = membroRepository.findById(dto.idMembro())
                .orElseThrow(() ->new ResponseStatusException(HttpStatus.NOT_FOUND, "Membro não encontrado"));

        Igreja igreja = igrejaRepository.findById(dto.idIgreja())
                .orElseThrow(() ->new ResponseStatusException(HttpStatus.NOT_FOUND, "Igreja não encontrado"));

        Ministro ministro = ministroMapper.toEntity(dto);
        ministro.setMembro(membro);
        ministro.setIgreja(igreja);

        return ministroMapper.toDTO(ministroRepository.save(ministro));
    }
}
