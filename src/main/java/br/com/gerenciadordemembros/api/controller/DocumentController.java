package br.com.gerenciadordemembros.api.controller;

import br.com.gerenciadordemembros.api.dtos.DocumentRequestDTO;
import br.com.gerenciadordemembros.api.service.DocumentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('ADMIN', 'SECRETARY')")
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping("/generate")
    public ResponseEntity<byte[]> generateDocument(@RequestBody @Valid DocumentRequestDTO dto) {

        if (dto.documentType() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tipo de documento é obrigatório.");
        }

        try {
            byte[] document = documentService.generateRecommendationLetter(dto);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            String filename = "carta_de_recomendacao_" + dto.idMember() + ".pdf";
            headers.setContentDispositionFormData("attachment", filename);

            return new ResponseEntity<>(document, headers, HttpStatus.OK);

        } catch (Exception e) {
            // Em caso de erro na geração, lança uma exceção
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Falha ao gerar o documento.");
        }
    }
}