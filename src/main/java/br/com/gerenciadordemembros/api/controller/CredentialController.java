package br.com.gerenciadordemembros.api.controller;

import br.com.gerenciadordemembros.api.dtos.CredentialResponseDTO;
import br.com.gerenciadordemembros.api.service.CredentialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/credentials")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('ADMIN', 'SECRETARY')")
public class CredentialController {

    private final CredentialService credentialService;

    @GetMapping
    public ResponseEntity<CredentialResponseDTO> generateCredential(
            @RequestParam String type,
            @RequestParam Long id) {


        if ("membro".equalsIgnoreCase(type)) {
            CredentialResponseDTO credential = credentialService.generateMemberCredential(id);
            return ResponseEntity.ok(credential);

        } else if ("ministro".equalsIgnoreCase(type)) {
            CredentialResponseDTO credential = credentialService.generateMinisterCredential(id);
            return ResponseEntity.ok(credential);
        } else {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tipo de credencial inválido.");
        }
    }
}