package co.edu.itli.campus.core.respositorios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.itli.campus.core.model.Autorizacion;
import java.util.Optional;
/**
 * Created by eanunezt on 02/08/17.
 */
@Repository
public interface AuthorizationRepository extends JpaRepository<Autorizacion, Long> {
    Optional<Autorizacion> findByNombre(String nombre);
}