package br.com.marcos.todo_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.marcos.todo_api.model.Usuario; 


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {


    Usuario findByEmail(String email);
}