package co.edu.itli.campus.core.model;

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
@Table(name="rol_autorizaciones")
@IdClass(RolAutorizacionId.class)
public class RolAutorizacion extends EntidadAuditada{
  @Id
  //@Column(name = "contact_id")
  @ApiModelProperty(position = 0)
  private Long idRol;
  @Id
  @ApiModelProperty(position = 1)
  private Long idAutorizacion;

  @ApiModelProperty(position = 2)
  private Instant fecInicial=Instant.now();
  @ApiModelProperty(position = 3)
  private Instant fecFin;
  
  @ManyToOne
  @JoinColumn(name = "idRol", updatable = false, insertable = false,
          referencedColumnName = "id")
  @JsonIgnore
  private Rol rol;
  
  @ManyToOne
  @JoinColumn(name = "idAutorizacion", updatable = false, insertable = false,
          referencedColumnName = "id")
  private Autorizacion autorizacion;

public Long getIdRol() {
	return idRol;
}

public void setIdRol(Long idRol) {
	this.idRol = idRol;
}

public Long getIdAutorizacion() {
	return idAutorizacion;
}

public void setIdAutorizacion(Long idAutorizacion) {
	this.idAutorizacion = idAutorizacion;
}

public Rol getRol() {
	return rol;
}

public void setRol(Rol rol) {
	this.rol = rol;
}

public Autorizacion getAutorizacion() {
	return autorizacion;
}

public void setAutorizacion(Autorizacion autorizacion) {
	this.autorizacion = autorizacion;
}

public Instant getFecInicial() {
	return fecInicial;
}

public void setFecInicial(Instant fecInicial) {
	this.fecInicial = fecInicial;
}

public Instant getFecFin() {
	return fecFin;
}

public void setFecFin(Instant fecFin) {
	this.fecFin = fecFin;
}
  
 
  
 
}