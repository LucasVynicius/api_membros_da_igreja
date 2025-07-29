package br.com.gerenciadordemembros.api.service;

import br.com.gerenciadordemembros.api.dtos.MinisterRequestDTO;
import br.com.gerenciadordemembros.api.dtos.MinisterResponseDTO;
import br.com.gerenciadordemembros.api.mapper.MinisterMapper;
import br.com.gerenciadordemembros.api.model.Church;
import br.com.gerenciadordemembros.api.model.Member;
import br.com.gerenciadordemembros.api.model.Minister;
import br.com.gerenciadordemembros.api.repository.ChurchRepository;
import br.com.gerenciadordemembros.api.repository.MemberRepository;
import br.com.gerenciadordemembros.api.repository.MinisterRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MinisterServiceImpl implements MinisterService {

    private final MinisterRepository ministerRepository;
    private final MinisterMapper ministerMapper;
    private final ChurchRepository churchRepository;
    private final MemberRepository memberRepository;



    @Override
    public MinisterResponseDTO consecrateMinister(MinisterRequestDTO dto) {

        Member member = memberRepository.findById(dto.idMember())
                .orElseThrow(() ->new ResponseStatusException(HttpStatus.NOT_FOUND, "Membro não encontrado"));

        Church church = churchRepository.findById(dto.idChurch())
                .orElseThrow(() ->new ResponseStatusException(HttpStatus.NOT_FOUND, "Igreja não encontrado"));

        Minister minister = ministerMapper.toEntity(dto);
        minister.setMember(member);
        minister.setChurch(church);

        return ministerMapper.toDTO(ministerRepository.save(minister));
    }

    @Override
    public MinisterResponseDTO searchMinisterById(Long id) {
        Minister minister = ministerRepository.findById(id).orElseThrow(
                () ->new ResponseStatusException(HttpStatus.NOT_FOUND, "Ministro não encontrado")
        );
        return ministerMapper.toDTO(minister);
    }

    @Override
    public List<MinisterResponseDTO> listMinister() {
        return ministerRepository.findAll().stream().map(ministerMapper::toDTO).toList();
    }

    @Transactional
    @Override
    public MinisterResponseDTO updateMinister(Long id, MinisterRequestDTO dto) {
        Minister minister = ministerRepository.findById(id).orElseThrow(
                () ->new ResponseStatusException(HttpStatus.NOT_FOUND, "Ministro não encontrado")
        );

        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Erro: Membro não encontrado. Verifique se o ID informado está correto e tente novamente." ));


        Church church = churchRepository.findById(dto.idChurch())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Erro: Igreja não encontrada. Verifique se o ID informado está correto e tente novamente."));

        if (!minister.getMember().getId().equals(dto.idMember())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não é permitido alterar o membro de um registro ministerial existente. " +
                    "Crie um novo registro se for para outro membro..");
        }
        if (!minister.getChurch().getId().equals(dto.idChurch())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não é permitido alterar a igreja de um registro ministerial existente. " +
                    "Crie um novo registro se for de outra igreja.");
        }

        minister.setPosition(dto.position());
        minister.setConsecrationDate(dto.consecrationDate());


        ministerRepository.save(minister);

        return ministerMapper.toDTO(minister);

    }

    @Override
    public void deleteMinister(Long id) {
        if (!ministerRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ministro não encontrado");
        }

        ministerRepository.deleteById(id);
    }
}
