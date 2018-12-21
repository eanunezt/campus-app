package co.edu.itli.campus.core.model;

import java.io.Serializable;

public class RolAutorizacionId implements Serializable {

  private long idRol;

  private long idAutorizacion;

  public int hashCode() {
    return (int)(idRol + idAutorizacion);
  }

  public boolean equals(Object object) {
    if (object instanceof RolAutorizacionId) {
      RolAutorizacionId otherId = (RolAutorizacionId) object;
      return (otherId.idRol == this.idRol) 
              && (otherId.idAutorizacion == this.idAutorizacion);
    }
    return false;
  }
  
  @Override
	public String toString() {
		// TODO Auto-generated method stub
		return "idRol:"+idRol+"  idAutorizacion:"+idAutorizacion;
	}

}