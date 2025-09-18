package br.com.marcos.todo_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.marcos.todo_api.repository.UsuarioRepository;

@Service // Registra esta classe como um serviço gerenciado pelo Spring
public class AuthorizationService implements UserDetailsService {

    @Autowired
    private UsuarioRepository repository;

    // Este é o método que o Spring Security chama para carregar o usuário pelo nome de usuário (no nosso caso, o email)
    @Override
public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    System.out.println("Buscando usuário pelo email: " + username);

    UserDetails user = repository.findByEmail(username);
    
    if (user != null) {
        System.out.println("Usuário encontrado: " + user.getUsername());
    } else {
        System.out.println("Usuário NÃO encontrado com o email: " + username);
    }
    
   
    return user;
}
}