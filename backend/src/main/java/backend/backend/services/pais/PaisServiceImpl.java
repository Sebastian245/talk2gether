package backend.backend.services.pais;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.backend.DTO.DTOCrearPais;
import backend.backend.entities.Pais;
import backend.backend.repositories.PaisRepository;
import backend.backend.utils.validation.TextValidator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PaisServiceImpl implements PaisService {

    private final PaisRepository paisRepository;
    @Autowired
    private TextValidator textValidator;

    @Autowired
    public PaisServiceImpl(PaisRepository paisRepository) {
        this.paisRepository = paisRepository;
    }

    // Obtiene la lista de países que están activos
    @Override
    public List<String> listarPaisesActivos() {
        try {
            //Busca todos los paises que tengan fecha de fin de vigencia en nulo
            List<Pais> paises = paisRepository.findByFechaHoraFinVigenciaPaisIsNull();
            //Arma un arreglo con los nombres de los paises unicamente
            List<String> listaPaises = new ArrayList<>();
            for (Pais pais : paises) {
                listaPaises.add(pais.getNombrePais());
            }
            //Devuelve una lista de nombre de paises
            return listaPaises;
        } catch (Exception e) {
            throw new RuntimeException("Error al listar los países activos");
        }
    }

    // Obtiene un país por su ID
    @Override
    public Pais obtenerPaisPorId(Long id) {
        try {
            return paisRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("País no encontrado"));
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener el país por ID");
        }
    }

    // Crea un nuevo país con la información proporcionada
    @Override
    public Pais crearPais(DTOCrearPais dtoCrearPais) throws Exception {
        try {
            //Verificar si el pais que se intenta crear ya existe
            Optional<Pais> existingPais = paisRepository.findByNombrePais(dtoCrearPais.getNombrePais());
            if (existingPais.isPresent()) {
                throw new IllegalArgumentException("Ya existe un país con el mismo nombre.");
            }
            
            //Validar que el nombre del pais cumpla con las caracteristicas establecidas
            textValidator.validarTexto(dtoCrearPais.getNombrePais());
            //Si se cumplen se crea un nuevo pais con los datos enviados desde el navegador
            Pais pais = new Pais(new Date(), null, dtoCrearPais.getNombrePais(), dtoCrearPais.getUrlBandera());
            return paisRepository.save(pais);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (IllegalStateException e) {
            throw new IllegalStateException("Error al validar el texto: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error al crear el país", e);
        }
    }

    // Actualiza la información de un país existente
    //El ID es para localizar el pais que se quiere modificar en la base de datos y en el dto los datos con los que se lo va a actualizar
    @Override
    public Pais actualizarPais(DTOCrearPais dtoActualizarPais, Long id) throws Exception {
        try {
            //Verificar que el pais exista antes de actualizarlo
            Optional<Pais> paisExistente = paisRepository.findById(id);
            if (!paisExistente.isPresent()) {
                throw new IllegalArgumentException("El país que intenta modificar no existe.");
            }
            //Verificar que nombre del pais que se va a guardar no exista
            if(dtoActualizarPais.getNombrePais()!=null){
            Optional<Pais> existingPais = paisRepository.findByNombrePais(dtoActualizarPais.getNombrePais());
            if (existingPais.isPresent() && paisExistente.get().getNombrePais()!=existingPais.get().getNombrePais()) {
                throw new IllegalArgumentException("Ya existe un país con el mismo nombre.");
            }
            //Validar que el nombre del pais cumpla con las caracteristicas establecidas
            textValidator.validarTexto(dtoActualizarPais.getNombrePais());
            //Actualizar el pais
            
                paisExistente.get().setNombrePais(dtoActualizarPais.getNombrePais());
            }
            if(dtoActualizarPais.getUrlBandera()!=null){
                paisExistente.get().setUrlBandera(dtoActualizarPais.getUrlBandera());
            }
            return paisRepository.save(paisExistente.get());
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (IllegalStateException e) {
            throw new IllegalStateException("Error al validar el texto: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el país", e);
        }
    }

    // Deshabilita un país por su ID
    @Override
    public String deshabilitarPais(Long id) throws Exception {
        try {
            //Verificar que el pais que se quiere desabilitar existe
            Optional<Pais> paisExistente = paisRepository.findById(id);
            if (!paisExistente.isPresent()) {
                throw new IllegalArgumentException("El país que intenta deshabilitar no existe.");
            }
            //Obtener el pais que se quiere modificar
            Pais pais = obtenerPaisPorId(id);
            //Verificar que el pais no se encuentre deshabilitado
            if (pais.getFechaHoraFinVigenciaPais() != null) {
                throw new IllegalArgumentException("El país ya está deshabilitado");
            }
            //Agregarle una fecha de fin de vigencia
            pais.setFechaHoraFinVigenciaPais(new Date());
            paisRepository.save(pais);
            return "País deshabilitado correctamente";
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al deshabilitar el país", e);
        }
    }

    // Habilita un país por su ID
    @Override
    public String habilitarPais(Long id) throws Exception {
        try {
            //Verificar que el pais que se quiere desabilitar existe
            Optional<Pais> paisExistente = paisRepository.findById(id);
            if (!paisExistente.isPresent()) {
                throw new IllegalArgumentException("El país que intenta habilitar no existe.");
            }
            //Obtener el pais que se quiere modificar
            Pais pais = obtenerPaisPorId(id);
            //Verificar que el pais no se encuentre habilitado
            if (pais.getFechaHoraFinVigenciaPais() == null) {
                throw new IllegalArgumentException("El país ya está habilitado");
            }
            //Quitarle la fecha de fin de vigencia
            pais.setFechaHoraFinVigenciaPais(null);
            paisRepository.save(pais);
            return "País habilitado correctamente";
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al habilitar el país", e);
        }
    }

    //Listar todos los paises
    @Override
    public List<Pais> listarPaises() {
        try {
            return paisRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error al listar los países");
        }
    }
}
