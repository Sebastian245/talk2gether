package backend.backend.repositories;

import backend.backend.entities.NivelIdioma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NivelIdiomaRepository extends JpaRepository<NivelIdioma, Long> {
    List<NivelIdioma> findByFechaHoraFinVigenciaNivelIdiomaIsNull();
    Optional<NivelIdioma> findByNombreNivelIdioma(String nombreNivelIdioma);
    Optional<NivelIdioma> findByNombreNivelIdiomaAndFechaHoraFinVigenciaNivelIdiomaIsNull(
            String nombreNivelIdiomaAprendiz);
}
