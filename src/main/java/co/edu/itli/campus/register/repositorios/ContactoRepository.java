package co.edu.itli.campus.register.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.edu.itli.campus.register.model.Contacto;

public interface ContactoRepository extends JpaRepository<Contacto, Long>, JpaSpecificationExecutor<Contacto>{
	    Optional<Contacto> findByNombre(String nombre);
	    
	    Contacto findByEmail(String email);
	    @Query(value="select CASE WHEN (count(id) >= 1) THEN true ELSE false END  from Contacto where UPPER(email)=UPPER(:email)" )
	    boolean existEmail(@Param("email") String email);
	    /*@Query(value="select c from Contacto as c where UPPPER(c.nombre) like('%'+UPPER(:filter))" )
	    List<Contacto> nombreContains(@Param("filter") String filter);*/
	   
}
