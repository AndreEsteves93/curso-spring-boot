package com.cursospring.arquiteturaspring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ExemploValue {

    // Lendo uma propriedade do application.yml
    @Value("${app.config.variavel}")
    private String variavel;

    public void imprimirVariavel() {
        System.out.println(variavel);
    }

}
