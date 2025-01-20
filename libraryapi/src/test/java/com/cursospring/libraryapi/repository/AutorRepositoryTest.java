package com.cursospring.libraryapi.repository;

import com.cursospring.libraryapi.model.Autor;
import com.cursospring.libraryapi.model.GeneroLivro;
import com.cursospring.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository repository;

    @Autowired
    LivroRepository livroRepository;

    @Test
    public void salvarTest() {
        Autor autor = new Autor();
        autor.setNome("Maria");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1951, 2, 25));

        Autor autorSalvo = repository.save(autor);
        System.out.println("Autor salvo: " + autorSalvo);
    }

    @Test
    public void atualizarTest() {

        var id = UUID.fromString("b468c14f-369e-4a0c-b822-b28992a544df");

        Optional<Autor> possivelAutor = repository.findById(id);

        if(possivelAutor.isPresent()) {
            Autor autorEncontrado = possivelAutor.get();
            System.out.println("Dados do Autor: ");
            System.out.println(autorEncontrado);

            autorEncontrado.setNome("Mariana");

            repository.save(autorEncontrado);
        }

    }

    @Test
    public void listarTest() {
        List<Autor> lista = repository.findAll();
        lista.forEach(System.out::println);
    }

    @Test
    public void countTest() {
        System.out.println("Contagem de autores: " + repository.count());
    }

    @Test
    public void deletePorIdTest() {
        var id = UUID.fromString("b468c14f-369e-4a0c-b822-b28992a544df");
        repository.deleteById(id);
    }

    @Test
    public void deleteTest() {
        var id = UUID.fromString("10ae88d5-83c5-4ede-8225-917486742398");
        var maria = repository.findById(id);
        maria.ifPresent(autor -> repository.delete(autor));
    }

    @Test
    void salvarAutorComLivrosTest() {
        Autor autor = new Autor();
        autor.setNome("Müller");
        autor.setNacionalidade("Alemã");
        autor.setDataNascimento(LocalDate.of(1970, 4, 10));

        Livro livro = new Livro();
        livro.setIsbn("95559-11111");
        livro.setPreco(BigDecimal.valueOf(56));
        livro.setGenero(GeneroLivro.FANTASIA);
        livro.setTitulo("Cavalo que voa na lua");
        livro.setDataPublicacao(LocalDate.of(2015, 10, 20));
        livro.setAutor(autor);

        Livro livro2 = new Livro();
        livro2.setIsbn("95559-22222");
        livro2.setPreco(BigDecimal.valueOf(37));
        livro2.setGenero(GeneroLivro.FANTASIA);
        livro2.setTitulo("Homem foice");
        livro2.setDataPublicacao(LocalDate.of(2010, 11, 20));
        livro2.setAutor(autor);

        autor.setLivros(new ArrayList<>());
        autor.getLivros().add(livro);
        autor.getLivros().add(livro2);

        repository.save(autor);

        // Com o cascade, ao salvar o autor, os livros dele também são salvos
       // livroRepository.saveAll(autor.getLivros());
    }

    @Test
    void listarLivrosAutor() {
        var id = UUID.fromString("1e4452e4-9715-438b-a340-b12d14549a02");
        var autor = repository.findById(id).get();

        List<Livro> livrosLista = livroRepository.findByAutor(autor);
        autor.setLivros(livrosLista);

        autor.getLivros().forEach(System.out::println);
    }
}
