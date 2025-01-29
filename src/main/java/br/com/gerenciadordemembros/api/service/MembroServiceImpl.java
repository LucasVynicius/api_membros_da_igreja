package br.com.gerenciadordemembros.api.service;

import br.com.gerenciadordemembros.api.dtos.MembroRequestDTO;
import br.com.gerenciadordemembros.api.dtos.MembroResponseDTO;
import br.com.gerenciadordemembros.api.model.Membro;
import br.com.gerenciadordemembros.api.repository.MembroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

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

    @Override
    public Membro buscarMembroPeloId(Long id) {
        return membroRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Erro: Membro não encontrado. Verifique se o ID informado está correto e tente novamente."));
    }

    @Override
    public List<Membro> buscarTodosMembros() {
        return membroRepository.findAll();
    }


}
