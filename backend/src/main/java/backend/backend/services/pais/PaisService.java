package backend.backend.services.pais;
import java.util.List;

import backend.backend.DTO.DTOCrearPais;
import backend.backend.entities.Pais;

public interface PaisService {
    List<String> listarPaisesActivos();
    Pais obtenerPaisPorId(Long id);
    Pais crearPais(DTOCrearPais dtoCrearPais) throws Exception;
    Pais actualizarPais(DTOCrearPais dtoActualizarPais, Long id) throws Exception;
    String deshabilitarPais(Long id) throws Exception;
    String habilitarPais(Long id)throws Exception;
    List<Pais> listarPaises();
}