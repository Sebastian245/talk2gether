package backend.backend.repositories;

import backend.backend.entities.*;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<Rol,Long>{
    Optional<Rol> findByNombreRol(String nombreRol);

    List<Rol> findByFechaHoraFinVigenciaRolIsNull();

    Optional<Rol> findByNombreRolAndFechaHoraFinVigenciaRolIsNull(String nombreRol);
}
