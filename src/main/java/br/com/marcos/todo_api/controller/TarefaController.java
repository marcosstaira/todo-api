package br.com.marcos.todo_api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.marcos.todo_api.model.Tarefa;
import br.com.marcos.todo_api.repository.TarefaRepository;

@RestController 
@RequestMapping("/api/tarefas") 
public class TarefaController {

    
    @Autowired
    private TarefaRepository tarefaRepository;

    
    @GetMapping
    public List<Tarefa> listarTodas() {
        return tarefaRepository.findAll();
    }

   
    @PostMapping
    public ResponseEntity<Tarefa> criarTarefa(@RequestBody Tarefa tarefa) {
        Tarefa novaTarefa = tarefaRepository.save(tarefa);
        return new ResponseEntity<>(novaTarefa, HttpStatus.CREATED);


    }

    @GetMapping("/{id}")
public ResponseEntity<Tarefa> buscarPorId(@PathVariable Long id) {
    Optional<Tarefa> tarefaOptional = tarefaRepository.findById(id);

    if (tarefaOptional.isEmpty()) {
        
        return ResponseEntity.notFound().build();
    }

    
    return ResponseEntity.ok(tarefaOptional.get());
}

@PutMapping("/{id}")
public ResponseEntity<Tarefa> atualizarTarefa(@PathVariable Long id, @RequestBody Tarefa tarefaDetalhes) {
    Optional<Tarefa> tarefaOptional = tarefaRepository.findById(id);

    if (tarefaOptional.isEmpty()) {
        return ResponseEntity.notFound().build();
    }

    
    Tarefa tarefaExistente = tarefaOptional.get();

    
    tarefaExistente.setTexto(tarefaDetalhes.getTexto());
    tarefaExistente.setConcluida(tarefaDetalhes.isConcluida());


    Tarefa tarefaAtualizada = tarefaRepository.save(tarefaExistente);
    return ResponseEntity.ok(tarefaAtualizada);
}

@DeleteMapping("/{id}")
public ResponseEntity<?> deletarTarefa(@PathVariable Long id) {
    Optional<Tarefa> tarefaOptional = tarefaRepository.findById(id);

    if (tarefaOptional.isEmpty()) {
        return ResponseEntity.notFound().build();
    }

    tarefaRepository.deleteById(id);

    return ResponseEntity.noContent().build();
}
}