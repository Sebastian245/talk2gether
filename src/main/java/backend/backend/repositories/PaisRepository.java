package backend.backend.repositories;

import backend.backend.entities.*;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaisRepository extends JpaRepository<Pais,Long>{
    List<Pais> findByFechaHoraFinVigenciaPaisIsNull();
    Optional<Pais> findByNombrePais(String nombrePais);
    boolean existsByNombrePais(String nombrePais);
    Optional<Pais> findByNombrePaisAndFechaHoraFinVigenciaPaisIsNull(String nombrePais);
}
