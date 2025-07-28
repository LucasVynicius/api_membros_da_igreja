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

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {


    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;
    private final AddressMapper addressMapper;
    private final ChurchRepository igrejaRespository;

    @Override
    public MemberResponseDTO registerMember(MemberRequestDTO dto) {
       Member member = memberMapper.toEntity(dto);
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

        Church church = igrejaRespository.findById(dto.idIgreja())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Erro: Igreja não encontrada. Verifique se o ID informado está correto e tente novamente."));

        member.setFullName(dto.fullName());
        member.setCpf(dto.cpf());
        member.setRg(dto.rg());
        member.setTelephone(dto.telephone());
        member.setEmail(dto.email());
        member.setDateOfBirth(dto.dateOfBirth());
        member.setBaptismDate(dto.baptismDate());
        member.setEntryDate(dto.entryDate());
        member.setActive(dto.active());
        member.setAddress(addressMapper.toEntity(dto.address()));
        member.setChurch(church);


        memberRepository.save(member);

        return memberMapper.toDTO(member);
    }

    @Transactional
    @Override
    public void deletarMembro(Long id) {

    }


}
