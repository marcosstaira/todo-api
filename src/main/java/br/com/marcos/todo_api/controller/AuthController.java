package br.com.marcos.todo_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Importações necessárias
import br.com.marcos.todo_api.dto.LoginRequestDTO;
import br.com.marcos.todo_api.dto.LoginResponseDTO;
import br.com.marcos.todo_api.model.Usuario;
import br.com.marcos.todo_api.service.TokenService;

/**
 * Controller responsável pelo endpoint de autenticação (login).
 */
@RestController
@RequestMapping("/api/auth") // Define o caminho base para todos os endpoints nesta classe
public class AuthController {

    // Injeta o gerenciador de autenticação do Spring Security
    @Autowired
    private AuthenticationManager authenticationManager;

    // Injeta nosso serviço customizado para manipulação de tokens
    @Autowired
    private TokenService tokenService;

    /**
     * Endpoint para autenticar um usuário e retornar um token JWT.
     * Mapeado para requisições POST em /api/auth/login.
     *
     * @param loginRequestDTO Objeto contendo o email e a senha enviados no corpo da requisição.
     * @return Um ResponseEntity contendo o token JWT em caso de sucesso.
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        // 1. Cria um objeto de autenticação com as credenciais (email e senha) que o usuário enviou.
        //    O Spring Security usa este objeto para processar a tentativa de login.
        var usernamePassword = new UsernamePasswordAuthenticationToken(
                loginRequestDTO.email(),
                loginRequestDTO.senha()
        );

        // 2. Pede ao AuthenticationManager para autenticar o usuário.
        //    É neste momento que o Spring chama nosso `AuthorizationService` para buscar o usuário
        //    e usa o `PasswordEncoder` para comparar as senhas. Se as credenciais estiverem erradas,
        //    uma exceção será lançada aqui, resultando em um erro 403 (Forbidden).
        var auth = this.authenticationManager.authenticate(usernamePassword);

        // 3. Se a autenticação for bem-sucedida, o objeto 'auth' conterá os detalhes do usuário.
        //    Nós extraímos o objeto 'Usuario' que foi carregado pelo nosso AuthorizationService.
        var usuario = (Usuario) auth.getPrincipal();

        // 4. Com o usuário autenticado, chamamos nosso TokenService para gerar o token JWT.
        var token = tokenService.gerarToken(usuario);

        // 5. Retornamos uma resposta 200 OK, com o token dentro de um DTO para um formato de resposta JSON limpo.
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}