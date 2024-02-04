package backend.backend.repositories;

import backend.backend.entities.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface PermisoRepository extends JpaRepository<Permiso,Long>{
    Optional<Permiso> findByUrl(String url);

    //@Query("SELECT case when count(*) > 0 then true else false end FROM Permiso p JOIN p.listaRol lr WHERE p.url=?1 AND lr.nombreRol=?2 AND p.fechaHoraFinVigenciaPermiso IS NULL")
    //boolean verificarSeguimiento(String urlPermiso, String nombreRol);

    @Query("SELECT p.url FROM Permiso p JOIN p.listaRol lr WHERE lr.nombreRol=?1 AND p.fechaHoraFinVigenciaPermiso IS NULL")
    List<String> listaPermisosParaUnRol(String nombreRol);
}
