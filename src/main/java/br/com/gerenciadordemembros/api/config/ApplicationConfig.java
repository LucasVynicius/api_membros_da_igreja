package br.com.gerenciadordemembros.api.config;

import br.com.gerenciadordemembros.api.enums.Role;
import br.com.gerenciadordemembros.api.model.User;
import br.com.gerenciadordemembros.api.repository.UserRepository;
import br.com.gerenciadordemembros.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean

    public CommandLineRunner createInitialAdminUser(UserRepository userRepository) {

        return args -> {

            String adminUsername = "gracaereino";


            if (!userRepository.existsByUsername(adminUsername)) {



                User adminUser = User.builder()

                        .username(adminUsername)

                        .email("pravania.jp@hotmail.com")

                        .password(passwordEncoder.encode("Vyni1995"))

                        .role(Role.ADMIN)

                        .enabled(true)

                        .build();
                userRepository.save(adminUser);

                System.out.println(">>> Usuário ADMIN inicial criado com sucesso! Senha: Vyni1995");

            }

        };
    }
}