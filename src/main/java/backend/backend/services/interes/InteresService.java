package backend.backend.services.interes;

import java.util.List;

import backend.backend.DTO.DTOCrearInteres;
import backend.backend.DTO.DTOListarInteres;
import backend.backend.entities.Interes;

public interface InteresService {

    List<DTOListarInteres> listarInteresesActivos();

    Interes obtenerInteresPorId(Long id);

    Interes crearInteres(DTOCrearInteres dtoCrearInteres) throws Exception;

    Interes actualizarInteres(Long id, DTOCrearInteres dtoActualizarInteres) throws Exception;

    String deshabilitarInteres(Long id) throws Exception;

    String habilitarInteres(Long id) throws Exception;

    List<Interes> listarIntereses();
}
