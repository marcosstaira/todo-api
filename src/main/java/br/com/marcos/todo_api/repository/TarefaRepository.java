package br.com.marcos.todo_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.marcos.todo_api.model.Tarefa; 

@Repository 
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    
}