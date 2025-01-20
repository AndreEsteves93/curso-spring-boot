package com.cursospring.libraryapi.repository;

import com.cursospring.libraryapi.model.Autor;
import com.cursospring.libraryapi.model.GeneroLivro;
import com.cursospring.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID> {

    // Query Method
    // Esse método faz um select * from livro where id_autor = id
    List<Livro> findByAutor(Autor autor);

    // select * from livro where titulo = titulo
    List<Livro> findByTitulo(String titulo);

    // select * from livro where isbn = isbn
    List<Livro> findByIsbn(String isbn);

    // select * from livro where titulo = titulo AND preco = preco
    List<Livro> findByTituloAndPreco(String titulo, BigDecimal preco);

    // select * from livro where titulo = titulo OR isbn = isbn
    List<Livro> findByTituloOrIsbn(String titulo, String isbn);

    // select * from livro where data_publicacao between ? and ?
    List<Livro> findByDataPublicacaoBetween(LocalDate inicio, LocalDate fim);

    // select * from livro where titulo like '%?%'
    List<Livro> findByTituloContaining(String parteTitulo);

    // JPQL -> referencia as entidades e as propriedades
    // select l.* from livro as l order by l.titulo, l.preco
    @Query(" select l from Livro as l order by l.titulo, l.preco ")
    List<Livro> listarTodosOrdenadoPorTituloAndPreco();

    // select a.* from livro as l join autor as a on a.id = l.id_autor
    @Query(" select a from Livro as l join l.autor a ")
    List<Autor> listarAutoresDosLivros();

    // select distinct l.* from livro l
    @Query(" select distinct l.titulo from Livro l ")
    List<String> listarNomesDiferentesLivros();

    @Query("""
           select l.genero
           from Livro l
           join l.autor a
           where a.nacionalidade = 'Brasileira'
           order by l.genero
           """)
    List<String> listarGenerosAutoresBrasileiros();

    // query com parametros. Named Parameters
    @Query("""
           select l
           from Livro l
           where l.genero = :genero
           order by :paramOrdenacao
           """)
    List<Livro> findByGenero(
            @Param("genero") GeneroLivro generoLivro,
            @Param("paramOrdenacao") String nomePropriedade
    );

    // Positional Parameters
    @Query("""
           select l
           from Livro l
           where l.genero = ?1
           order by ?2
           """)
    List<Livro> findByGeneroPositionalParamaters(GeneroLivro generoLivro, String nomePropriedade);

    // delete por genero
    @Modifying // necessário para modificar dados no banco
    @Transactional // necessário abrir uma transação para alterar os dados
    @Query(" delete from Livro where genero = ?1 ")
    void deleteByGenero(GeneroLivro generoLivro);

    // update na data de publicação
    @Modifying // necessário para modificar dados no banco
    @Transactional // necessário abrir uma transação para alterar os dados
    @Query(" update Livro set dataPublicacao = ?1 where titulo = ?2 ")
    void updateDataPublicacao(LocalDate novaData, String titulo);
}
