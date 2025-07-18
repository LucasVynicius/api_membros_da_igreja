package br.com.gerenciadordemembros.api.service;

import br.com.gerenciadordemembros.api.dtos.IgrejaRequestDTO;
import br.com.gerenciadordemembros.api.dtos.IgrejaResponseDTO;
import br.com.gerenciadordemembros.api.mapper.EnderecoMapper;
import br.com.gerenciadordemembros.api.mapper.IgrejaMapper;
import br.com.gerenciadordemembros.api.model.Igreja;
import br.com.gerenciadordemembros.api.model.Ministro;
import br.com.gerenciadordemembros.api.repository.IgrejaRepository;
import br.com.gerenciadordemembros.api.repository.MinistroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IgrejaServiceImpl implements IgrejaService{

    private final IgrejaRepository igrejaRespository;
    private final IgrejaMapper igrejaMapper;
    private final MinistroRepository ministroRepository;
    private final EnderecoMapper enderecoMapper;

    @Override
    public IgrejaResponseDTO criarIgreja(IgrejaRequestDTO dto) {

        Igreja igreja = igrejaMapper.toEntity(dto);

        return igrejaMapper.toDTO(igrejaRespository.save(igreja));
    }

    @Override
    public IgrejaResponseDTO buscarIgrejaPeloId(Long id) {
        Igreja igreja = igrejaRespository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Erro: Igreja não encontrada. Verifique se o ID informado está correto e tente novamente."));
        return igrejaMapper.toDTO(igreja);
    }

    @Override
    public List<IgrejaResponseDTO> BuscarTodasIgrejas() {
        return igrejaRespository.findAll().stream().map(igrejaMapper::toDTO).toList();
    }

    @Override
    public IgrejaResponseDTO atualizarIgreja(Long id, IgrejaRequestDTO dto) {
        Igreja igreja = igrejaRespository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Erro: Igreja não encontrada. Verifique se o ID informado está correto e tente novamente."));

        Ministro pastor = ministroRepository.findById(dto.pastorLocalId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pastor local não encontrado"));
        igreja.setPastorLocal(pastor);

        igreja.setNome(dto.nome());
        igreja.setDataFundacao(dto.dataFundacao());
        igreja.setNomeFantasia(dto.nomeFantasia());
        igreja.setEndereco(enderecoMapper.toEntity(dto.endereco()));
        igreja.setPastorLocal(pastor);

        igrejaRespository.save(igreja);
        return igrejaMapper.toDTO(igreja);
    }
}
