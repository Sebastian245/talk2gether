package backend.backend.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import backend.backend.entities.PuntosPorActividad;

@Repository
public interface PuntosPorActividadRepository extends JpaRepository<PuntosPorActividad,Long>{

    List<PuntosPorActividad> findByFechaHoraFinVigenciaPuntosPorActividadIsNull();

    Optional<PuntosPorActividad> findByNombrePuntosPorActividad(String nombrePuntosPorActividad);
}   

