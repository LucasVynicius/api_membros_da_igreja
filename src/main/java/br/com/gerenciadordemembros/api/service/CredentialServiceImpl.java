package br.com.gerenciadordemembros.api.service;

import br.com.gerenciadordemembros.api.dtos.CredentialResponseDTO;
import br.com.gerenciadordemembros.api.model.Member;
import br.com.gerenciadordemembros.api.model.Minister;
import br.com.gerenciadordemembros.api.repository.MemberRepository;
import br.com.gerenciadordemembros.api.repository.MinisterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CredentialServiceImpl implements CredentialService {

    private final MemberRepository memberRepository;
    private final MinisterRepository ministerRepository;

    @Override
    public CredentialResponseDTO generateMemberCredential(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Membro não encontrado."));

        Optional<Minister> ministerOptional = ministerRepository.findByMemberId(id);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDateOfBirth = member.getDateOfBirth().format(formatter);

        return new CredentialResponseDTO(
                member.getId(),
                member.getFullName(),
                formattedDateOfBirth,
                member.getCpf(),
                member.getChurch().getName(),
                ministerOptional.map(minister -> minister.getPosition().name()).orElse(null),
                member.getPhotoUrl()
        );
    }

    @Override
    public CredentialResponseDTO generateMinisterCredential(Long ministerId) {

        Minister minister = ministerRepository.findById(ministerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ministro não encontrado."));

        Member member = minister.getMember();


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDateOfBirth = member.getDateOfBirth().format(formatter);


        return new CredentialResponseDTO(
                member.getId(),
                member.getFullName(),
                formattedDateOfBirth,
                member.getCpf(),
                member.getChurch().getName(),
                minister.getPosition().name(),
                member.getPhotoUrl()
        );
    }
}