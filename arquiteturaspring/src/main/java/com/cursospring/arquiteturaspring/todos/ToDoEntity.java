package com.cursospring.arquiteturaspring.todos;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_todo")
public class ToDoEntity {

    // Atributos
    @Id //Primary Key
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Auto increment
    private Integer id;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "fl_concluido")
    private Boolean concluido;

    // Métodos especiais
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getConcluido() {
        return concluido;
    }

    public void setConcluido(Boolean concluido) {
        this.concluido = concluido;
    }
}
