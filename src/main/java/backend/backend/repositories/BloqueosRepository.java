package backend.backend.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import backend.backend.entities.Bloqueos;

@Repository
public interface BloqueosRepository extends JpaRepository<Bloqueos,Long>{
    @Transactional
    @Query("SELECT b FROM Bloqueos b WHERE b.usuarioQueBloquea.id = ?1 AND b.usuarioBloqueado.id = ?2")
    Bloqueos encontrarBloqueosPorUsuarios(Long idUsuarioQueBloquea, Long idUsuarioBloqueado);
    
    @Transactional
    @Query("SELECT b FROM Bloqueos b WHERE (b.usuarioQueBloquea.id=?1 OR b.usuarioBloqueado.id=?1) AND b.fechaHoraFinVigenciaBloqueoRealizado IS NULL")
    List<Bloqueos> listaCuentasBloqueadas(Long idUsuarioQueBloquea);

    @Transactional
    @Query("SELECT b FROM Bloqueos b WHERE (b.usuarioBloqueado.id=?1) AND b.fechaHoraFinVigenciaBloqueoRealizado IS NULL")
    List<Bloqueos> listaCuentasQueMeBloquearon(Long idUsuarioQueBloquea);


}