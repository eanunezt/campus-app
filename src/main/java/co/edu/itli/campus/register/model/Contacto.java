/**
 * 
 */
package co.edu.itli.campus.register.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.NaturalId;

import co.edu.itli.campus.core.enums.Estado;
import co.edu.itli.campus.core.model.EntidadAuditada;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author eanunezt
 *
 */
@Entity
@Table(name = "contactos", indexes= {@Index(name = "email_contact_ix",  columnList="email", unique = true)})

public class Contacto extends EntidadAuditada {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(position = 0)
    private Long id;

    @Column(length = 60)
    @ApiModelProperty(position = 1)
    private String nombre;
    
    @NaturalId
    @NotNull
    @Size(max = 60)
    @Email
    @ApiModelProperty(position = 2)
    private String email;
    
    @NotBlank
    @ApiModelProperty(position = 3)
    private String numTelefono;
    
    @Column(length = 60)
    @ApiModelProperty(position = 4)
    private String organizacion;
    
    @Column(length = 60)
    @ApiModelProperty(position = 5)
    private String direccion;
    
    @Column(length = 200)
    @ApiModelProperty(position = 6)
    private String observaciones;
    
    @Column(length = 100)
    @ApiModelProperty(position = 7)
    private String urlAvatar;
    
    
    @ApiModelProperty(position = 8)
    @Enumerated(EnumType.ORDINAL)
    private Estado estado;
   

	@OneToMany(mappedBy="contacto",cascade=CascadeType.DETACH)
    @ApiModelProperty(position = 6)
    private Set<ContactoPrograma> programas= new HashSet<ContactoPrograma>();

	/**
	 * 
	 */
	public Contacto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	 
   
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}



	public String getNombre() {
		return nombre;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getNumTelefono() {
		return numTelefono;
	}



	public void setNumTelefono(String numTelefono) {
		this.numTelefono = numTelefono;
	}



	public String getOrganizacion() {
		return organizacion;
	}



	public void setOrganizacion(String organizacion) {
		this.organizacion = organizacion;
	}



	public String getDireccion() {
		return direccion;
	}



	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}



	public String getObservaciones() {
		return observaciones;
	}



	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}



	public Estado getEstado() {
		return estado;
	}



	public void setEstado(Estado estado) {
		this.estado = estado;
	}



	public Set<ContactoPrograma> getProgramas() {
		return programas;
	}



	public void setProgramas(Set<ContactoPrograma> programas) {
		this.programas = programas;
	}



	
	

}
