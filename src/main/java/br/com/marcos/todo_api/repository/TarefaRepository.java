package br.com.marcos.todo_api.repository;

import org.springframework.data.jpa.repository.JpaRepository; // Importa o JpaRepository
import org.springframework.stereotype.Repository; // Importa a anotação Repository

import br.com.marcos.todo_api.model.Tarefa; // Importa nossa entidade Tarefa

@Repository // interface como um componente de repositório do Spring
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    
}