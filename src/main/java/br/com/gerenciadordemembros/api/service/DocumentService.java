package br.com.gerenciadordemembros.api.service;

import br.com.gerenciadordemembros.api.dtos.DocumentRequestDTO;
import org.springframework.stereotype.Service;

@Service
public interface DocumentService {
    byte[] generateRecommendationLetter(DocumentRequestDTO dto);
}
