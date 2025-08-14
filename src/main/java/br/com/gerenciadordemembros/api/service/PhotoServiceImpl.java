package br.com.gerenciadordemembros.api.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class PhotoServiceImpl implements PhotoService {

    @Value("${upload.dir}")
    private String uploadDir;

    @Override
    public String savePhoto(MultipartFile file, String subdirectory) throws IOException {
        // 1. Gera um nome de arquivo único
        String filename = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();

        // 2. Cria o caminho completo usando o caminho absoluto configurado
        // Corrigido para usar o caminho absoluto do uploadDir
        Path uploadPath = Paths.get(uploadDir, subdirectory);

        // 3. Cria a pasta se ela não existir
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // 4. Salva o arquivo no sistema de arquivos
        Path filePath = uploadPath.resolve(filename);
        Files.copy(file.getInputStream(), filePath);

        // 5. Retorna o caminho relativo da API
        return "/" + subdirectory + "/" + filename;
    }
}