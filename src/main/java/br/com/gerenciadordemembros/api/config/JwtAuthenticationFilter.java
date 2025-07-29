package br.com.gerenciadordemembros.api.config;

import br.com.gerenciadordemembros.api.service.JwtService;
import br.com.gerenciadordemembros.api.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService; // Usando o seu UserDetailsServiceCustom

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getServletPath();

        // 🔓 Ignora caminhos públicos (login, registro, Swagger)
        // ATENÇÃO: Os caminhos aqui devem corresponder EXATAMENTE aos que você define nos seus controllers.
        // Se seu AuthenticationController está em @RequestMapping("/api/auth"), então deve ser "/api/auth/login"
        // Se seus docs Swagger são "/v3/api-docs", etc.
        if (path.startsWith("/api/auth/login") // Ajustado para /api/auth
                || path.startsWith("/api/auth/register") // Ajustado para /api/auth
                || path.startsWith("/v3/api-docs")
                || path.startsWith("/swagger-ui")
                || path.equals("/swagger-ui.html")) {
            System.out.println("🔓 Caminho público, seguindo sem autenticação.");
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // Se não há token, ou não é um token Bearer, mas não é um caminho público,
            // a requisição continua e o Spring Security irá interceptar e lançar um 403 Forbidden/401 Unauthorized
            // se o endpoint acessado exigir autenticação.
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(7); // Extrai o token após "Bearer "
        final String username = jwtService.extractUsername(jwt); // Extrai o username do token

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Carrega os detalhes do usuário
            UserDetails userDetails = userService.loadUserByUsername(username);

            // Valida o token
            if (jwtService.isTokenValid(jwt, userDetails)) {
                // Se o token é válido, cria e seta o objeto de autenticação no SecurityContextHolder
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } else {
                System.out.println("❌ Token inválido para o usuário: " + username);
                // Opcional: Você pode querer lançar uma exceção ou enviar um erro 401 aqui
                // response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                // return;
            }
        }

        // Continua a cadeia de filtros
        filterChain.doFilter(request, response);
    }
}