package backend.backend.services.puntosporactividad;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.backend.DTO.DTOCrearPuntosPorActividad;
import backend.backend.entities.PuntosPorActividad;
import backend.backend.repositories.PuntosPorActividadRepository;
import backend.backend.utils.validation.NumberValidator;

@Service
public class PuntosPorActividadServiceImpl implements PuntosPorActividadService {

    private final PuntosPorActividadRepository puntosPorActividadRepository;
    @Autowired
    private NumberValidator numberValidator;

    @Autowired
    public PuntosPorActividadServiceImpl(PuntosPorActividadRepository puntosPorActividadRepository) {
        this.puntosPorActividadRepository = puntosPorActividadRepository;
    }

    @Override
    public List<PuntosPorActividad> listarPuntosPorActividadActivos() {
        try {
            return puntosPorActividadRepository.findByFechaHoraFinVigenciaPuntosPorActividadIsNull();
        } catch (Exception e) {
            throw new RuntimeException("Error al listar los Puntos por actividad activos");
        }
    }

    @Override
    public PuntosPorActividad obtenerPuntosPorActividadPorId(Long id) {
        try {
            return puntosPorActividadRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Puntos por actividad no encontrados"));
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener los puntos por actividad por ID");
        }
    }

    /* @Override
    public PuntosPorActividad crearPuntosPorActividad(DTOCrearPuntosPorActividad dtoCrearPuntosPorActividad)
            throws Exception {
        try {
            Optional<PuntosPorActividad> existingPuntosPorActividad = puntosPorActividadRepository
                    .findByNombrePuntosPorActividad(dtoCrearPuntosPorActividad.getNombrePuntosPorActividad());
            if (existingPuntosPorActividad.isPresent()) {
                throw new IllegalArgumentException("Ya existen puntos por actividad con el mismo nombre.");
            }
            textValidator.validarTexto(dtoCrearPuntosPorActividad.getNombrePuntosPorActividad());
            numberValidator.validarEntero(dtoCrearPuntosPorActividad.getPuntosPorActividad());
            PuntosPorActividad puntosPorActividad = new PuntosPorActividad(new Date(), null,
                    dtoCrearPuntosPorActividad.getNombrePuntosPorActividad(),
                    dtoCrearPuntosPorActividad.getPuntosPorActividad());
            return puntosPorActividadRepository.save(puntosPorActividad);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (IllegalStateException e) {
            throw new IllegalStateException("Error al validar el texto: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error al crear los puntos por actividad", e);
        }
    } */

    @Override
    public PuntosPorActividad actualizarPuntosPorActividad(DTOCrearPuntosPorActividad dtoActualizarPuntosPorActividad,
            Long id) throws Exception {
        try {
            Optional<PuntosPorActividad> puntosPorActividadExistente = puntosPorActividadRepository.findById(id);
            if (!puntosPorActividadExistente.isPresent()) {
                throw new IllegalArgumentException("Los puntos por actividad que intenta modificar no existen.");
            }
            if(dtoActualizarPuntosPorActividad.getNombrePuntosPorActividad()!=null){
               //VALIDAR?
                puntosPorActividadExistente.get().setNombrePuntosPorActividad(dtoActualizarPuntosPorActividad.getNombrePuntosPorActividad());
            }
            if(dtoActualizarPuntosPorActividad.getValorMaximo()!=null){
                puntosPorActividadExistente.get().setPuntosMaximos(Integer.parseInt(dtoActualizarPuntosPorActividad.getValorMaximo()));
            }
            if(dtoActualizarPuntosPorActividad.getDescripcion()!=null){
                puntosPorActividadExistente.get().setDescripcionPuntosPorActividad(dtoActualizarPuntosPorActividad.getDescripcion());
            }
            if(dtoActualizarPuntosPorActividad.getPuntosPorActividad()!=null){
                numberValidator.validarEntero(Integer.parseInt(dtoActualizarPuntosPorActividad.getPuntosPorActividad()));
                puntosPorActividadExistente.get().setPuntosPorActividad(Integer.parseInt(dtoActualizarPuntosPorActividad.getPuntosPorActividad()));
            }
            
            return puntosPorActividadRepository.save(puntosPorActividadExistente.get());
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar los puntos por actividad", e);
        }
    }

/*     @Override
    public String deshabilitarPuntosPorActividad(Long id) throws Exception {
        try {
            Optional<PuntosPorActividad> puntosPorActividadExistente = puntosPorActividadRepository.findById(id);
            if (!puntosPorActividadExistente.isPresent()) {
                throw new IllegalArgumentException("Los puntos por actividad que intenta deshabilitar no existen.");
            }
            PuntosPorActividad puntosPorActividad = obtenerPuntosPorActividadPorId(id);
            if (puntosPorActividad.getFechaHoraFinVigenciaPuntosPorActividad() != null) {
                throw new IllegalArgumentException("Los puntos por actividad ya están deshabilitados");
            }
            puntosPorActividad.setFechaHoraFinVigenciaPuntosPorActividad(new Date());
            puntosPorActividadRepository.save(puntosPorActividad);
            return "Puntos por actividad deshabilitados correctamente";
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al deshabilitar los puntos por actividad", e);
        }
    }

    @Override
    public String habilitarPuntosPorActividad(Long id) throws Exception {
        try {
            Optional<PuntosPorActividad> puntosPorActividadExistente = puntosPorActividadRepository.findById(id);
            if (!puntosPorActividadExistente.isPresent()) {
                throw new IllegalArgumentException("Los puntos por actividad que intenta habilitar no existen.");
            }
            PuntosPorActividad puntosPorActividad = obtenerPuntosPorActividadPorId(id);
            if (puntosPorActividad.getFechaHoraFinVigenciaPuntosPorActividad() == null) {
                throw new IllegalArgumentException("Los puntos por actividad ya están habilitados");
            }
            puntosPorActividad.setFechaHoraFinVigenciaPuntosPorActividad(null);
            puntosPorActividadRepository.save(puntosPorActividad);
            return "Puntos por actividad habilitados correctamente";
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al habilitar los puntos por actividad", e);
        }
    } */

    @Override
    public List<PuntosPorActividad> listarPuntosPorActividad() {
        try {
            return puntosPorActividadRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error al listar los puntos por actividad");
        }
    }
}
