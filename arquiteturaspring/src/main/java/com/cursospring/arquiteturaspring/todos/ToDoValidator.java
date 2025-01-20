package com.cursospring.arquiteturaspring.todos;

import org.springframework.stereotype.Component;

@Component
public class ToDoValidator {

    // Injetando o repository
    private ToDoRepository repository;

    public ToDoValidator(ToDoRepository repository) {
        this.repository = repository;
    }

    // Método para validar se a descrição é diferente
    public void validar(ToDoEntity todo) {
        if(existeToDoComDescricao(todo.getDescricao())) {
            throw new IllegalArgumentException("Já existe um TOTO com essa descrição!");
        }
    }

    private boolean existeToDoComDescricao(String descricao) {
        return repository.existsByDescricao(descricao);
    }
}
