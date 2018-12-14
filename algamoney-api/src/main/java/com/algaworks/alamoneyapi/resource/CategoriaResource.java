package com.algaworks.alamoneyapi.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.alamoneyapi.event.RecursoCriadoEvent;
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
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@PostMapping 							//o valid=validação do campo categoria
	public ResponseEntity<Categoria> criar(@Valid @RequestBody Categoria categoria, HttpServletResponse response) { 
		Categoria categoriaSalva = categoriaRepository.save(categoria);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, categoria.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva); //aqui retorna para o consumo da API o endereco da categoria salva
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Categoria> buscarPelaCategoria (@PathVariable Long codigo) {
		
//		Refatoring do metodo
		Categoria categoria = categoriaRepository.findOne(codigo);
		return categoria !=null ? ResponseEntity.ok(categoria) : ResponseEntity.notFound().build();
		
//		Metodo antigo
//		if(categoriaRepository.findOne(codigo) != null) {
//			return ResponseEntity.ok(categoriaRepository.findOne(codigo));
//		}else {
//			return ResponseEntity.notFound().build();
//		}		
	}
	
	@DeleteMapping("/{codigo}")
	public void remover(@PathVariable Long codigo){
		Categoria categoriaExist = categoriaRepository.findOne(codigo);
		if(categoriaExist == null) {
			throw new EmptyResultDataAccessException(1);
		}
		categoriaRepository.delete(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Categoria> atualiza(@PathVariable Long codigo, Categoria categoria) {
		Categoria categoriaExist = categoriaRepository.findOne(codigo);
		if(categoriaExist == null) {
			throw new EmptyResultDataAccessException(1);
		}
		BeanUtils.copyProperties(categoria, categoriaExist, "codigo");
		categoriaRepository.save(categoriaExist);
		return ResponseEntity.ok(categoriaExist);
	}
}
