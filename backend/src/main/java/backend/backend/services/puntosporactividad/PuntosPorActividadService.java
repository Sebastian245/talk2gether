package backend.backend.services.puntosporactividad;

import java.util.List;

import backend.backend.DTO.DTOCrearPuntosPorActividad;
import backend.backend.entities.PuntosPorActividad;

public interface PuntosPorActividadService {

    public List<PuntosPorActividad> listarPuntosPorActividad();

    public List<PuntosPorActividad> listarPuntosPorActividadActivos();

    public PuntosPorActividad obtenerPuntosPorActividadPorId(Long id) throws Exception;

    //public String deshabilitarPuntosPorActividad(Long id) throws Exception;

    //public String habilitarPuntosPorActividad(Long id) throws Exception;

    //public PuntosPorActividad crearPuntosPorActividad(DTOCrearPuntosPorActividad dtoCrearPuntosPorActividad) throws Exception;

    public PuntosPorActividad actualizarPuntosPorActividad(DTOCrearPuntosPorActividad dtoActualizarPuntosPorActividad, Long id) throws Exception;
}
