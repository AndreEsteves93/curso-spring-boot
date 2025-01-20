package com.cursospring.libraryapi.repository;

import com.cursospring.libraryapi.model.Autor;
import com.cursospring.libraryapi.model.GeneroLivro;
import com.cursospring.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

// Na classe teste não é necessário definir acesso public, private etc

@SpringBootTest
class LivroRepositoryTest {

    @Autowired
    LivroRepository repository;

    @Autowired
    AutorRepository autorRepository;

    @Test
    void salvarTest() {
        Livro livro = new Livro();
        livro.setIsbn("98969-97869");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("UFO");
        livro.setDataPublicacao(LocalDate.of(1980, 1, 2));

        Autor autor = autorRepository
                .findById(UUID.fromString("3c95e9e7-f716-4aca-8073-326114bc9323"))
                .orElse(null);

        livro.setAutor(autor);

        repository.save(livro);
    }

    // Opção padrão caso o autor ainda não esteja salvo
    @Test
    void salvarLivroEAutorTest() {
        Livro livro = new Livro();
        livro.setIsbn("95132-94261");
        livro.setPreco(BigDecimal.valueOf(999));
        livro.setGenero(GeneroLivro.CIENCIA);
        livro.setTitulo("Algorithms");
        livro.setDataPublicacao(LocalDate.of(2005, 9, 9));

        Autor autor = new Autor();
        autor.setNome("Peter");
        autor.setNacionalidade("British");
        autor.setDataNascimento(LocalDate.of(1960, 10, 14));

        autorRepository.save(autor);

        livro.setAutor(autor);

        repository.save(livro);
    }

    // raramente utilizado
    @Test
    void salvarCascadeTest() {
        Livro livro = new Livro();
        livro.setIsbn("95559-942269");
        livro.setPreco(BigDecimal.valueOf(99));
        livro.setGenero(GeneroLivro.CIENCIA);
        livro.setTitulo("Matemática para leigos");
        livro.setDataPublicacao(LocalDate.of(1999, 10, 20));

        Autor autor = new Autor();
        autor.setNome("Jurema");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1945, 5, 20));

        livro.setAutor(autor);

        repository.save(livro);
    }

    @Test
    void atualizarAutorDoLivro() {
        UUID id = UUID.fromString("0e58d5fe-3cb3-4e1a-8ab0-d01baf8eeef0");
        var livroParaAtualizar = repository.findById(id).orElse(null);

        UUID idAutor = UUID.fromString("6afddbd7-c717-4783-8189-e29760fcf8d8");
        Autor jurema = autorRepository.findById(idAutor).orElse(null);

        livroParaAtualizar.setAutor(jurema);

        repository.save(livroParaAtualizar);
    }

    @Test
    void deletarPorId() {
        UUID id = UUID.fromString("e4fc5d8b-e235-45ee-9a75-6aec4f7c6c55");
        repository.deleteById(id);
    }

    @Test
    @Transactional // Escolher o do Spring. Não o do jakarta
    void buscarLivroTest() {
        UUID id = UUID.fromString("0e58d5fe-3cb3-4e1a-8ab0-d01baf8eeef0");
        Livro livro = repository.findById(id).orElse(null);

        System.out.println("Livro: " + livro.getTitulo());
        System.out.println("Autor: " + livro.getAutor().getNome());
    }

    @Test
    void pesquisaPorTituloTest() {
        List<Livro> listaLivros = repository.findByTitulo("Matemática para leigos");
        listaLivros.forEach(System.out::println);
    }

    @Test
    void pesquisaPorIsbnTest() {
        List<Livro> listaLivros = repository.findByIsbn("95559-94230");
        listaLivros.forEach(System.out::println);
    }

    @Test
    void pesquisarPorTituloAndPrecoTest() {
        List<Livro> listaLivros = repository.findByTituloAndPreco("UFO", BigDecimal.valueOf(100));
        listaLivros.forEach(System.out::println);
    }

    @Test
    void pesquisarPorTituloOrIsbnTest() {
        List<Livro> listaLivros = repository.findByTituloOrIsbn("UFO", "95132-94261");
        listaLivros.forEach(System.out::println);
    }

    @Test
    void pesquisarDataPublicacaoBetweenTest() {
        LocalDate inicio = LocalDate.of(1981, 2, 12);
        LocalDate fim = LocalDate.of(2006, 2, 12);
        List<Livro> listaLivros = repository.findByDataPublicacaoBetween(inicio, fim);
        listaLivros.forEach(System.out::println);
    }

    @Test
    void pesquisaPorContendoEmTituloTest() {
        List<Livro> listaLivros = repository.findByTituloContaining("inter");
        listaLivros.forEach(System.out::println);
    }

    @Test
    void listarLivrosComQueryJPQL() {
        List<Livro> livros = repository.listarTodosOrdenadoPorTituloAndPreco();
        livros.forEach(System.out::println);
    }

    @Test
    void listarAutoresDosLivrosQueryJPQL() {
        List<Autor> autores = repository.listarAutoresDosLivros();
        autores.forEach(System.out::println);
    }

    @Test
    void listarDiferentesLivrosQueryJPQL() {
        List<String> livros = repository.listarNomesDiferentesLivros();
        livros.forEach(System.out::println);
    }

    @Test
    void listarGenerosLivrosAutoresBrasileirosQueryJPQL() {
        List<String> generosLivros = repository.listarGenerosAutoresBrasileiros();
        generosLivros.forEach(System.out::println);
    }

    @Test
    void listarPorGeneroQueryParamTest() {
        List<Livro> listaGeneros = repository.findByGenero(GeneroLivro.FANTASIA, "preco");
        listaGeneros.forEach(System.out::println);
    }

    @Test
    void listarPorGeneroQueryParamPositionalTest() {
        List<Livro> listaGeneros = repository.findByGeneroPositionalParamaters(GeneroLivro.FANTASIA, "preco");
        listaGeneros.forEach(System.out::println);
    }

    @Test
    void deletePorGeneroTest() {
        repository.deleteByGenero(GeneroLivro.CIENCIA);
    }

    @Test
    void updateDataPublicacaoTest() {
        repository.updateDataPublicacao(LocalDate.of(2000, 1, 1), "Matemática intermediária");
    }
}