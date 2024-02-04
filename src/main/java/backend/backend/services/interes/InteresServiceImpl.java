package backend.backend.services.interes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.backend.DTO.DTOCrearInteres;
import backend.backend.DTO.DTOListarInteres;
import backend.backend.entities.Interes;
import backend.backend.entities.UsuarioInteres;
import backend.backend.repositories.InteresRepository;
import backend.backend.repositories.UsuarioInteresRepository;
import backend.backend.utils.validation.TextValidator;

@Service
public class InteresServiceImpl implements InteresService {

    private final InteresRepository interesRepository;
    @Autowired
    private UsuarioInteresRepository usuarioInteresRepository;
    @Autowired
    private TextValidator textValidator;

    @Autowired
    public InteresServiceImpl(InteresRepository interesRepository) {
        this.interesRepository = interesRepository;
    }

    // Obtiene la lista de intereses que están activos
    @Override
    public List<DTOListarInteres> listarInteresesActivos() {
        try {
            //Busca todos los intereses que tengan fecha de fin de vigencia en nulo
            List<Interes> intereses = interesRepository.findByFechaHoraFinVigenciaInteresIsNull();
            //Arma un arreglo con los nombres de los paises unicamente
            List<DTOListarInteres> listaIntereses = new ArrayList<>();
            for(Interes interes : intereses){
                DTOListarInteres dtoInteres = new DTOListarInteres(interes.getId(),interes.getNombreInteres(),interes.getUrlInteres(),false);
                listaIntereses.add(dtoInteres);
            }
            //Devuelve una lista de nombre de paises
            return listaIntereses;
        } catch (Exception e) {
            throw new RuntimeException("Error al listar los intereses activos");
        }
    }

    // Obtiene un interes por su ID
    @Override
    public Interes obtenerInteresPorId(Long id) {
        try {
            return interesRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Interés no encontrado"));
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener el interés por ID");
        }
    }

    // Crea un nuevo interes con la información proporcionada
    @Override
    public Interes crearInteres(DTOCrearInteres dtoCrearInteres) throws Exception {
        try {
            //Verificar si el interes que se intenta crear ya existe
            Optional<Interes> existingInteres = interesRepository.findByNombreInteres(dtoCrearInteres.getNombreInteres());
            if (existingInteres.isPresent()) {
                throw new Exception("Ya existe un interés con el mismo nombre.");
            }
            if(dtoCrearInteres.getUrlInteres()==null){
                throw new Exception("El URL de imagen de interes no puede ser nulo.");
            }
            //Validar que el nombre del interes cumpla con las caracteristicas establecidas
            textValidator.validarTexto(dtoCrearInteres.getNombreInteres());
            //Si se cumplen se crea un nuevo interes con los datos enviados desde el navegador
            Interes interes = new Interes(new Date(), null, dtoCrearInteres.getNombreInteres(),dtoCrearInteres.getUrlInteres());
            return interesRepository.save(interes);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (IllegalStateException e) {
            throw new IllegalStateException("Error al validar el texto: " + e.getMessage());
        } 
    }

    // Actualiza la información de un interes existente
    //El ID es para localizar el interes que se quiere modificar en la base de datos y en el dto los datos con los que se lo va a actualizar
    @Override
    public Interes actualizarInteres(Long id,DTOCrearInteres dtoActualizarInteres) throws Exception {
        try {
            //Verificar que el interes exista antes de actualizarlo
            Optional<Interes> interesExistente = interesRepository.findById(id);
            if (!interesExistente.isPresent()) {
                throw new IllegalArgumentException("El interés que intenta modificar no existe.");
            }
            //Verificar que nombre del interes que se va a guardar no exista
            Optional<Interes> existingInteres = interesRepository.findByNombreInteres(dtoActualizarInteres.getNombreInteres());
            if (existingInteres.isPresent() && interesExistente.get().getNombreInteres()!=existingInteres.get().getNombreInteres()) {
                throw new IllegalArgumentException("Ya existe un interés con el mismo nombre.");
            }
            //Validar que el nombre del interes cumpla con las caracteristicas establecidas
            textValidator.validarTexto(dtoActualizarInteres.getNombreInteres());
            //Actualizar el interes
            interesExistente.get().setNombreInteres(dtoActualizarInteres.getNombreInteres());
            if(dtoActualizarInteres.getUrlInteres()!=null){
            interesExistente.get().setUrlInteres(dtoActualizarInteres.getUrlInteres());
            }else throw new Exception("El URL de imagen de interes no puede ser nulo.");
            return interesRepository.save(interesExistente.get());
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (IllegalStateException e) {
            throw new IllegalStateException("Error al validar el texto: " + e.getMessage());
        } 
    }

    // Deshabilita un interes por su ID
    @Override
    public String deshabilitarInteres(Long id) throws Exception { 
        try {
            //Verificar que el interes que se quiere desabilitar existe
            Optional<Interes> interesExistente = interesRepository.findById(id);
            if (!interesExistente.isPresent()) {
                throw new IllegalArgumentException("El interés que intenta deshabilitar no existe.");
            }
            //Obtener el interes que se quiere modificar
            Interes interes = obtenerInteresPorId(id);
            //Verificar que el interes no se encuentre deshabilitado
            if (interes.getFechaHoraFinVigenciaInteres() != null) {
                throw new IllegalArgumentException("El interés ya está deshabilitado");
            }
            //Agregarle una fecha de fin de vigencia
            interes.setFechaHoraFinVigenciaInteres(new Date());
            List<UsuarioInteres> usuarioInteres = usuarioInteresRepository.findAll();
            for (UsuarioInteres usuarioInteres2 : usuarioInteres) {
               if(usuarioInteres2.getInteres().getId()==id){
                usuarioInteres2.setFechaHoraFinVigenciaInteres(new Date());
                usuarioInteresRepository.save(usuarioInteres2);
               }
            }
            interesRepository.save(interes);
            return "Interés deshabilitado correctamente";
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al deshabilitar el interés", e);
        }
    }

    // Habilita un interes por su ID
    @Override
    public String habilitarInteres(Long id) throws Exception {
        try {
            //Verificar que el interes que se quiere desabilitar existe
            Optional<Interes> interesExistente = interesRepository.findById(id);
            if (!interesExistente.isPresent()) {
                throw new IllegalArgumentException("El interés que intenta habilitar no existe.");
            }
            //Obtener el interes que se quiere modificar
            Interes interes = obtenerInteresPorId(id);
            //Verificar que el interes no se encuentre habilitado
            if (interes.getFechaHoraFinVigenciaInteres() == null) {
                throw new IllegalArgumentException("El interés ya está habilitado");
            }
            //Quitarle la fecha de fin de vigencia
            interes.setFechaHoraFinVigenciaInteres(null);
            interesRepository.save(interes);
            return "Interés habilitado correctamente";
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al habilitar el interés", e);
        }
    }

    //Listar todos los intereses
    @Override
    public List<Interes> listarIntereses() {
        try {
            return interesRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error al listar los intereses");
        }
    }
    
}
