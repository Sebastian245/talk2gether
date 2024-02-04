package backend.backend.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import backend.backend.entities.UsuarioCalificacion;

@Repository 
public interface UsuarioCalificacionRepository extends JpaRepository<UsuarioCalificacion,Long> {

    Optional<UsuarioCalificacion> findByIdUsuarioCalificadorAndIdReunionVirtual(Long idCuentaCalificador,
            Long idReunionVirtual);
    
}
