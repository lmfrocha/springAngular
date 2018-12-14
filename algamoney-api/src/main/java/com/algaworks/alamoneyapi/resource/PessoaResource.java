package com.algaworks.alamoneyapi.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.alamoneyapi.event.RecursoCriadoEvent;
import com.algaworks.alamoneyapi.model.Pessoa;
import com.algaworks.alamoneyapi.repository.PessoaRepository;

@RestController //Controlador JSON dispensa uso de notaçoes adicionais
@RequestMapping("/pessoa") //faz o mapeamento da requisicao tipo meusite.com/pessoas
public class PessoaResource {

	@Autowired //injeta uma implementacao de "pessoas" da anotacao categoriaRepository nesta classe resource
	private PessoaRepository pessoaRepository;
	
	@GetMapping //mapeamento do "get" para a "/pessoa"
	public List<Pessoa> listar() {
		return pessoaRepository.findAll();
	}
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@PostMapping 							//o valid=validação do campo pessoa
	public ResponseEntity<Pessoa> criar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) { 
		Pessoa pessoaSalva = pessoaRepository.save(pessoa);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalva.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva); //aqui retorna para o consumo da API o endereco da pessoa salva
	}
	
	@GetMapping("/{codigo}") //mapea no endereco principal meusite.com/pessoa + este mapeamento, o primeiro @getmapping sempre vai ser
	public ResponseEntity<Pessoa> buscarPelaPessoa (@PathVariable Long codigo) { //o principal no caso meusit.com/pessoa/ <--- este mapeamennto
		
//		Melhoria no código
		Pessoa pessoa = pessoaRepository.findOne(codigo);
		return pessoa != null ? ResponseEntity.ok(pessoa) : ResponseEntity.notFound().build();
		
//		Primeira versap do tratamento caso nao encontre a pessoa
//		if(pessoaRepository.findOne(codigo) != null) {
//			return ResponseEntity.ok(pessoaRepository.findOne(codigo));
//		}else {
//			return ResponseEntity.notFound().build();
//		}		
	}
	
	
}
