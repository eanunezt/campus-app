package co.edu.itli.campus.register.model;

import java.io.Serializable;

public class ContactoProgramaId implements Serializable {

  private long idContacto;

  private long idPrograma;

  public int hashCode() {
    return (int)(idContacto + idPrograma);
  }

  public boolean equals(Object object) {
    if (object instanceof ContactoProgramaId) {
      ContactoProgramaId otherId = (ContactoProgramaId) object;
      return (otherId.idContacto == this.idContacto) 
              && (otherId.idPrograma == this.idPrograma);
    }
    return false;
  }
  
  @Override
	public String toString() {
		// TODO Auto-generated method stub
		return "idContacto:"+idContacto+"  idPrograma:"+idPrograma;
	}

}