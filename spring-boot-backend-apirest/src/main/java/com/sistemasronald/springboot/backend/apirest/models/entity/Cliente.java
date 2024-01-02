package com.sistemasronald.springboot.backend.apirest.models.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table( name = "clientes")
public class Cliente implements Serializable {

	//ATRIBUTOS
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "no puede estar vacío.")
	@Size(min = 2, max = 20, message="el tamaño tiene que estar entre 4 y 12 caracteres.")
	@Column(nullable=false)
	private String nombre;

	@NotEmpty(message = "no puede estar vacío.")
	private String apellido;

	@NotEmpty(message = "no puede estar vacío.")
	@Email(message="no es una dirección de correo bien formada.")
	@Column(nullable=false, unique=false)
	private String email;
	
	@NotNull(message = "no puede estar vacío.")
	@Column(name = "create_at")
	@Temporal(TemporalType.DATE)
	private Date createAt;
	
	private String foto;  
	
	@NotNull(message="la región no puede ser vacia")
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="region_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Region region;
	
	
	//MÉTODOS

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	
	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	private static final long serialVersionUID = 1L;

}
