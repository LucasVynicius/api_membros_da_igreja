package br.com.gerenciadordemembros.api.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtService {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Value("${application.security.jwt.access-token.expiration}")
    private long accessTokenExpiration;

    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshTokenExpiration;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateAccessToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        // Adiciona as roles como uma lista de strings
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        claims.put("roles", roles);
        return buildToken(claims, userDetails, accessTokenExpiration);
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return buildToken(new HashMap<>(), userDetails, refreshTokenExpiration);
    }


    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims) // Claims personalizados
                .setSubject(userDetails.getUsername()) // Subject (geralmente username)
                .setIssuedAt(new Date(System.currentTimeMillis())) // Data de emissão
                .setExpiration(new Date(System.currentTimeMillis() + expiration)) // Data de expiração
                .setId(UUID.randomUUID().toString()) // JWT ID (JTI) para revogação e rastreamento
                .signWith(getSignInKey(), SignatureAlgorithm.HS256) // Assinatura com chave secreta e algoritmo
                .compact(); // Constrói e serializa o token
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts
                    .parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            System.err.println("JWT expirado: " + e.getMessage());
            throw e;
        } catch (UnsupportedJwtException e) {
            System.err.println("JWT não suportado: " + e.getMessage());
            throw e;
        } catch (MalformedJwtException e) {
            System.err.println("JWT malformado: " + e.getMessage());
            throw e;
        } catch (SignatureException e) {
            System.err.println("Assinatura JWT inválida: " + e.getMessage());
            throw e;
        } catch (IllegalArgumentException e) {
            System.err.println("Argumento ilegal para JWT (string vazia, nula, etc.): " + e.getMessage());
            throw e;
        }
    }

    private Key getSignInKey() {
        // Decodifica a chave secreta Base64 e cria uma chave HMAC SHA-256
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}