package backend.backend.services.cuenta;

import java.util.List;

import backend.backend.DTO.*;
import backend.backend.DTORepository.DTOListarSeguidosRepository;

public interface CuentaService {

    void bloquearCuenta(DTOBloqueoUsuario dtoBloquearUsuario) throws Exception;

    void desbloquearCuenta(DTODesbloqueoUsuario dtoDesbloquearUsuario) throws Exception;

    DTOVerificacion verificarBloqueo(DTOBloqueoUsuario dtoBloquearUsuario) throws Exception;

    

    String referirUsuario(Long id) throws Exception ;

    String seguirUsuario(DTOSeguirUsuario dtoSeguirUsuario) throws Exception;

    String dejarDeSeguirUsuario(DTOSeguirUsuario dtoSeguirUsuario) throws Exception;

    boolean verificarSeguimiento(DTOSeguirUsuario dtoSeguirUsuario) throws Exception;

    List<DTOListarSeguidosRepository> listarSeguidos(Long idCuenta) throws Exception;

    List<DTOListarSeguidosRepository> listarSeguidores(Long idCuenta) throws Exception;

    String reportarUsuario(DTOReportarUsuario dtoReportarUsuario) throws Exception;

    String obtenerIdiomaAprendiz(Long idCuenta) throws Exception;

    DTOUsuarioChat obtenerUsuarioChat(Long idCuenta) throws Exception;

    DTOVisualizarEstadisticasAprendiz visualizarEstadisticasAprendiz(Long idCuenta) throws Exception;

    String puntuarUsuario(int cantidadPuntos, long idCuenta);

    DTODatosPersonales obtenerDatosPersonales(Long idCuenta) throws Exception;

    DTOCantidadSeguidosYSeguidores obtenercantidadSeguidosSeguidores(Long idCuenta) throws Exception;

    void actualizarUltimaConexion(Long idCuenta) throws Exception;


    List<DTOUsuarioBloqueado> listaUsuariosBloqueados(Long idCuenta) throws Exception;
}
