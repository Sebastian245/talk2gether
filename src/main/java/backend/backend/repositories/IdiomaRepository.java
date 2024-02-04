package backend.backend.repositories;

import backend.backend.entities.Idioma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IdiomaRepository extends JpaRepository<Idioma, Long> {
    List<Idioma> findByFechaHoraFinVigenciaIdiomaIsNull();
    Optional<Idioma> findByNombreIdioma(String nombreIdioma);
    boolean existsByNombreIdioma(String nombreIdioma);
    Optional<Idioma> findByNombreIdiomaAndFechaHoraFinVigenciaIdiomaIsNull(String nombreIdiomaAprendiz);
    
}
