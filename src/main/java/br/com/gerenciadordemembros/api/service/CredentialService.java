package br.com.gerenciadordemembros.api.service;

import br.com.gerenciadordemembros.api.dtos.CredentialResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public interface CredentialService {

    CredentialResponseDTO generateMemberCredential(Long id);

     CredentialResponseDTO generateMinisterCredential(Long id);
}