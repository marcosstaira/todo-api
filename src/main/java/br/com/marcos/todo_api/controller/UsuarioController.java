

package br.com.marcos.todo_api.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.marcos.todo_api.model.Usuario;
import br.com.marcos.todo_api.repository.UsuarioRepository;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; 

    
    @PostMapping("/registrar")
public ResponseEntity<String> registrarUsuario(@RequestBody Usuario usuario) {
    System.out.println("--- DEBUG: DENTRO DO REGISTRO ---");
    System.out.println("Senha recebida (texto puro): '" + usuario.getSenha() + "'");
    
    String senhaHasheada = passwordEncoder.encode(usuario.getSenha());
    System.out.println("Senha após o hash: '" + senhaHasheada + "'");
    
    usuario.setSenha(senhaHasheada);
    usuarioRepository.save(usuario);
    
    System.out.println("--- DEBUG: FIM DO REGISTRO ---");
    return ResponseEntity.ok("Usuário registrado com sucesso!");
}
}