package com.algaworks.alamoneyapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algaworks.alamoneyapi.model.Categoria;
import com.algaworks.alamoneyapi.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

}
