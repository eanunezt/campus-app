package co.edu.itli.campus.register.services;

import java.io.Serializable;
import java.util.List;

import co.edu.itli.campus.core.dtos.CatalogoDTO;
import co.edu.itli.campus.core.enums.Estado;
import co.edu.itli.campus.core.services.ICommonsOperations;
import co.edu.itli.campus.register.model.Programa;

public interface IProgramaService<T extends Serializable> extends ICommonsOperations<T> {
	List<Programa> findAllByState(Estado... estado);
	 List<CatalogoDTO> getCatalog();
   
}