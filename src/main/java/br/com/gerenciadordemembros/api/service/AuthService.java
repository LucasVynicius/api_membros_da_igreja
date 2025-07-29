package br.com.gerenciadordemembros.api.service;

import br.com.gerenciadordemembros.api.dtos.AuthRequestDTO;
import br.com.gerenciadordemembros.api.dtos.AuthResponseDTO;
import br.com.gerenciadordemembros.api.dtos.RegisterRequestDTO;
import br.com.gerenciadordemembros.api.dtos.UserResponseDTO;
import br.com.gerenciadordemembros.api.model.User;
import br.com.gerenciadordemembros.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public AuthResponseDTO register(RegisterRequestDTO request) { // Usando RegisterRequest
        if (userRepository.findByUsername(request.username()).isPresent()) {
            throw new UsernameNotFoundException("Username already exists.");
        }

        User user = User.builder()
                .username(request.username())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(request.role())
                .build();
        userRepository.save(user);

        String accessToken = jwtService.generateAccessToken((UserDetails)user);
        String refreshToken = jwtService.generateRefreshToken((UserDetails)user);
        return new AuthResponseDTO(accessToken, refreshToken);
    }

    public AuthResponseDTO login(AuthRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        String accessToken = jwtService.generateAccessToken((UserDetails)user);
        String refreshToken = jwtService.generateRefreshToken((UserDetails) user);
        return new AuthResponseDTO(accessToken, refreshToken); // Retorna ambos os tokens
    }

    public UserResponseDTO getLoggedInUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        return new UserResponseDTO(
                user.getUsername(),
                user.getEmail(),
                user.getRole()
        );
    }
}