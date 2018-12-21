package co.edu.itli.campus.register.services.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.itli.campus.core.dtos.CatalogoDTO;
import co.edu.itli.campus.core.enums.Estado;
import co.edu.itli.campus.core.services.AbstractService;
import co.edu.itli.campus.register.model.Programa;
import co.edu.itli.campus.register.repositorios.ProgramaRepository;
import co.edu.itli.campus.register.services.IProgramaService;

@Service
@Transactional
public class ProgramService extends AbstractService<Programa> implements IProgramaService<Programa> {
	
	
	@Autowired
	ProgramaRepository programRepository;	

	
	
	@Override
	protected PagingAndSortingRepository<Programa, Long> getRepository() {
		// TODO Auto-generated method stub
		return programRepository;
	}	
	
	@Override
	protected String getNameEntity() {
		// TODO Auto-generated method stub
		return "Program";
	}

	@Override
	public List<Programa> findAllByState(Estado... state) {
		return programRepository.findAllByEstado(state);
	}

	@Override
	public List<CatalogoDTO> getCatalog() {
		return programRepository.getCatalogo();
	}

	
	
}
