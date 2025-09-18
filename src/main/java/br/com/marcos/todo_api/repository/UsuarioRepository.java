package br.com.marcos.todo_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.marcos.todo_api.model.Usuario; // Importa nossa entidade Usuario


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // A CORREÇÃO: Mudar o tipo de retorno de UserDetails para Usuario.
    // O Spring Data JPA sabe como implementar "findByEmail" quando o retorno é a própria entidade.
    Usuario findByEmail(String email);
}