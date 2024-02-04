package backend.backend.services.administrador;

import java.util.Date;
import java.util.List;

import backend.backend.DTO.DTOAdministrarUsuario_Administrador;
import backend.backend.DTO.DTODatosCalificacion;
import backend.backend.DTO.DTOEliminarUsuario_Administrador;
import backend.backend.DTO.DTOModificarUsuario_Administrador;
import backend.backend.DTO.DTOMotivosCuentaEliminada;
import backend.backend.DTO.DTOUsuario_Administrador;
import backend.backend.DTO.DTOVisualizarEstadisticasPlataforma;

public interface UsuarioAdministradorService {

    List<DTOUsuario_Administrador> listarUsuariosDesdeCuentaAdministrador() throws Exception;

    String crearUsuarioDesdeCuentaAdministrador(DTOAdministrarUsuario_Administrador dtoCrearUsuario_Administrador)
            throws Exception;

    String eliminarCuentaUsuario(DTOMotivosCuentaEliminada dtoMotivosCuentaEliminada) throws Exception;

    List<DTOUsuario_Administrador>  filtrarUsuarios(String filtroParametro) throws Exception ; 
        
    String modificarUsuarioDesdeCuentaAdministrador(Long idCuenta, DTOModificarUsuario_Administrador dtoModificarUsuario_administrador) throws Exception;
    
    DTOEliminarUsuario_Administrador datoseliminarusuario(long idCuenta) throws Exception;

    DTOVisualizarEstadisticasPlataforma obtenerEstadisticasAdministrador() throws Exception;

    DTODatosCalificacion obtenerCalificaciones(long idCuenta) throws Exception;

    DTODatosCalificacion filtrarCalificaciones(long idCuenta, String fechaDesde, String fechaHasta) throws Exception;

    DTOVisualizarEstadisticasPlataforma obtenerEstadisticasAdministradorGranuladas(String fechaDesde, String fechaHasta ) throws Exception;
}
