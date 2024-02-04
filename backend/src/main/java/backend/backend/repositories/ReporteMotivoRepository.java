package backend.backend.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import backend.backend.DTO.DTOReporteMotivoDetalle;
import backend.backend.entities.ReporteMotivo;

@Repository
public interface ReporteMotivoRepository extends JpaRepository<ReporteMotivo,Long>{

    List<ReporteMotivo> findByIdCuentaReportada(Long idCuenta);
   
    //Obtener un detalle de todos los reportes hechos a un usuario
    @Transactional
    @Query("SELECT new backend.backend.DTO.DTOReporteMotivoDetalle(rm.idCuentaInformanteMotivo as idCuentaInformanteMotivo, rm.fechaHoraAltaReporteMotivo as fechaHoraAltaReporteMotivo, rm.descripcionReporteMotivo as  descripcionReporteMotivo) FROM ReporteMotivo rm WHERE rm.idCuentaReportada=?1 AND rm.motivo.id=?2")
    List<DTOReporteMotivoDetalle> obtenerReporteAUsuario(long idReportado, Long motivo);
}
