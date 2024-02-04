package backend.backend.services.idioma;

import java.util.List;

import backend.backend.DTO.DTOCrearIdioma;
import backend.backend.entities.Idioma;

public interface IdiomaService {
    List<String> listarIdiomasActivos();
    Idioma obtenerIdiomaPorId(Long id);
    Idioma crearIdioma(DTOCrearIdioma dtoCrearIdioma) throws Exception;
    Idioma actualizarIdioma(DTOCrearIdioma dtoActualizarIdioma, Long id) throws Exception;
    String deshabilitarIdioma(Long id) throws Exception;
    String habilitarIdioma(Long id) throws Exception;
    List<Idioma> listarIdiomas();
}
