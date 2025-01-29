package br.com.gerenciadordemembros.api.service;

import br.com.gerenciadordemembros.api.dtos.MembroRequestDTO;
import br.com.gerenciadordemembros.api.dtos.MembroResponseDTO;
import br.com.gerenciadordemembros.api.model.Membro;
import br.com.gerenciadordemembros.api.repository.MembroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MembroServiceImpl implements MembroService {

    @Autowired
    MembroRepository membroRepository;

    @Override
    public MembroResponseDTO criarMembro(MembroRequestDTO membroRequestDTO) {
        Membro membro = new Membro();
        membro.setNome(membroRequestDTO.nome());
        membro.setCpf(membroRequestDTO.cpf());
        membro.setNacionalidade(membroRequestDTO.nacionalidade());
        membro.setDataNascimento(membroRequestDTO.dataNascimento());
        membro.setDataBatismo(membroRequestDTO.dataBatismo());
        membro.setDataConsagracao(membroRequestDTO.dataConsagracao());
        membro.setCriadoEm(LocalDateTime.now());
        membro.setTipoMembro(membroRequestDTO.tipoMembro());

        Membro membroCriado = membroRepository.save(membro);

        return new MembroResponseDTO("Membro criado com sucesso! Todas as informações foram registradas corretamente.", membro.getId());
    }
}
