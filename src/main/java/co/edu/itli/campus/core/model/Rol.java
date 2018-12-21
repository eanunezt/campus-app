package co.edu.itli.campus.core.model;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import org.springframework.security.core.GrantedAuthority;

import co.edu.itli.campus.register.model.ContactoPrograma;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by eanunezt on 01/08/17.
 */
@Entity
@Table(name = "roles")
public class Rol implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(position = 0)
    private Long id;

    @Column(length = 60)
    @ApiModelProperty(position = 1)
    private String nombre;
    
    @OneToMany(mappedBy="rol",cascade=CascadeType.DETACH,fetch = FetchType.LAZY)
    @ApiModelProperty(position = 2)
    private Set<RolAutorizacion> autorizaciones = new HashSet<>();

    public Rol() {

    }
    
   

    public Rol(Long id) {
		super();
		this.id = id;
	}



	public Rol(String name) {
        this.nombre = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    
    
    public String getAuthority() {
        return "R"+getId();
      }

	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return ""+id+"-"+nombre;
	}



	public String getNombre() {
		return nombre;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public Set<RolAutorizacion> getAutorizaciones() {
		return autorizaciones;
	}



	public void setAutorizaciones(Set<RolAutorizacion> autorizaciones) {
		this.autorizaciones = autorizaciones;
	}



}
