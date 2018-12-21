package co.edu.itli.campus.core.respositorios;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.itli.campus.core.model.Rol;

/**
 * Created by eanunezt on 02/08/17.
 */
@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByName(String rolName);
}