package com.generation.blogpessoal.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.generation.blogpessoal.model.Usuario;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioRepositoryTest {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@BeforeAll
	void start() {
		
		usuarioRepository.save(new Usuario(0L, "João da Silva", "https://conteudo.imguol.com.br/c/entretenimento/29/2015/08/25/mordecai-e-rigby-personagens-do-desenho-apenas-um-show-1440532277679_615x470.jpg", "joao@email.com", "123456"));
		
		usuarioRepository.save(new Usuario(0L, "Manuela da Silva", "https://conteudo.imguol.com.br/c/entretenimento/29/2015/08/25/mordecai-e-rigby-personagens-do-desenho-apenas-um-show-1440532277679_615x470.jpg","manuela@email.com", "123456"));
		
		usuarioRepository.save(new Usuario(0L, "Adriana da Silva", "https://conteudo.imguol.com.br/c/entretenimento/29/2015/08/25/mordecai-e-rigby-personagens-do-desenho-apenas-um-show-1440532277679_615x470.jpg", "adriana@email.com", "123456"));
		
		usuarioRepository.save(new Usuario(0L, "Paulo Antunes", "https://conteudo.imguol.com.br/c/entretenimento/29/2015/08/25/mordecai-e-rigby-personagens-do-desenho-apenas-um-show-1440532277679_615x470.jpg", "paulo@email.com", "123456"));
		
	}
	
	@Test
	@DisplayName("Retorna 1 usuario")
	public void deveRetornarUmUsuario() {
		
		Optional<Usuario> usuario = usuarioRepository.findByUsuario("joao@email.com");
		assertTrue(usuario.get().getUsuario().equals("joao@email.com"));
	}
	
	@Test
	@DisplayName("Retorna 3 usuarios")
	public void deveRetornarTresUsuarios() {
		List<Usuario> listaUsuarios = usuarioRepository.findAllByNomeContainingIgnoreCase("Silva");
		assertEquals(3, listaUsuarios.size());
		assertTrue(listaUsuarios.get(0).getNome().equals("João da Silva"));
		assertTrue(listaUsuarios.get(1).getNome().equals("Manuela da Silva"));
		assertTrue(listaUsuarios.get(2).getNome().equals("Adriana da Silva"));
	}
	
	@AfterAll
	public void end() {
		usuarioRepository.deleteById(1L);
		usuarioRepository.deleteById(2L);
		usuarioRepository.deleteById(3L);
		usuarioRepository.deleteById(4L);

	}
}
