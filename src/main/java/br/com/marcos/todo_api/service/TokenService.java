package br.com.marcos.todo_api.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.marcos.todo_api.model.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service // Marca a classe como um serviço gerenciado pelo Spring
public class TokenService {

    public String validarToken(String token){
    try {
        var claims = Jwts.parser()
                .setSigningKey(this.jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject(); // Retorna o email (subject) do usuário
    } catch (Exception e){
        return ""; // Retorna vazio se o token for inválido
    }
}

    // Pega o valor da propriedade 'jwt.secret' do application.properties
    @Value("${jwt.secret}")
    private String jwtSecret;

    // Gera um token para o usuário
    public String gerarToken(Usuario usuario) {
        return Jwts.builder()
                .setSubject(usuario.getEmail()) // Define o "assunto" do token (quem ele representa)
                .setIssuedAt(new Date(System.currentTimeMillis())) // Data de criação
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // Data de expiração (24 horas)
                .signWith(SignatureAlgorithm.HS256, jwtSecret) // Assina com o algoritmo e o segredo
                .compact(); // Constrói o token como uma string
    }
}