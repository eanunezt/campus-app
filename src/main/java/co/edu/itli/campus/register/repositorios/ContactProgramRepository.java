package co.edu.itli.campus.register.repositorios;

import java.time.Instant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import co.edu.itli.campus.register.model.ContactoPrograma;

public interface ContactProgramRepository extends JpaRepository<ContactoPrograma, Long> {
	@Modifying
	@Transactional
	@Query("delete from ContactoPrograma where idContacto=:idContacto")
	void deleteContactProgramByIdContacto(Long idContacto);
	
	//insert into contact_program (updated_date, contact_id, program_id) values (?, ?, ?)
	 @Modifying
	@Query(value="insert into contacto_programas (fec_cambio, id_contacto, id_programa) values (:fec_cambio, :id_contacto, :id_programa)",nativeQuery=true )
	 @Transactional
	void insert(@Param("fec_cambio") Instant fec_cambio,@Param("id_contacto") Long id_contacto,@Param("id_programa") Long id_programa);
}
