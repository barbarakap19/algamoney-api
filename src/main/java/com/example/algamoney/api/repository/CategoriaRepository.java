// é uma interface que contém diversos métodos pré-implementados para a manipulação de dados de uma entidade 
//como métodos para salvar, deletar, listar e recuperar pelo o ID da classe.

package com.example.algamoney.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.algamoney.api.model.Categoria;


// estender a interface JpaRepository; os tipos passados são a classe da entidade usada; 
//e o tipo do atributo que é o identificador dessa classe

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
