package co.edu.itli.campus.register.services;
import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.edu.itli.campus.core.services.ICommonsOperations;
import co.edu.itli.campus.register.model.Contacto;

public interface IContactoService<T extends Serializable> extends ICommonsOperations<T> {
     
    Contacto findByEmail(String email);
    @Query(value="select count(id) from Contacto where UPPPER(email)=UPPER(:email)" )
    boolean existEmail(@Param("email") String email);
    
    
    Page<Contacto> contactoFilter(final String filter,final int page, final int size);
}