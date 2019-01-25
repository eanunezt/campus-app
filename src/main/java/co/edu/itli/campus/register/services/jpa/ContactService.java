package co.edu.itli.campus.register.services.jpa;

import java.time.Instant;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	    		 _contact=contactRepository.save(entity);
		    	}else {
		    		
		    		contProgRepositoy.deleteContactProgramByIdContacto(entity.getId());
		    	}
	    	
	    	for (Iterator<ContactoPrograma> iterator = entity.getProgramas().iterator(); iterator.hasNext();) {
	    		ContactoPrograma contPrg= iterator.next();
	    		if(isNew) {
	    		contPrg.setIdContacto(_contact.getId()); }
	    		
	    		contPrg.setFecCambio(Instant.now());
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
	
	
	
	
}
