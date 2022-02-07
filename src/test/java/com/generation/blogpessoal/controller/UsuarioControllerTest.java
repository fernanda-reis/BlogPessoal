package com.generation.blogpessoal.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.generation.blogpessoal.model.Usuario;
import com.generation.blogpessoal.service.UsuarioService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioControllerTest {

	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Test
	@Order(1)
	@DisplayName("Cadastrar um usuário")
	public void deveCriarUmUsuario() {
		HttpEntity<Usuario> requisicao = new HttpEntity<Usuario>(new Usuario(0L, 
				"Paulo Antunes", 
				"https://conteudo.imguol.com.br/c/entretenimento/29/2015/08/25/mordecai-e-rigby-personagens-do-desenho-apenas-um-show-1440532277679_615x470.jpg", 
				"paulo_antunes@email.com.br", "123456"));
		
		ResponseEntity<Usuario> resposta = testRestTemplate
				.exchange("/usuarios/cadastrar", HttpMethod.POST, requisicao, Usuario.class);
		
		assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
		assertEquals(requisicao.getBody().getNome(), resposta.getBody().getNome());
		assertEquals(requisicao.getBody().getUsuario(), resposta.getBody().getUsuario());
	}
	
	@Test
	@Order(2)
	@DisplayName("Não deve permitir duplicação de usuário")
	public void naoDeveDuplicarUsuario() {
		usuarioService.CadastrarUsuario(new Usuario(0L, 
				"Maria Soares", 
				"https://conteudo.imguol.com.br/c/entretenimento/29/2015/08/25/mordecai-e-rigby-personagens-do-desenho-apenas-um-show-1440532277679_615x470.jpg",
				"maria_silva@email.com.br", "123456"));
		
		HttpEntity<Usuario> requisicao = new HttpEntity<Usuario>(new Usuario(0L, 
				"https://conteudo.imguol.com.br/c/entretenimento/29/2015/08/25/mordecai-e-rigby-personagens-do-desenho-apenas-um-show-1440532277679_615x470.jpg",
				"Maria Soares", "maria_silva@email.com.br", "123456"));
		
		ResponseEntity<Usuario> resposta = testRestTemplate
				.exchange("/usuarios/cadastrar", HttpMethod.POST, requisicao, Usuario.class);
		
		assertEquals(HttpStatus.BAD_REQUEST, resposta.getStatusCode());
	}
	
	@Test
	@Order(3)
	@DisplayName("Alterar um usuario")
	public void deveAtualizarUmUsuario() {
		
		Optional<Usuario> usuarioCreate = usuarioService.CadastrarUsuario(new Usuario(0L,
				"Juliana Andrews", 
				"https://conteudo.imguol.com.br/c/entretenimento/29/2015/08/25/mordecai-e-rigby-personagens-do-desenho-apenas-um-show-1440532277679_615x470.jpg",
				"juliana_andrews@email.com", "juliana123"));
		
		
		Usuario usuarioUpdate = new Usuario(usuarioCreate.get().getId(),
				"Juliana Andrews Ramos", 
				"https://conteudo.imguol.com.br/c/entretenimento/29/2015/08/25/mordecai-e-rigby-personagens-do-desenho-apenas-um-show-1440532277679_615x470.jpg",
				"juliana_ramos@email.com", "juliana123");
		
		HttpEntity<Usuario> requisicao = new HttpEntity<Usuario>(usuarioUpdate);
		
		ResponseEntity<Usuario> resposta = testRestTemplate
				.withBasicAuth("root","root")
				.exchange("/usuarios/atualizar", HttpMethod.PUT, requisicao, Usuario.class);
		
		assertEquals(HttpStatus.OK, resposta.getStatusCode());
		assertEquals(usuarioUpdate.getNome(), resposta.getBody().getNome());
		assertEquals(usuarioUpdate.getUsuario(), resposta.getBody().getUsuario());
	}
	
	@Test
	@Order(4)
	@DisplayName("Listar todos os usuários")
	public void deveMostrarTodosUsuarios() {
		usuarioService.CadastrarUsuario(new Usuario(0L,
				"Sabrina Sanches", 
				"https://conteudo.imguol.com.br/c/entretenimento/29/2015/08/25/mordecai-e-rigby-personagens-do-desenho-apenas-um-show-1440532277679_615x470.jpg",
				"sabrina_sanches@email.com.br", "sabrian123"));
		
		usuarioService.CadastrarUsuario(new Usuario(0L,
				"Ricardo Marques", "https://conteudo.imguol.com.br/c/entretenimento/29/2015/08/25/mordecai-e-rigby-personagens-do-desenho-apenas-um-show-1440532277679_615x470.jpg",
				"ricardo_marques@email.com.br", "ricado123"));
		
		ResponseEntity<String> resposta = testRestTemplate
				.withBasicAuth("root", "root")
				.exchange("/usuarios", HttpMethod.GET, null, String.class);
		
		assertEquals(HttpStatus.OK, resposta.getStatusCode());
	}
}
