package br.com.gerenciadordemembros.api.service;

import br.com.gerenciadordemembros.api.dtos.MinisterRequestDTO;
import br.com.gerenciadordemembros.api.dtos.MinisterResponseDTO;
import br.com.gerenciadordemembros.api.enums.MinisterialPosition;
import br.com.gerenciadordemembros.api.enums.Role;
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


    @Transactional
    @Override
    public MinisterResponseDTO consecrateMinister(MinisterRequestDTO dto) {
        if (dto.position().equals(MinisterialPosition.PRESIDENT) || dto.position().equals(Role.ADMIN)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Este cargo não pode ser atribuído a um ministro.");
        }


        if (ministerRepository.existsByMemberId(dto.idMember())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Este membro já possui um cargo ministerial.");
        }


        Member member = memberRepository.findById(dto.idMember())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Membro não encontrado"));

        Church church = churchRepository.findById(dto.idChurch())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Igreja não encontrada"));

        Minister minister = new Minister();
        minister.setMember(member);
        minister.setChurch(church);
        minister.setPosition(dto.position());
        minister.setConsecrationDate(dto.consecrationDate());


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

    @Override
    @Transactional
    public MinisterResponseDTO updateMinister(Long id, MinisterRequestDTO dto) {

        Minister minister = ministerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ministro não encontrado"));


        if (!minister.getMember().getId().equals(dto.idMember())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não é permitido alterar o membro de um registro ministerial.");
        }


        if (!minister.getChurch().getId().equals(dto.idChurch())) {
            Church newChurch = churchRepository.findById(dto.idChurch())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "A nova igreja informada não foi encontrada."));
            minister.setChurch(newChurch);
        }


        ministerMapper.updateMinisterFromDto(dto, minister);

        return ministerMapper.toDTO(ministerRepository.save(minister));
    }

    @Transactional
    @Override
    public void deleteMinister(Long id) {
        if (!ministerRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ministro não encontrado");
        }

        ministerRepository.deleteById(id);
    }
}
