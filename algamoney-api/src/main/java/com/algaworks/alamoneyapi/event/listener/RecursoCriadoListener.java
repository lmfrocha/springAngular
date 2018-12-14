package com.algaworks.alamoneyapi.event.listener;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.algaworks.alamoneyapi.event.RecursoCriadoEvent;

@Component
public class RecursoCriadoListener implements ApplicationListener<RecursoCriadoEvent>{

	@Override
	public void onApplicationEvent(RecursoCriadoEvent recursoCriadoEvent) {
		HttpServletResponse	response = recursoCriadoEvent.getResponse();
		Long codigo = recursoCriadoEvent.getCodigo();
		
		adicionarHeaderLocation(response, codigo);
		
	}

	private void adicionarHeaderLocation(HttpServletResponse response, Long codigo) {
		URI uri = ServletUriComponentsBuilder //Classe do Spring para pegar a URI de response da chamada
				.fromCurrentRequestUri().path("/{codigo}") //coloca o codigo na uri de response
				.buildAndExpand(codigo) //pega o codigo da "/categoria" cadastrada
				.toUri(); //tipo toString.. etc
		response.setHeader("Location", uri.toASCIIString());
	}

}
