package com.generation.blogpessoal.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.blogpessoal.model.Tema;
import com.generation.blogpessoal.repository.TemaRepository;

@RestController
@RequestMapping("/tema")
@CrossOrigin("*")
public class TemaController {

	@Autowired
	private TemaRepository repository;
	
	@GetMapping
	public ResponseEntity<List<Tema>> findAllTema(){
		List<Tema> list = repository.findAll();
		if(list.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} else {
			return ResponseEntity.ok(list);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Tema> findByIDTema(@PathVariable long id){
		return repository.findById(id)
				.map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/descricao/{descricao}")
	public ResponseEntity<Tema> findByDescricaoTema(@PathVariable String descricao){
		return repository.findByDescricaoContainingIgnoreCase(descricao)
				.map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.noContent().build());
	}
	
	@PostMapping
	public ResponseEntity<Tema> postTema(@RequestBody Tema tema){
		return ResponseEntity.ok(repository.save(tema));
	}
	
	@PutMapping
	public ResponseEntity<Tema> putTema(@RequestBody Tema tema){
		return repository.findById(tema.getId())
				.map(resp -> ResponseEntity.ok(repository.save(tema)))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@SuppressWarnings("rawtypes")
	@DeleteMapping("{id}")
	public ResponseEntity deleteTema(@PathVariable long id) {
		Optional<Tema> optional = repository.findById(id);
		if(optional.isPresent()) {
			repository.deleteById(id);
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	
	
}
