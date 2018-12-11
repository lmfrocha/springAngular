package com.algaworks.alamoneyapi.resource;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.algaworks.alamoneyapi.model.Categoria;
import com.algaworks.alamoneyapi.repository.CategoriaRepository;

@RestController //Controlador JSON dispensa uso de notaçoes adicionais
@RequestMapping("/categorias") //faz o mapeamento da requisicao tipo meusite.com/categorias
public class CategoriaResource {

	@Autowired //injeta uma implementacao de "Categoria" da anotacao categoriaRepository nesta classe resource
	private CategoriaRepository categoriaRepository;
	
	@GetMapping //mapeamento do "get" para a "/categorias"
	public List<Categoria> listar() {
		return categoriaRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Categoria> criar(@RequestBody Categoria categoria, HttpServletResponse response) {
		Categoria categoriaSalva = categoriaRepository.save(categoria);

		URI uri = ServletUriComponentsBuilder //Classe do Spring para pegar a URI de response da chamada
				.fromCurrentRequestUri().path("/{codigo}") //coloca o codigo na uri de response
				.buildAndExpand(categoriaSalva.getCodigo()) //pega o codigo da "/categoria" cadastrada
				.toUri(); //tipo toString.. etc
		
		response.setHeader("location", uri.toASCIIString()); //Aqui e adicionado o local do contexto
		
		return ResponseEntity.created(uri).body(categoriaSalva); //aqui retorna para o consumo da API o endereco da categoria salva
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Categoria> buscarPelaCategoria (@PathVariable Long codigo) {
		
		if(categoriaRepository.findOne(codigo) != null) {
			return ResponseEntity.ok(categoriaRepository.findOne(codigo));
		}else {
			return ResponseEntity.notFound().build();
		}		
	}
	
	
}
