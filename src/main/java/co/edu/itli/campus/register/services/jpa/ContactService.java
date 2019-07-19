package co.edu.itli.campus.register.services.jpa;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import co.edu.itli.campus.core.services.AbstractService;
import co.edu.itli.campus.register.model.Contacto;
import co.edu.itli.campus.register.model.ContactoPrograma;
import co.edu.itli.campus.register.repositorios.ContactProgramRepository;
import co.edu.itli.campus.register.repositorios.ContactoRepository;
import co.edu.itli.campus.register.services.IContactoService;

@Service
@Transactional
public class ContactService extends AbstractService<Contacto> implements IContactoService<Contacto>{
	
	
	@Autowired
	ContactoRepository contactRepository;
	@Autowired
	ContactProgramRepository contProgRepositoy;

	@Override
	protected PagingAndSortingRepository<Contacto, Long> getRepository() {
		// TODO Auto-generated method stub
		return contactRepository;
	}

	@Override
	protected String getNameEntity() {
		// TODO Auto-generated method stub
		return "Contact";
	}
	
	@Override
	public Contacto create(Contacto entity) {	
		entity.setEmail(entity.getEmail().toLowerCase());
		entity.setNombre(entity.getNombre().toUpperCase());
		return save(entity);
	}
	
	@Override
	public Contacto update(Contacto entity) {
		return save(entity);
	}

	
	private  Contacto save(Contacto entity) {
		boolean isNew=entity.getId()==null;
		
		Contacto _contact=null;
		
    	
	     if(entity.getProgramas()!=null && !entity.getProgramas().isEmpty()){
	    	 
	    	 if(isNew) {
	    		 _contact=create(entity);
		    	}else {
		    		
		    		contProgRepositoy.deleteContactProgramByIdContacto(entity.getId());
		    	}
	    	
	    	for (Iterator<ContactoPrograma> iterator = entity.getProgramas().iterator(); iterator.hasNext();) {
	    		ContactoPrograma contPrg= iterator.next();
	    		if(isNew) {
	    		contPrg.setIdContacto(_contact.getId()); }
	    		
	    		contPrg.setFecCambio(LocalDateTime.now());
	    		 contProgRepositoy.insert(contPrg.getFecCambio(),contPrg.getIdContacto(),contPrg.getIdPrograma());
			}
	    	 if(!isNew) {
	    		 _contact=contactRepository.save(entity);
	    	 }
	    	
	     }

		
		return _contact;
	}

	@Override
	public Contacto findByEmail(String email) {
		// TODO Auto-generated method stub
		return contactRepository.findByEmail(email);
	}

	@Override
	public boolean existEmail(String email) {
		// TODO Auto-generated method stub
		return contactRepository.existEmail(email);
	}

	@Override
	public Page<Contacto> contactoFilter(final String filter,final int page, final int size) {
		
		if(filter!=null) {
			
			ObjectMapper objectMapper = new ObjectMapper();
				JsonNode jsonNode;
				String numTelefonoNombre=null;
				try {
					jsonNode = objectMapper.readTree(filter);
					numTelefonoNombre = jsonNode.get("numTelefonoNombre").asText();
				} catch (IOException e) {					
					e.printStackTrace();
				}
				
			
			if(numTelefonoNombre!=null && !"".equals(numTelefonoNombre))
			return contactRepository.findAll(_nombreContains(numTelefonoNombre),PageRequest.of(page, size));	
		}
			return contactRepository.findAll(PageRequest.of(page, size));
    }
	
	static Specification<Contacto> _nombreContains(String filter) {
	    return (contacto, cq, cb) -> 
	    		cb.like(
	    				cb.upper(
	    						cb.concat(
	    								cb.concat(contacto.get("numTelefono"), " "),
	    								contacto.get("nombre"))
	    						), "%" + filter.toUpperCase() + "%"
	    		);
	}
	
	
}
