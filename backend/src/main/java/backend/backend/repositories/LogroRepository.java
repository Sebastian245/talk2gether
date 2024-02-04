package backend.backend.repositories;

import backend.backend.entities.Logro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogroRepository extends JpaRepository<Logro, Long> {
    List<Logro> findAllByFechaHoraFinVigenciaLogroIsNull();
}
