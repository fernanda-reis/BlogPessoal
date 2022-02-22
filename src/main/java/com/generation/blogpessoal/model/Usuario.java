package com.generation.blogpessoal.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tb_usuario")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = "O atributo nome é obrigatório.")
	@Size(min = 2, max = 100, message = "O nome deve conter pelo menos 2 caracteres.")
	private String nome;
	
	@Size(max = 5000, message = "O link da foto não pode ser maior do que 5000 caracteres")
	private String foto;
	
	@Email(message = "O atributo usuario deve ser um email.")
	@NotNull(message = "O atributo usuario é obrigatório.")
	@Size(min = 4, max = 100, message = "O usuario deve conter pelo menos 5 caracteres.")
	private String usuario;
	
	@NotNull(message = "O atributo senha é obrigatório.")
	@Size(min = 4, message = "A senha deve conter pelo menos 5 caracteres.")
	private String senha;
	
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.REMOVE)
	@JsonIgnoreProperties("usuario")
	private List<Postagem> postagens;
	
	private String tipo;
	
	
	public Usuario(long id, String nome, String foto, String usuario, String senha, String tipo) {
		this.id = id;
		this.nome = nome;
		this.foto = foto;
		this.usuario = usuario;
		this.senha = senha;
		this.tipo = tipo;
	}

	public Usuario() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public List<Postagem> getPostagens() {
		return postagens;
	}

	public void setPostagens(List<Postagem> postagens) {
		this.postagens = postagens;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	

}
