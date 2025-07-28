package br.com.gerenciadordemembros.api.service;

import br.com.gerenciadordemembros.api.dtos.ChurchRequestDTO;
import br.com.gerenciadordemembros.api.dtos.ChurchResponseDTO;
import br.com.gerenciadordemembros.api.mapper.AddressMapper;
import br.com.gerenciadordemembros.api.mapper.ChurchMapper;
import br.com.gerenciadordemembros.api.model.Church;
import br.com.gerenciadordemembros.api.model.Minister;
import br.com.gerenciadordemembros.api.repository.ChurchRepository;
import br.com.gerenciadordemembros.api.repository.MinisterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChurchServiceImpl implements ChurchService {

    private final ChurchRepository churchRespository;
    private final ChurchMapper churchMapper;
    private final MinisterRepository ministerRepository;
    private final AddressMapper addressMapper;

    @Override
    public ChurchResponseDTO registerChurch(ChurchRequestDTO dto) {

        Church church = churchMapper.toEntity(dto);

        return churchMapper.toDTO(churchRespository.save(church));
    }

    @Override
    public ChurchResponseDTO searchChurchById(Long id) {
        Church church = churchRespository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Erro: Igreja não encontrada. Verifique se o ID informado está correto e tente novamente."));
        return churchMapper.toDTO(church);
    }

    @Override
    public List<ChurchResponseDTO> searchAllChurches() {
        return churchRespository.findAll().stream().map(churchMapper::toDTO).toList();
    }

    @Override
    public ChurchResponseDTO updateChurch(Long id, ChurchRequestDTO dto) {
        Church church = churchRespository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Erro: Igreja não encontrada. Verifique se o ID informado está correto e tente novamente."));

        Minister pastor = ministerRepository.findById(dto.pastorLocalId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pastor local não encontrado"));
        church.setPastorLocal(pastor);

        church.setName(dto.name());
        church.setFoundationDate(dto.foundationDate());
        church.setTradeName(dto.tradeName());
        church.setAddress(addressMapper.toEntity(dto.address()));
        church.setPastorLocal(pastor);

        churchRespository.save(church);
        return churchMapper.toDTO(church);
    }

    @Override
    public void deletarIgreja(Long id) {

    }
}
