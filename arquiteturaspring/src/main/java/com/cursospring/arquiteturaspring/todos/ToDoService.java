package com.cursospring.arquiteturaspring.todos;

import org.springframework.stereotype.Service;

@Service
public class ToDoService {

    // Injetando repository, validator e mailSender
    private ToDoRepository repository;
    private ToDoValidator validator;
    private MailSender mailSender;

    // Construtor para o Spring injetar as dependências
    public ToDoService(ToDoRepository repository, ToDoValidator validator, MailSender mailSender) {
        this.repository = repository;
        this.validator = validator;
        this.mailSender = mailSender;
    }

    public ToDoEntity salvar(ToDoEntity novoToDo) {
        validator.validar(novoToDo);
        return repository.save(novoToDo);
    }

    public void atualizarStatus(ToDoEntity todo) {
        repository.save(todo);
        String status = todo.getConcluido() == Boolean.TRUE ? "Concluido" : "Não concluido";
        mailSender.enviar("ToDo " + todo.getDescricao() + " foi atualizado para " + status);
    }

    public ToDoEntity buscarPorId(Integer id) {
        return repository.findById(id).orElse(null);
    }
}
