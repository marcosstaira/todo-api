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

@RestController // Anotação que combina @Controller e @ResponseBody, simplificando a criação de APIs REST
@RequestMapping("/api/tarefas") // Define que todos os endpoints nesta classe começarão com /api/tarefas
public class TarefaController {

    // Injeção de Dependência: O Spring vai automaticamente nos dar uma instância de TarefaRepository
    @Autowired
    private TarefaRepository tarefaRepository;

    // Endpoint para LISTAR todas as tarefas
    // Mapeia para requisições GET em /api/tarefas
    @GetMapping
    public List<Tarefa> listarTodas() {
        return tarefaRepository.findAll();
    }

    // Endpoint para CRIAR uma nova tarefa
    // Mapeia para requisições POST em /api/tarefas
    @PostMapping
    public ResponseEntity<Tarefa> criarTarefa(@RequestBody Tarefa tarefa) {
        Tarefa novaTarefa = tarefaRepository.save(tarefa);
        return new ResponseEntity<>(novaTarefa, HttpStatus.CREATED);


    }

    @GetMapping("/{id}")
public ResponseEntity<Tarefa> buscarPorId(@PathVariable Long id) {
    Optional<Tarefa> tarefaOptional = tarefaRepository.findById(id);

    if (tarefaOptional.isEmpty()) {
        // Se a tarefa não for encontrada, retorna um erro 404 Not Found
        return ResponseEntity.notFound().build();
    }

    // Se a tarefa for encontrada, retorna 200 OK com a tarefa no corpo
    return ResponseEntity.ok(tarefaOptional.get());
}

@PutMapping("/{id}")
public ResponseEntity<Tarefa> atualizarTarefa(@PathVariable Long id, @RequestBody Tarefa tarefaDetalhes) {
    Optional<Tarefa> tarefaOptional = tarefaRepository.findById(id);

    if (tarefaOptional.isEmpty()) {
        return ResponseEntity.notFound().build();
    }

    // Pega a tarefa existente do banco de dados
    Tarefa tarefaExistente = tarefaOptional.get();

    // Atualiza os campos com os novos detalhes recebidos no corpo da requisição
    tarefaExistente.setTexto(tarefaDetalhes.getTexto());
    tarefaExistente.setConcluida(tarefaDetalhes.isConcluida());

    // Salva a tarefa atualizada de volta no banco
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

    // Para uma deleção bem-sucedida, é comum retornar uma resposta sem corpo
    // com o status 204 No Content
    return ResponseEntity.noContent().build();
}
}