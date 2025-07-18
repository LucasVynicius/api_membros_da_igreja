package br.com.gerenciadordemembros.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Aplica o CORS a todos os endpoints
                .allowedOrigins("http://127.0.0.1:5500") // Permite requisições do seu frontend (porta do Live Server)
                // .allowedOrigins("http://localhost:5500") // Se você estiver usando localhost em vez de 127.0.0.1
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Métodos permitidos
                .allowedHeaders("*") // Permite todos os headers
                .allowCredentials(true); // Permite o envio de cookies de credenciais (se você usar autenticação baseada em sessão)
    }
}