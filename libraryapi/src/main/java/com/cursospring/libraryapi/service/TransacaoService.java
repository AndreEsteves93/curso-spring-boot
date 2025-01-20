package com.cursospring.libraryapi.service;

import com.cursospring.libraryapi.model.Autor;
import com.cursospring.libraryapi.model.GeneroLivro;
import com.cursospring.libraryapi.model.Livro;
import com.cursospring.libraryapi.repository.AutorRepository;
import com.cursospring.libraryapi.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class TransacaoService {

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private LivroRepository livroRepository;

    @Transactional
    public void atualizacaoSemAtualizar() {
        var livro = livroRepository
                .findById(UUID.fromString("e1a77044-cc64-4921-98c4-2c7506e69a7f"))
                .orElse(null);

        livro.setDataPublicacao(LocalDate.of(2020, 3, 21));
        // Como está dentro do transaction não é necessário o save.
        // A entidade fica managed durante a transação e no final dá um commit.
    }

    @Transactional
    public void executar() {
        // Salva o autor
        Autor autor = new Autor();
        autor.setNome("Amélia");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(19750, 10, 16));

        autorRepository.save(autor);

        // Salva o livro
        Livro livro = new Livro();
        livro.setIsbn("95132-94261");
        livro.setPreco(BigDecimal.valueOf(999));
        livro.setGenero(GeneroLivro.CIENCIA);
        livro.setTitulo("Algorithms");
        livro.setDataPublicacao(LocalDate.of(2005, 9, 9));

        livro.setAutor(autor);

        livroRepository.save(livro);

        if(autor.getNome().equals("José")) {
            throw new RuntimeException("Rollback!");
        }
    }
}
