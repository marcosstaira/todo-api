package br.com.marcos.todo_api.service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.marcos.todo_api.model.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class TokenService {

    // Segredo definido no application.properties
    @Value("${jwt.secret}")
    private String jwtSecret;

    // Gera um token JWT para o usuário
    public String gerarToken(Usuario usuario) {
        return Jwts.builder()
                .setSubject(usuario.getEmail()) // Quem é o dono do token
                .setIssuedAt(new Date(System.currentTimeMillis())) // Data de emissão
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // Expira em 24h
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256) // Assina usando a chave
                .compact();
    }

    // Valida o token e retorna o email (subject)
    public String validarToken(String token) {
        try {
            var claims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return claims.getSubject();
        } catch (Exception e) {
            return ""; // Retorna vazio se inválido
        }
    }
}
