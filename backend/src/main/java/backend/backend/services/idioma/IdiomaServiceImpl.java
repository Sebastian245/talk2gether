package backend.backend.services.idioma;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.backend.DTO.DTOCrearIdioma;
import backend.backend.entities.Idioma;
import backend.backend.repositories.IdiomaRepository;
import backend.backend.utils.validation.TextValidator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class IdiomaServiceImpl implements IdiomaService {

    private final IdiomaRepository idiomaRepository;

    @Autowired
    private TextValidator textValidator;

    @Autowired
    public IdiomaServiceImpl(IdiomaRepository idiomaRepository) {
        this.idiomaRepository = idiomaRepository;
    }

    // Obtiene la lista de idiomas que estan activos
    @Override
    public List<String> listarIdiomasActivos() {
        try {
            //Busca todos los idiomas que tengan fecha de fin de vigencia en nulo
            List<Idioma> idiomas = idiomaRepository.findByFechaHoraFinVigenciaIdiomaIsNull();
            //Arma un arreglo con los nombres de los idiomas unicamente
            List<String> listaIdiomas = new ArrayList<>();
            for(Idioma idioma : idiomas){
                listaIdiomas.add(idioma.getNombreIdioma());
            }
            //Devuelve una lista de nombre de idiomas
            return listaIdiomas;
        } catch (Exception e) {
            throw new RuntimeException("Error al listar los idiomas activos");
        }
    }
    // Obtiene un idioma por su ID
    @Override
    public Idioma obtenerIdiomaPorId(Long id) {
        try {
            return idiomaRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Idioma no encontrado"));
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener el idioma por ID");
        }
    }

    // Crea un nuevo idioma con la información proporcionada
    @Override
    public Idioma crearIdioma(DTOCrearIdioma dtoCrearIdioma) throws Exception {
        try {
            //Verificar si el idioma que se intenta crear ya existe
            Optional<Idioma> existingIdioma = idiomaRepository.findByNombreIdioma(dtoCrearIdioma.getNombreIdioma());
            if (existingIdioma.isPresent()) {
                throw new IllegalArgumentException("Ya existe un idioma con el mismo nombre.");
            }
            //Validar que el nombre del idioma cumpla con las caracteristicas establecidas
            textValidator.validarTexto(dtoCrearIdioma.getNombreIdioma());
            //Si se cumplen se crea un nuevo idioma con los datos enviados desde el navegador
            Idioma idioma = new Idioma(new Date(),null,dtoCrearIdioma.getNombreIdioma());
            return idiomaRepository.save(idioma);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (IllegalStateException e) {
            throw new IllegalStateException("Error al validar el texto: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error al crear el idioma", e);
        }
    }

    // Actualiza la información de un idioma existente
    //El ID es para localizar el idioma que se quiere modificar en la base de datos y en el dto los datos con los que se lo va a actualizar
    @Override
    public Idioma actualizarIdioma(DTOCrearIdioma dtoActualizarIdioma, Long id) throws Exception {
        try {
            //Verificar que el idioma exista antes de actualizarlo
            Optional<Idioma> idiomaExistente = idiomaRepository.findById(id);
            if (!idiomaExistente.isPresent()) {
                throw new IllegalArgumentException("El idioma que intenta modificar no existe.");
            }
            //Verificar que nombre del idioma que se va a guardar no exista
            Optional<Idioma> existingIdioma = idiomaRepository.findByNombreIdioma(dtoActualizarIdioma.getNombreIdioma());
            if (existingIdioma.isPresent()) {
                throw new IllegalArgumentException("Ya existe un idioma con el mismo nombre.");
            }
            //Validar que el nombre del idioma cumpla con las caracteristicas establecidas
            textValidator.validarTexto(dtoActualizarIdioma.getNombreIdioma());
            //Actualizar el idioma
            idiomaExistente.get().setNombreIdioma(dtoActualizarIdioma.getNombreIdioma());
            return idiomaRepository.save(idiomaExistente.get());
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (IllegalStateException e) {
            throw new IllegalStateException("Error al validar el texto: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el idioma", e);
        }
    }

    // Deshabilita un idioma por su ID
    @Override
    public String deshabilitarIdioma(Long id) throws Exception {
        try {
            //Verificar que el idioma que se quiere desabilitar existe
            Optional<Idioma> idiomaExistente = idiomaRepository.findById(id);
            if (!idiomaExistente.isPresent()) {
                throw new IllegalArgumentException("El idioma que intenta deshabilitar no existe.");
            }
            //Obtener el idioma que se quiere modificar
            Idioma idioma = obtenerIdiomaPorId(id);
            //Verificar que el idioma no se encuentre deshabilitado
            if (idioma.getFechaHoraFinVigenciaIdioma() != null) {
                throw new IllegalArgumentException("El idioma ya está deshabilitado");
            }
            //Agregarle una fecha de fin de vigencia
            idioma.setFechaHoraFinVigenciaIdioma(new Date());
            idiomaRepository.save(idioma);
            return "Idioma deshabilitado correctamente";
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al deshabilitar el idioma", e);
        }
    }

    // Habilita un idioma por su ID
    @Override
    public String habilitarIdioma(Long id) throws Exception {
        try {
            //Verificar que el idioma que se quiere desabilitar existe
            Optional<Idioma> idiomaExistente = idiomaRepository.findById(id);
            if (!idiomaExistente.isPresent()) {
                throw new IllegalArgumentException("El idioma que intenta habilitar no existe.");
            }
            //Obtener el idioma que se quiere modificar
            Idioma idioma = obtenerIdiomaPorId(id);
            //Verificar que el idioma no se encuentre habilitado
            if (idioma.getFechaHoraFinVigenciaIdioma() == null) {
                throw new IllegalArgumentException("El idioma ya está habilitado");
            }
            //Quitarle la fecha de fin de vigencia
            idioma.setFechaHoraFinVigenciaIdioma(null);
            idiomaRepository.save(idioma);
            return "Idioma habilitado correctamente";
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al habilitar el idioma", e);
        }
    }

    //Listar todos los idiomas
    @Override
    public List<Idioma> listarIdiomas() {
        try {
            return idiomaRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error al listar los idiomas");
        }    
    }
}
