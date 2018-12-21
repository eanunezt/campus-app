package co.edu.itli.campus.core.respositorios;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.itli.campus.core.model.Usuario;

/**
 * Created by eanunezt on 02/08/17.
 */
@Repository
public interface UserRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByUsuarioOrEmail(String usuario, String email);

    List<Usuario> findByIdIn(List<Long> userIds);

    Optional<Usuario> findByUsuario(String usuario);

    Boolean existsByUsuario(String usuario);

    Boolean existsByEmail(String email);
}
