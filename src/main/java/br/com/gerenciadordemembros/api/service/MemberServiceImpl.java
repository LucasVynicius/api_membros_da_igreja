package br.com.gerenciadordemembros.api.service;

import br.com.gerenciadordemembros.api.dtos.MemberRequestDTO;
import br.com.gerenciadordemembros.api.dtos.MemberResponseDTO;
import br.com.gerenciadordemembros.api.mapper.AddressMapper;
import br.com.gerenciadordemembros.api.mapper.MemberMapper;
import br.com.gerenciadordemembros.api.model.Church;
import br.com.gerenciadordemembros.api.model.Member;
import br.com.gerenciadordemembros.api.repository.ChurchRepository;
import br.com.gerenciadordemembros.api.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {


    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;
    private final AddressMapper addressMapper;
    private final ChurchRepository churchRepository;

    @Transactional
    @Override
    public MemberResponseDTO registerMember(MemberRequestDTO dto) {

        if (memberRepository.existsByCpf(dto.cpf())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CPF já cadastrado no sistema.");
        }

        Church church = churchRepository.findById(dto.idChurch())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Erro: Igreja não encontrada. Não é possível registrar o membro."));

       Member member = memberMapper.toEntity(dto);
        member.setChurch(church);

       return memberMapper.toDTO(memberRepository.save(member));
    }

    @Override
    public MemberResponseDTO searchMemberById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Erro: Membro não encontrado. Verifique se o ID informado está correto e tente novamente."));

        return memberMapper.toDTO(member);
    }

    @Override
    public List<MemberResponseDTO> searchAllMember() {
        return memberRepository.findAll().stream().map(memberMapper::toDTO).toList();
    }

    @Transactional
    @Override
    public MemberResponseDTO updateMember(Long id, MemberRequestDTO dto) {

        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Erro: Membro não encontrado. Verifique se o ID informado está correto e tente novamente." ));

        if (!Objects.equals(member.getCpf(), dto.cpf()) && memberRepository.existsByCpf(dto.cpf())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CPF informado já pertence a outro membro.");
        }

        Church church = churchRepository.findById(dto.idChurch())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Erro: Igreja não encontrada. Verifique se o ID informado está correto e tente novamente."));

        memberMapper.updateMemberFromDto(dto, member);
        member.setChurch(church);

        return memberMapper.toDTO(memberRepository.save(member));
    }

    @Transactional
    @Override
    public void deleteMember(Long id) {
        if (!memberRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Erro: Membro não encontrado. Verifique se o ID informado está correto e tente novamente." );
        }

        memberRepository.deleteById(id);
    }


}
