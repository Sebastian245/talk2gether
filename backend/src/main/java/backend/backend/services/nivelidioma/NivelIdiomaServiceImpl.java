package backend.backend.services.nivelidioma;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.backend.DTO.DTOCrearNivelIdioma;
import backend.backend.entities.NivelIdioma;
import backend.backend.repositories.NivelIdiomaRepository;
import backend.backend.utils.validation.TextValidator;

@Service
public class NivelIdiomaServiceImpl implements NivelIdiomaService {

    private final NivelIdiomaRepository nivelIdiomaRepository;

    @Autowired
    private TextValidator textValidator;

    @Autowired
    public NivelIdiomaServiceImpl(NivelIdiomaRepository nivelIdiomaRepository) {
        this.nivelIdiomaRepository = nivelIdiomaRepository;
    }

    // Obtiene la lista de niveles de idioma que están activos
    @Override
    public List<String> listarNivelesIdiomaActivos() {
        try {
            //Busca todos los niveles de idioma que tengan fecha de fin de vigencia en nulo
            List<NivelIdioma> nivelIdiomas = nivelIdiomaRepository.findByFechaHoraFinVigenciaNivelIdiomaIsNull();
            //Arma un arreglo con los nombres de los niveles de idioma unicamente
            List<String> listaNivelIdiomas = new ArrayList<>();
            //El nativo no se muestra, ese se setea directamente por codigo
            for (NivelIdioma nivelIdioma : nivelIdiomas) {
                if (!nivelIdioma.getNombreNivelIdioma().equals("Nativo")) {
                    listaNivelIdiomas.add(nivelIdioma.getNombreNivelIdioma());
                }

            }
            //Devuelve una lista de niveles de idioma
            return listaNivelIdiomas;
        } catch (Exception e) {
            throw new RuntimeException("Error al listar los niveles de idioma activos");
        }
    }

    // Obtiene un nivel de idioma por su ID
    @Override
    public NivelIdioma obtenerNivelIdiomaPorId(Long id) {
        try {
            return nivelIdiomaRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Nivel de idioma no encontrado"));
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener el nivel de idioma.");
        }
    }

    // Crea un nuevo nivel de idioma con la información proporcionada
    @Override
    public NivelIdioma crearNivelIdioma(DTOCrearNivelIdioma dtoCrearNivelIdioma) throws Exception {
        try {
            //Verificar si el nivel de idioma que se intenta crear ya existe
            Optional<NivelIdioma> existingNivelIdioma = nivelIdiomaRepository
                    .findByNombreNivelIdioma(dtoCrearNivelIdioma.getNombreNivelIdioma());
            if (existingNivelIdioma.isPresent()) {
                throw new IllegalArgumentException("Ya existe un nivel de idioma con el mismo nombre.");
            }
            //Validar que el nombre del nivel de idioma cumpla con las caracteristicas establecidas
            textValidator.validarTexto(dtoCrearNivelIdioma.getNombreNivelIdioma());
            //Si se cumplen se crea un nuevo nivel de idioma con los datos enviados desde el navegador
            NivelIdioma nivelIdioma = new NivelIdioma(new Date(), null, dtoCrearNivelIdioma.getNombreNivelIdioma());
            return nivelIdiomaRepository.save(nivelIdioma);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (IllegalStateException e) {
            throw new IllegalStateException("Error al validar el texto: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error al crear el nivel de idioma", e);
        }
    }

    // Actualiza la información de un nivel de idioma existente
    //El ID es para localizar el nivel de idioma que se quiere modificar en la base de datos y en el dto los datos con los que se lo va a actualizar
    @Override
    public NivelIdioma actualizarNivelIdioma(DTOCrearNivelIdioma dtoActualizarNivelIdioma, Long id) throws Exception {
        try {
            //Verificar que el nivel de idioma exista antes de actualizarlo
            Optional<NivelIdioma> nivelIdiomaExistente = nivelIdiomaRepository.findById(id);
            if (!nivelIdiomaExistente.isPresent()) {
                throw new IllegalArgumentException("El nivel de idioma que intenta modificar no existe.");
            }
            //Verificar que nombre del nivel de idioma que se va a guardar no exista
            Optional<NivelIdioma> existingNivelIdioma = nivelIdiomaRepository
                    .findByNombreNivelIdioma(dtoActualizarNivelIdioma.getNombreNivelIdioma());
            if (existingNivelIdioma.isPresent() && nivelIdiomaExistente.get().getNombreNivelIdioma()!=existingNivelIdioma.get().getNombreNivelIdioma()) {
                throw new IllegalArgumentException("Ya existe un nivel de idioma con el mismo nombre.");
            }
            //Validar que el nombre del nivel de idioma cumpla con las caracteristicas establecidas
            textValidator.validarTexto(dtoActualizarNivelIdioma.getNombreNivelIdioma());
            //Actualizar el nivel de idioma
            nivelIdiomaExistente.get().setNombreNivelIdioma(dtoActualizarNivelIdioma.getNombreNivelIdioma());
            return nivelIdiomaRepository.save(nivelIdiomaExistente.get());
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (IllegalStateException e) {
            throw new IllegalStateException("Error al validar el texto: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el nivel de idioma", e);
        }
    }

    // Deshabilita un nivel de idioma por su ID
    @Override
    public String deshabilitarNivelIdioma(Long id) throws Exception {
        try {
            //Verificar que el nivel de idioma que se quiere desabilitar existe
            Optional<NivelIdioma> nivelIdiomaExistente = nivelIdiomaRepository.findById(id);
            if (!nivelIdiomaExistente.isPresent()) {
                throw new IllegalArgumentException("El nivel de idioma que intenta deshabilitar no existe.");
            }
            //Obtener el nivel de idioma que se quiere modificar
            NivelIdioma nivelIdioma = obtenerNivelIdiomaPorId(id);
            //Verificar que el nivel de idioma no se encuentre deshabilitado
            if (nivelIdioma.getFechaHoraFinVigenciaNivelIdioma() != null) {
                throw new IllegalArgumentException("El nivel de idioma ya está deshabilitado");
            }
            //Agregarle una fecha de fin de vigencia
            nivelIdioma.setFechaHoraFinVigenciaNivelIdioma(new Date());
            nivelIdiomaRepository.save(nivelIdioma);
            return "Nivel de idioma deshabilitado correctamente";
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al deshabilitar el nivel de idioma", e);
        }
    }

    // Habilita un nivel de idioma por su ID
    @Override
    public String habilitarNivelIdioma(Long id) throws Exception {
        try {
            //Verificar que el nivel de idioma que se quiere desabilitar existe
            Optional<NivelIdioma> nivelIdiomaExistente = nivelIdiomaRepository.findById(id);
            if (!nivelIdiomaExistente.isPresent()) {
                throw new IllegalArgumentException("El nivel de idioma que intenta habilitar no existe.");
            }
            //Obtener el nivel de idioma que se quiere modificar
            NivelIdioma nivelIdioma = obtenerNivelIdiomaPorId(id);
            //Verificar que el nivel de idioma no se encuentre habilitado
            if (nivelIdioma.getFechaHoraFinVigenciaNivelIdioma() == null) {
                throw new IllegalArgumentException("El nivel de idioma ya está habilitado");
            }
            //Quitarle la fecha de fin de vigencia
            nivelIdioma.setFechaHoraFinVigenciaNivelIdioma(null);
            nivelIdiomaRepository.save(nivelIdioma);
            return "Nivel de idioma habilitado correctamente";
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al habilitar el nivel de idioma", e);
        }
    }

    //Listar todos los niveles de idioma
    @Override
    public List<NivelIdioma> listarNivelesIdioma() {
        try {
            return nivelIdiomaRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error al listar los niveles de idioma");
        }
    }
}
