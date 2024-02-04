package backend.backend.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.backend.entities.Calificacion;

public interface CalificacionRepository extends JpaRepository<Calificacion,Long>{

    Optional<Calificacion> findByCantidadEstrellas(int cantidadEstrellas);
    
}
