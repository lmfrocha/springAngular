package com.algaworks.alamoneyapi.resource;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.algaworks.alamoneyapi.model.Pessoa;
import com.algaworks.alamoneyapi.repository.PessoaRepository;

@RestController //Controlador JSON dispensa uso de notaçoes adicionais
@RequestMapping("/pessoa") //faz o mapeamento da requisicao tipo meusite.com/categorias
public class PessoaResource {

	@Autowired //injeta uma implementacao de "Categoria" da anotacao categoriaRepository nesta classe resource
	private PessoaRepository pessoaRepository;
	
	@GetMapping //mapeamento do "get" para a "/categorias"
	public List<Pessoa> listar() {
		return pessoaRepository.findAll();
	}
	
	@PostMapping 							//o valid=validação do campo categoria
	public ResponseEntity<Pessoa> criar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) { 
		Pessoa pessoaSalva = pessoaRepository.save(pessoa);

		URI uri = ServletUriComponentsBuilder //Classe do Spring para pegar a URI de response da chamada
				.fromCurrentRequestUri().path("/{codigo}") //coloca o codigo na uri de response
				.buildAndExpand(pessoaSalva.getCodigo()) //pega o codigo da "/categoria" cadastrada
				.toUri(); //tipo toString.. etc
		
		response.setHeader("location", uri.toASCIIString()); //Aqui e adicionado o local do contexto
		
		return ResponseEntity.created(uri).body(pessoaSalva); //aqui retorna para o consumo da API o endereco da categoria salva
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Pessoa> buscarPelaPessoa (@PathVariable Long codigo) {
		
		if(pessoaRepository.findOne(codigo) != null) {
			return ResponseEntity.ok(pessoaRepository.findOne(codigo));
		}else {
			return ResponseEntity.notFound().build();
		}		
	}
	
	
}
