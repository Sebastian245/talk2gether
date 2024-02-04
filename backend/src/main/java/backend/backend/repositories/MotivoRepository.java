package backend.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import backend.backend.DTO.DTOReporteComportamiento;
import backend.backend.entities.Motivo;

import java.util.List;
import java.util.Optional;

@Repository
public interface MotivoRepository extends JpaRepository<Motivo, Long> {

    List<Motivo> findByFechaHoraFinVigenciaMotivoIsNull();

    Optional<Motivo> findByNombreMotivo(String nombreMotivo);

    List<Motivo> findAllByFechaHoraFinVigenciaMotivoIsNull();
 
    @Query("SELECT new backend.backend.DTO.DTOReporteComportamiento(u.cuenta.id,u.nombreUsuario,u.apellidoUsuario,u.cuenta.correo,r.motivo.id,r.motivo.nombreMotivo,count(*)) FROM ReporteMotivo r JOIN Usuario u ON r.idCuentaReportada=u.cuenta.id where r.fechaHoraFinVigenciaReporteMotivo IS NULL GROUP BY r.motivo.id, r.motivo.nombreMotivo, u.cuenta.id, u.nombreUsuario, u.apellidoUsuario")
    List<DTOReporteComportamiento> obtenerReporteComportamiento();
    
    @Query("SELECT new backend.backend.DTO.DTOReporteComportamiento(u.cuenta.id, u.nombreUsuario, u.apellidoUsuario,u.cuenta.correo, r.motivo.id, r.motivo.nombreMotivo, count(*)) " +
       "FROM ReporteMotivo r JOIN Usuario u ON r.idCuentaReportada = u.cuenta.id " +
       "WHERE r.fechaHoraFinVigenciaReporteMotivo IS NULL " +
       "AND (u.nombreUsuario LIKE %:nombreUsuario% OR u.apellidoUsuario LIKE %:nombreUsuario% OR u.cuenta.correo LIKE %:nombreUsuario%) " +
       "GROUP BY r.motivo.id, r.motivo.nombreMotivo, u.cuenta.id, u.nombreUsuario, u.apellidoUsuario")
    List<DTOReporteComportamiento> obtenerReporteComportamientoPorNombre(@Param("nombreUsuario") String nombreUsuario);

    @Query("SELECT new backend.backend.DTO.DTOReporteComportamiento(u.cuenta.id, u.nombreUsuario, u.apellidoUsuario,u.cuenta.correo, r.motivo.id, r.motivo.nombreMotivo, count(*)) " +
    "FROM ReporteMotivo r JOIN Usuario u ON r.idCuentaReportada = u.cuenta.id " +
    "WHERE r.fechaHoraFinVigenciaReporteMotivo IS NULL " +
    "AND r.motivo.id = :motivoId " +
    "GROUP BY r.motivo.id, r.motivo.nombreMotivo, u.cuenta.id, u.nombreUsuario, u.apellidoUsuario")
    List<DTOReporteComportamiento> obtenerReporteComportamientoPorMotivoId(@Param("motivoId") Long motivoId);

  

}
