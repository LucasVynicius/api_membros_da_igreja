package br.com.gerenciadordemembros.api.service;

import br.com.gerenciadordemembros.api.dtos.MembroRequestDTO;
import br.com.gerenciadordemembros.api.dtos.MembroResponseDTO;
import br.com.gerenciadordemembros.api.mapper.EnderecoMapper;
import br.com.gerenciadordemembros.api.mapper.MembroMapper;
import br.com.gerenciadordemembros.api.model.Membro;
import br.com.gerenciadordemembros.api.repository.MembroRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MembroServiceImpl implements MembroService {


    private final MembroRepository membroRepository;
    private final MembroMapper membroMapper;
    private final EnderecoMapper enderecoMapper;

    @Override
    public MembroResponseDTO criarMembro(MembroRequestDTO dto) {
       Membro membro = membroMapper.toEntity(dto);
       return membroMapper.toDTO(membroRepository.save(membro));
    }

    @Override
    public MembroResponseDTO buscarMembroPeloId(Long id) {
        Membro membro = membroRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Erro: Membro não encontrado. Verifique se o ID informado está correto e tente novamente."));

        return membroMapper.toDTO(membro);
    }

    @Override
    public List<MembroResponseDTO> buscarTodosMembros() {
        return membroRepository.findAll().stream().map(membroMapper::toDTO).toList();
    }

    @Transactional
    @Override
    public MembroResponseDTO atualizarMembro(Long id, MembroRequestDTO dto) {
        Membro membro = membroRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Erro: Membro não encontrado. Verifique se o ID informado está correto e tente novamente." ));

        membro.setNomeCompleto(dto.nomeCompleto());
        membro.setCpf(dto.cpf());
        membro.setRg(dto.rg());
        membro.setTelefone(dto.telefone());
        membro.setDataNascimento(dto.dataNascimento());
        membro.setDataBatismo(dto.dataBatismo());
        membro.setDataEntrada(dto.dataEntrada());
        membro.setAtivo(dto.ativo());
        membro.setEndereco(enderecoMapper.toEntity(dto.endereco()));

        membroRepository.save(membro);

        return membroMapper.toDTO(membro);
    }


}
