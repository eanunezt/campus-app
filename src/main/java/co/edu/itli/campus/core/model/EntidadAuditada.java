package co.edu.itli.campus.core.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.Instant;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(
        value = {"createdAt", "updatedAt"},
        allowGetters = true
)
public abstract class EntidadAuditada implements Serializable {
    private Instant fecRegistro;
    private Instant fecCambio;
    private Long idUsuarioCambio;
	public Instant getFecRegistro() {
		return fecRegistro;
	}
	public void setFecRegistro(Instant fecRegistro) {
		this.fecRegistro = fecRegistro;
	}
	public Instant getFecCambio() {
		return fecCambio;
	}
	public void setFecCambio(Instant fecCambio) {
		this.fecCambio = fecCambio;
	}
	public Long getIdUsuarioCambio() {
		return idUsuarioCambio;
	}
	public void setIdUsuarioCambio(Long idUsuarioCambio) {
		this.idUsuarioCambio = idUsuarioCambio;
	}

}