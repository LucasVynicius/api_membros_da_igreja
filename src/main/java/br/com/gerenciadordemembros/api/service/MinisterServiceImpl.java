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
        return null;
    }

    @Override
    public List<MinisterResponseDTO> listMinister() {
        return List.of();
    }

    @Override
    public MinisterResponseDTO updateMinister(Long id, MinisterRequestDTO dto) {
        return null;
    }

    @Override
    public void deletarMinistro(Long id) {

    }
}
