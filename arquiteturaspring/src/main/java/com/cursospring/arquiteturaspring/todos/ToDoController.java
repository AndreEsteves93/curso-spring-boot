package com.cursospring.arquiteturaspring.todos;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("todos")
public class ToDoController {

    // Atributos (Injetando service)
    private ToDoService service;

    // Construtor (Injetando service)
    public ToDoController(ToDoService service) {
        this.service = service;
    }

    @PostMapping
    public ToDoEntity salvar(@RequestBody ToDoEntity todo) {
        try {
            return this.service.salvar(todo);
        } catch (IllegalArgumentException e) {
            var mensagemErro = e.getMessage();
            throw new ResponseStatusException(HttpStatus.CONFLICT, mensagemErro);
        }
    }

    @PutMapping("{id}")
    public void atualizarStatus(@PathVariable("id") Integer id,
                                @RequestBody ToDoEntity todo) {
        todo.setId(id);
        service.atualizarStatus(todo);
    }

    @GetMapping("{id}")
    public ToDoEntity buscar(@PathVariable("id") Integer id) {
        return service.buscarPorId(id);
    }
}
