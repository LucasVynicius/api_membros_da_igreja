package br.com.gerenciadordemembros.api.service;

import br.com.gerenciadordemembros.api.dtos.*;
import org.springframework.security.core.userdetails.UserDetailsService;
import java.util.List;

public interface UserService extends UserDetailsService {


    UserResponseDTO createUser(UserRequestDTO request);
    List<UserResponseDTO> getAllUsers();
    void activateUser(Long userId, UserActivationRequest request);
    UserResponseDTO resetUserPassword(Long userId, UserPasswordResetRequest request);
    UserResponseDTO updateUser(Long id, UserUpdateRequestDTO request);
    void deleteUser(Long userId);
}