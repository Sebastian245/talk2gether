package backend.backend.services.motivo;

import java.util.List;

import backend.backend.DTO.DTOCrearMotivo;
import backend.backend.DTO.DTOListarMotivosActivos;
import backend.backend.DTO.DTOListarMotivosActivosConID;
import backend.backend.DTO.DTOListarTipoMotivos;
import backend.backend.DTO.DTOReporteComportamiento;
import backend.backend.DTO.DTOReporteMotivoDetalle;
import backend.backend.entities.Motivo;

public interface MotivoService {
    DTOListarMotivosActivos listarMotivosActivos(String nombreTipoMotivo) throws Exception;

    Motivo obtenerMotivoPorId(Long id);

    Motivo crearMotivo(DTOCrearMotivo dtoCrearMotivo) throws Exception;

    Motivo actualizarMotivo(Long id, DTOCrearMotivo dtoActualizarMotivo) throws Exception;

    String deshabilitarMotivo(Long id) throws Exception;

    String habilitarMotivo(Long id) throws Exception;

    List<Motivo> listarMotivos();

    List<DTOReporteMotivoDetalle> obtenerDetalleReporteMotivo(long idCuentaReportada,long idMotivo) throws Exception;

    List<DTOListarTipoMotivos> listarTipoMotivos() throws Exception;

    List<DTOReporteComportamiento> listarReporteComportamiento() throws Exception;

    List<DTOReporteComportamiento> filtrarReportePorUsuario(String nombreUsuario) throws Exception;

    List<DTOReporteComportamiento> filtrarReportePorMotivo(String idMotivo) throws Exception;

    List<DTOListarMotivosActivosConID> listarMotivosActivosConID(String nombreTipoMotivo) throws Exception;
}
