package br.com.marcos.todo_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.marcos.todo_api.model.Usuario;
import br.com.marcos.todo_api.repository.UsuarioRepository;
import br.com.marcos.todo_api.service.TokenService;

// DTOs
class LoginRequest {
    private String email;
    private String senha;
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
}

class TokenResponse {
    private String token;
    public TokenResponse(String token) { this.token = token; }
    public String getToken() { return token; }
}

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            // Autentica o usuário
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(), loginRequest.getSenha()
                )
            );

            // Busca usuário no banco
            Usuario usuario = usuarioRepository.findByEmail(loginRequest.getEmail());
            if (usuario == null) {
                return ResponseEntity.status(401).body("Usuário não encontrado");
            }

            // Gera o token
            String token = tokenService.gerarToken(usuario);

            // Retorna token
            return ResponseEntity.ok(new TokenResponse(token));

        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Email ou senha inválidos");
        }
    }
}
