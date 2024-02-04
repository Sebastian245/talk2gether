package backend.backend.services.gamificacion;

import java.util.List;

import backend.backend.DTO.DTOLogrosAprendiz;
import backend.backend.DTO.DTOTablaRanking;

public interface GamificacionService {

    List<DTOTablaRanking> tablaRanking(Long idCuenta,int cantidadFilas) throws Exception;

    List<DTOLogrosAprendiz> obtenerLogros(Long idCuenta) throws Exception;
    
}
