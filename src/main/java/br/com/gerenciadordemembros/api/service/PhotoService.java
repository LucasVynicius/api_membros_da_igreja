package br.com.gerenciadordemembros.api.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface PhotoService {

    String savePhoto(MultipartFile file, String subdirectory) throws IOException;
}
