package backend.backend.services.reunionvirtual;

import java.util.List;
import java.util.Set;

import backend.backend.DTO.DTOCrearSala;
import backend.backend.DTO.DTOFiltro;
import backend.backend.DTO.DTOListarReunionesVirtuales;
import backend.backend.DTO.DTOPrevioCalificarUsuario;

public interface ReunionVirtualService {

    DTOCrearSala crearSala(Long idCuenta) throws Exception;

    String unirseASala(String linkReunion, Long idSegundoParticipante) throws Exception;

    String finalizarVideollamada(String linkReunion) throws Exception;

    List<DTOListarReunionesVirtuales> listarSalasActivas(Long idCuenta) throws Exception;

    List<DTOListarReunionesVirtuales> listarSalasActivasFiltradas(Long idCuenta,
            String edadMinima,
            String edadMaxima,
            List<String> intereses,
            String nombreNivelIdioma,
            String nombrePais) throws Exception;

    DTOPrevioCalificarUsuario dtoPrevioCalificarUsuario(Long idCuenta, String url) throws Exception;

    public String finalizarvideollamadaPorRefrescar(long idCuenta) throws Exception;
}
