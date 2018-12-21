/**
 * 
 */
package co.edu.itli.campus.register.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import co.edu.itli.campus.core.enums.Estado;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author eanunezt
 *
 */
@Entity
@Table(name = "programas", indexes = {@Index(name = "name_program_ix",  columnList="nombre", unique = true),
		@Index(name = "name_short_program_ix",  columnList="nombreCorto", unique = true)})
public class Programa implements Serializable {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @ApiModelProperty(position = 0)
	    private Long id;

	    @Column(length = 60)
	    @ApiModelProperty(position = 1)
	    private String nombreCorto;
	    
	    @Column(length = 100)
	    @ApiModelProperty(position = 2)
	    private String nombre;
	    
	    
	    @Column(length = 2000)
	    @ApiModelProperty(position = 3)
	    private String descripcion;
	    
	    
	    
	    @ApiModelProperty(position = 4)
	    @Enumerated(EnumType.ORDINAL)
	    private Estado estado;
	    

		public Programa() {
			super();
			// TODO Auto-generated constructor stub
		}


		public Long getId() {
			return id;
		}


		public void setId(Long id) {
			this.id = id;
		}


		public String getNombreCorto() {
			return nombreCorto;
		}


		public void setNombreCorto(String nombreCorto) {
			this.nombreCorto = nombreCorto;
		}


		public String getNombre() {
			return nombre;
		}


		public void setNombre(String nombre) {
			this.nombre = nombre;
		}


		public String getDescripcion() {
			return descripcion;
		}


		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}


		public Estado getEstado() {
			return estado;
		}


		public void setEstado(Estado estado) {
			this.estado = estado;
		}

		

	    
	    
}
