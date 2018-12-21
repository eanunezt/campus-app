package co.edu.itli.campus.register.repositorios;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.edu.itli.campus.core.dtos.CatalogoDTO;
import co.edu.itli.campus.core.enums.Estado;
import co.edu.itli.campus.register.model.Programa;

public interface ProgramaRepository extends JpaRepository<Programa, Long> {
	    Optional<Programa> findByNombre(String nopmbre);
	    List<Programa> findAllByEstado(Estado... estado);
	    //CatalogDTO(Long id, String nameShort, String name)
	    @Query(value="select new co.edu.itli.campus.core.dtos.CatalogoDTO(p.id, p.nombreCorto, p.nombre) from Programa p where p.estado=0"/*, nativeQuery = true*/)
	    List<CatalogoDTO> getCatalogo();
	    
	    

}