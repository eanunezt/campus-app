package co.edu.itli.campus.register.model;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name="contacto_programas")
@IdClass(ContactoProgramaId.class)
public class ContactoPrograma {
  @Id
  //@Column(name = "contact_id")
  @ApiModelProperty(position = 0)
  private Long idContacto;
  @Id
  @ApiModelProperty(position = 1)
  private Long idPrograma;
  
  @LastModifiedDate
  private Instant fecCambio;
  
  @ManyToOne
  @JoinColumn(name = "idContacto", updatable = false, insertable = false,
          referencedColumnName = "id")
  @JsonIgnore
  private Contacto contacto;
  
  @ManyToOne
  @JoinColumn(name = "idPrograma", updatable = false, insertable = false,
          referencedColumnName = "id")
  private Programa programa;

public Long getIdContacto() {
	return idContacto;
}

public void setIdContacto(Long idContacto) {
	this.idContacto = idContacto;
}

public Long getIdPrograma() {
	return idPrograma;
}

public void setIdPrograma(Long idPrograma) {
	this.idPrograma = idPrograma;
}

public Instant getFecCambio() {
	return fecCambio;
}

public void setFecCambio(Instant fecCambio) {
	this.fecCambio = fecCambio;
}

public Contacto getContacto() {
	return contacto;
}

public void setContacto(Contacto contacto) {
	this.contacto = contacto;
}

public Programa getPrograma() {
	return programa;
}

public void setPrograma(Programa programa) {
	this.programa = programa;
}
  
 
  
 
}