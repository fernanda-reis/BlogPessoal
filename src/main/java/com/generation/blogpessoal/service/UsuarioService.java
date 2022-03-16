package com.generation.blogpessoal.service;

import java.nio.charset.Charset;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.generation.blogpessoal.model.UserLogin;
import com.generation.blogpessoal.model.Usuario;
import com.generation.blogpessoal.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repository;

	public Optional<Usuario> CadastrarUsuario(Usuario usuario) {
		Optional<Usuario> retorno;
		Optional<Usuario> user = repository.findByUsuario(usuario.getUsuario());

		if (user.isEmpty()) {
			String senhaEncoder = criptografarSenha(usuario.getSenha());
			usuario.setSenha(senhaEncoder);
			repository.save(usuario);
			retorno = Optional.of(usuario);
		
		} else {retorno = Optional.empty();}

		return retorno;
		
	}

	public String criptografarSenha(String senha) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(senha);
	}
	
	private boolean compararSenhas(String senhaDigitada, String senhaBanco) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		return encoder.matches(senhaDigitada, senhaBanco);

	}
	
	public Optional<Usuario> AtualizarUsuario(Usuario Usuario){
		Optional<Usuario> UsuarioVerifica = repository.findById(Usuario.getId());
		if(UsuarioVerifica.isPresent()) {
			if(compararSenhas(Usuario.getSenha(), UsuarioVerifica.get().getSenha())) {
				UsuarioVerifica.get().setUsuario(Usuario.getUsuario());
				UsuarioVerifica.get().setFoto(Usuario.getFoto());
				UsuarioVerifica.get().setNome(Usuario.getNome());
				UsuarioVerifica.get().setTipo(Usuario.getTipo());
				
				repository.save(UsuarioVerifica.get());
				return UsuarioVerifica;
			}
		}
		
		return Optional.empty();
	}
	
	
	public Optional<UserLogin> Logar(Optional<UserLogin> user) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		Optional<Usuario> usuario = repository.findByUsuario(user.get().getUsuario());

		if (usuario.isPresent()) {
			if(encoder.matches(user.get().getSenha(), usuario.get().getSenha())) {

				String auth = user.get().getUsuario() + ":" + user.get().getSenha();
				byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));

				String authHeader = "Basic " + new String(encodedAuth);

				user.get().setToken(authHeader);
				
				user.get().setId(usuario.get().getId());

				user.get().setNome(usuario.get().getNome());
				
				user.get().setFoto(usuario.get().getFoto());

				user.get().setSenha(usuario.get().getSenha());
				
				user.get().setTipo(usuario.get().getTipo());

				return user;
			}
		}

		return null;
	}

}
