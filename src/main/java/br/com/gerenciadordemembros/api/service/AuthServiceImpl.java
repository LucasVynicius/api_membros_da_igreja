package br.com.gerenciadordemembros.api.service;

import br.com.gerenciadordemembros.api.dtos.AuthRequestDTO;
import br.com.gerenciadordemembros.api.dtos.AuthResponseDTO;
import br.com.gerenciadordemembros.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponseDTO login(AuthRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );


        var user = userRepository.findByUsername(request.username()).orElseThrow();


        String jwtToken = jwtService.generateAccessToken(user);


        return new AuthResponseDTO(jwtToken, null);
    }
}