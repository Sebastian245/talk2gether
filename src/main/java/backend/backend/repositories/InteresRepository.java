package backend.backend.repositories;

import backend.backend.entities.Interes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InteresRepository extends JpaRepository<Interes, Long> {

    List<Interes> findByFechaHoraFinVigenciaInteresIsNull();

    Optional<Interes> findByNombreInteres(String nombreInteres);

    Optional<Interes> findByNombreInteresAndFechaHoraFinVigenciaInteresIsNull(String string);

    
}
