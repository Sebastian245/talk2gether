package backend.backend.services.usuario;


import java.util.List;

import backend.backend.DTO.DTOCalificarUsuario;
import backend.backend.DTO.DTOCambiarContrasenia;
import backend.backend.DTO.DTOListarReunionesVirtuales;
import backend.backend.DTO.DTOMotivosCuentaEliminada;
import backend.backend.DTO.DTOPerfilOtroUsuario;
import backend.backend.DTO.DTORegistrarUsuario;
import backend.backend.DTORepository.DTOListarReunionesVirtualesRepository;

public interface UsuarioService {

    String registroUsuario(DTORegistrarUsuario dtoRegistrarUsuario,String idCuenta);

    String confirmarTokenVerficacion(String token, String idRefiere);

    String reenviarMail(String correo);

    String recuperarContrasenia(String correo);

    String confirmarCambioContrasenia(String correo, String contrasenia);

    DTOPerfilOtroUsuario visualizarOtroPerfil(Long idCuenta) throws Exception;


    String cambiarContrasenia(DTOCambiarContrasenia dtoCambiarContrasenia) throws Exception;

    int calculoTablaRanking(Long idUsuario);

    String calificarUsuario(DTOCalificarUsuario dtoCalificarUsuario) throws Exception;

    String modificarUsuarioDesdeCuentaAprendiz(Long idCuenta, DTORegistrarUsuario dtoModificarDatosUsuario) throws Exception;

    String eliminarCuenta(DTOMotivosCuentaEliminada dtoMotivosCuentaEliminada) throws Exception;

    List<DTOListarReunionesVirtuales> buscarUsuario(String parametroDeBusqueda, long idCuenta) throws Exception;
   
    
   

}
