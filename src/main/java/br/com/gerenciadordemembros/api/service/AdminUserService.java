package br.com.gerenciadordemembros.api.service;

import br.com.gerenciadordemembros.api.dtos.UserActivationRequest;
import br.com.gerenciadordemembros.api.dtos.UserPasswordResetRequest;
import br.com.gerenciadordemembros.api.dtos.UserResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdminUserService {

    List<UserResponseDTO> getAllUsers();
    UserResponseDTO activateUser(Long userId, UserActivationRequest request);
    UserResponseDTO resetUserPassword(Long userId, UserPasswordResetRequest request);
    void deleteUser(Long userId);
}
