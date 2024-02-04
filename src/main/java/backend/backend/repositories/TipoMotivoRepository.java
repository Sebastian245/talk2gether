package backend.backend.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import backend.backend.entities.TipoMotivo;

@Repository
public interface TipoMotivoRepository extends JpaRepository<TipoMotivo,Long> {

    boolean existsByNombreTipoMotivo(String nombreTipoMotivo);

    Optional<TipoMotivo> findByNombreTipoMotivo(String nombreTipoMotivo);

    

   
    
}
