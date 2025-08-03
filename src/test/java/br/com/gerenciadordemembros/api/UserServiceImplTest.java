package br.com.gerenciadordemembros.api;

import br.com.gerenciadordemembros.api.dtos.UserActivationRequest;
import br.com.gerenciadordemembros.api.dtos.UserRequestDTO;
import br.com.gerenciadordemembros.api.enums.Role;
import br.com.gerenciadordemembros.api.model.User;
import br.com.gerenciadordemembros.api.repository.UserRepository;
import br.com.gerenciadordemembros.api.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Injetamos o encoder para verificar a senha

    @Test
    @DisplayName("Deve criar um novo usuário com status 'enabled = false' por padrão")
    void createUser_WithValidData_ShouldCreateInactiveUser() {
        // Arrange (Preparação)
        var request = new UserRequestDTO(
                "novo_secretario",
                "secretario@email.com",
                "senha123",
                Role.SECRETARY
        );

        // Act (Ação)
        var response = userService.createUser(request);

        // Assert (Verificação)
        assertNotNull(response);
        assertNotNull(response.id());

        // Busca o usuário direto do banco para uma verificação mais profunda
        User savedUser = userRepository.findById(response.id()).orElseThrow();

        assertEquals("novo_secretario", savedUser.getUsername());
        assertEquals("secretario@email.com", savedUser.getEmail());
        assertFalse(savedUser.isEnabled()); // A REGRA DE NEGÓCIO MAIS IMPORTANTE!
        assertTrue(passwordEncoder.matches("senha123", savedUser.getPassword())); // Verifica se a senha foi salva criptografada
    }

    @Test
    @DisplayName("Deve lançar exceção 400 ao tentar criar usuário com username duplicado")
    void createUser_WithDuplicateUsername_ShouldThrowBadRequest() {
        // Arrange: Primeiro, criamos um usuário para que o username já exista
        userRepository.save(User.builder()
                .username("usuario_existente")
                .email("email1@teste.com")
                .password("senha")
                .role(Role.ADMIN)
                .build());

        // Agora, tentamos criar outro com o mesmo username
        var request = new UserRequestDTO(
                "usuario_existente", // Username duplicado
                "email2@teste.com",
                "senha123",
                Role.SECRETARY
        );

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            userService.createUser(request);
        });

        assertEquals(400, exception.getStatusCode().value());
        assertTrue(exception.getReason().contains("Username já está em uso"));
    }

    @Test
    @DisplayName("Deve ativar um usuário inativo com sucesso")
    void activateUser_ForExistingInactiveUser_ShouldSucceed() {
        // Arrange: Criamos um usuário que, por padrão, nasce inativo (enabled=false)
        User savedUser = userRepository.save(User.builder()
                .username("usuario_inativo")
                .email("inativo@email.com")
                .password("senha")
                .role(Role.SECRETARY)
                .build());

        assertFalse(savedUser.isEnabled()); // Garante que ele começou inativo

        var activationRequest = new UserActivationRequest(true);

        // Act
        userService.activateUser(savedUser.getId(), activationRequest);

        // Assert
        User activatedUser = userRepository.findById(savedUser.getId()).orElseThrow();
        assertTrue(activatedUser.isEnabled()); // Verifica se o status mudou para true
    }
}