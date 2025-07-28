package br.com.gerenciadordemembros.api.service;

import br.com.gerenciadordemembros.api.dtos.MemberRequestDTO;
import br.com.gerenciadordemembros.api.dtos.MemberResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MemberService {

    MemberResponseDTO registerMember(MemberRequestDTO dto);

    MemberResponseDTO searchMemberById(Long id);

    List<MemberResponseDTO> searchAllMember();

    MemberResponseDTO updateMember(Long id, MemberRequestDTO dto);

    void deletarMembro(Long id);

}
