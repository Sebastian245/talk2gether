package backend.backend.services.nivelidioma;

import backend.backend.DTO.DTOCrearNivelIdioma;
import backend.backend.entities.NivelIdioma;

import java.util.List;

public interface NivelIdiomaService {
    List<String> listarNivelesIdiomaActivos();

    NivelIdioma obtenerNivelIdiomaPorId(Long id);

    NivelIdioma crearNivelIdioma(DTOCrearNivelIdioma dtoCrearNivelIdioma) throws Exception;

    NivelIdioma actualizarNivelIdioma(DTOCrearNivelIdioma dtoCrearNivelIdioma, Long id) throws Exception;

    String deshabilitarNivelIdioma(Long id) throws Exception;

    String habilitarNivelIdioma(Long id) throws Exception;

    List<NivelIdioma> listarNivelesIdioma();
}
