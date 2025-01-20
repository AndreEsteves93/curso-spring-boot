package com.cursospring.arquiteturaspring.montadora;

public class Chave {

    // Atributos
    private Montadora montadora;
    private String tipo;

    // Métodos especiais
    public Montadora getMontadora() {
        return montadora;
    }

    public void setMontadora(Montadora montadora) {
        this.montadora = montadora;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
