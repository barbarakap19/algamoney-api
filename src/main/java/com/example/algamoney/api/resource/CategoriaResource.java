

package com.example.algamoney.api.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.algamoney.api.event.RecursoCriadoEvent;
import com.example.algamoney.api.model.Categoria;
import com.example.algamoney.api.repository.CategoriaRepository;



@RestController
@RequestMapping("/categorias") // caminho para acessar esse controlador
public class CategoriaResource {
	
	// É adicionado um atributo do tipo CategoriaRepository, que é o repositório criado
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
    private List<Categoria> listar() {
	return categoriaRepository.findAll();
	
	
}
	// método para adicionar uma categoria, que será acessado quando a requisição for do tipo POST
	// recebe também como parâmetro o nome de uma categoria.
	//a categoria será enviada pela requisição, será cadastrado no banco de dados utilizando a interface repositório. 
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Categoria> criar(@Valid @RequestBody Categoria categoria, HttpServletResponse response) {
		Categoria categoriaSalva = categoriaRepository.save(categoria);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalva.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
		
	
	}
	
	// listar as categorias de um determinado codigo e que será acessado quando a requisição para esse controlador 
	//for do tipo GET
	//  receberá como parâmetro o codigo, por onde é feita uma busca no banco de dados para encontrar todos as 
	//categorias. Caso sejam encontrada, essa lista é adicionada no model para que seja mostrada depois 
	//na tela da aplicação
	
	@GetMapping("/{codigo}")
	public Categoria buscarPeloCodigo(@PathVariable Long codigo) {
		return categoriaRepository.findOne(codigo);
		}
	}
	

