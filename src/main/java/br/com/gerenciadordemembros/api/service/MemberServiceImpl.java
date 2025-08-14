package br.com.gerenciadordemembros.api.service;

import br.com.gerenciadordemembros.api.dtos.MemberRequestDTO;
import br.com.gerenciadordemembros.api.dtos.MemberResponseDTO;
import br.com.gerenciadordemembros.api.mapper.AddressMapper;
import br.com.gerenciadordemembros.api.mapper.MemberMapper;
import br.com.gerenciadordemembros.api.model.Address;
import br.com.gerenciadordemembros.api.model.Church;
import br.com.gerenciadordemembros.api.model.Member;
import br.com.gerenciadordemembros.api.repository.AddressRepository;
import br.com.gerenciadordemembros.api.repository.ChurchRepository;
import br.com.gerenciadordemembros.api.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {


    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final ChurchRepository churchRepository;
    private final PhotoService photoService;

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

        String unformattedCpf = dto.cpf().replaceAll("[^0-9]", "");
        member.setCpf(unformattedCpf);

        if (dto.address() != null) {
            Address savedAddress = addressRepository.save(addressMapper.toEntity(dto.address()));
            member.setAddress(savedAddress);
        }

       return memberMapper.toDTO(memberRepository.save(member));
    }

    @Override
    public MemberResponseDTO getMemberById(Long id) {
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
        if (!memberRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Erro: Membro não encontrado. Verifique se o ID informado está correto e tente novamente.");
        }

        memberRepository.deleteById(id);
    }

    @Transactional
    @Override
    public MemberResponseDTO uploadPhoto(Long id, MultipartFile file) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Membro não encontrado."));

        try {
            String photoPath = photoService.savePhoto(file, "members");
            member.setPhotoUrl(photoPath);
            memberRepository.save(member);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao fazer upload da foto.");
        }

        return memberMapper.toDTO(member);
    }


}
