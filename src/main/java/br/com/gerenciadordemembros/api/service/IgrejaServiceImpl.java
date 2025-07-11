package br.com.gerenciadordemembros.api.service;

import br.com.gerenciadordemembros.api.dtos.IgrejaRequestDTO;
import br.com.gerenciadordemembros.api.dtos.IgrejaResponseDTO;
import br.com.gerenciadordemembros.api.mapper.EnderecoMapper;
import br.com.gerenciadordemembros.api.mapper.IgrejaMapper;
import br.com.gerenciadordemembros.api.model.Igreja;
import br.com.gerenciadordemembros.api.repository.IgrejaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class IgrejaServiceImpl implements IgrejaService{

    private final IgrejaRepository igrejaRespository;
    private final IgrejaMapper igrejaMapper;
    private final EnderecoMapper enderecoMapper;

    @Override
    public IgrejaResponseDTO criarIgreja(IgrejaRequestDTO dto) {

        Igreja igreja = igrejaMapper.toEntity(dto);

        return igrejaMapper.toDTO(igrejaRespository.save(igreja));
    }

    @Override
    public IgrejaResponseDTO atualizarIgreja(Long id, IgrejaRequestDTO dto) {
        Igreja igreja = igrejaRespository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Erro: Igreja não encontrada. Verifique se o ID informado está correto e tente novamente."));

        igreja.setNome(dto.nome());
        igreja.setDataFundacao(dto.dataFundacao());
        igreja.setNomeFantasia(dto.nomeFantasia());
        igreja.setEndereco(enderecoMapper.toEntity(dto.endereco()));

        igrejaRespository.save(igreja);
        return igrejaMapper.toDTO(igreja);
    }
}
