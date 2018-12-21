package co.edu.itli.campus.core.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by enunezt on 11/12/18.
 */
@Entity
@Table(name = "autorizaciones")
public class Autorizacion implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(position = 0)
    private Long id;

    @Column(length = 60)
    @ApiModelProperty(position = 1)
    private String nombre;

    public Autorizacion() {

    }

    public Autorizacion(String nombre) {
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    
    public String getAuthority() {
        return "A"+getId();
      }

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}