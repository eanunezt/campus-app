package co.edu.itli.campus.core.services;
import java.io.Serializable;
import java.security.Principal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import co.edu.itli.campus.core.model.EntidadAuditada;
import co.edu.itli.campus.core.seguridad.IConstants;
import co.edu.itli.campus.core.seguridad.UserPrincipal;
import co.edu.itli.campus.exception.EntityNotFoundException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Transactional
public abstract class AbstractService<T extends Serializable> implements ICommonsOperations<T> {
	UserPrincipal principal;

	private UserPrincipal getUserInvited() {
		 List<GrantedAuthority> authorities=new ArrayList<GrantedAuthority>();
	        authorities.add(new SimpleGrantedAuthority("R2"));
	        //List<GrantedAuthority> authorities=Arrays.asList(claims.get(VAR_AUTHOS));
	        
	       return  new UserPrincipal(
	        		2L,
	               "",
	               "Invitado",
	                "",
	                "",
	                authorities                
	        );
	}
	
	
	//protected UserPrincipal principal=(UserPrincipal) SecurityContextHolder.getContext().getAuthentication();
	protected UserPrincipal getUserAuthenticated() {
		if (principal == null) {
			Object principalObj = SecurityContextHolder.getContext().getAuthentication();
			if (principalObj instanceof UserPrincipal) {
				principal=(UserPrincipal) principalObj;
				 

			} else {
				principal= getUserInvited();
			}
		}
		return principal;
	}

	protected Long getUserId() {
		return getUserAuthenticated().getId();
	}
	
	protected abstract String getNameEntity() ;
	//protected abstract Integer getIdUser() ;
	// read - one
    
    @Transactional(readOnly = true)
    public T findById(final long id) {
        return getRepository().findById(id).orElseThrow(() ->
	    new EntityNotFoundException(getNameEntity()+" not found with id : " + id)
				);
    }

    // read - all

   
    @Transactional(readOnly = true)
    public List<T> findAll() {
        return Lists.newArrayList(getRepository().findAll());
    }

   
    public Page<T> findPaginated(final int page, final int size) {
        return getRepository().findAll(PageRequest.of(page, size));
    }

    // write

    public T create(T entity) {
    	if(entity instanceof EntidadAuditada) {
    		((EntidadAuditada)entity).setFecCambio(Instant.now());
    		((EntidadAuditada)entity).setIdUsuarioCambio(getUserId());
    		((EntidadAuditada)entity).setFecRegistro(Instant.now());
    		
    	}
    	
    	    	return getRepository().save(entity);
    }

    
    public T update(final T entity) {
    	
    	if(entity instanceof EntidadAuditada) {
    		((EntidadAuditada)entity).setIdUsuarioCambio(getUserId());
    		((EntidadAuditada)entity).setFecRegistro(Instant.now());    		
    	}
        return getRepository().save(entity);
    }

    
    public void delete(final T entity) {
        getRepository().delete(entity);
    }
    
    public Page<T> findPaginated(Pageable pageable){
    	return getRepository().findAll(pageable);
    }

    
    public void deleteById(final long entityId) {
        getRepository().deleteById(entityId);
    }

    protected abstract PagingAndSortingRepository<T, Long> getRepository();

}