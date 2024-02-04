package backend.backend.services.logro;

import backend.backend.DTO.DTOCrearLogro;
import backend.backend.entities.Logro;

import java.util.List;

public interface LogroService {
    List<Logro> listarLogrosActivos();
    Logro obtenerLogroPorId(Long id);
    Logro crearLogro(DTOCrearLogro dtoCrearLogro) throws Exception;
    Logro actualizarLogro(Long id, DTOCrearLogro dtoActualizarLogro) throws Exception;
    String deshabilitarLogro(Long id) throws Exception;
    String habilitarLogro(Long id) throws Exception;
}
