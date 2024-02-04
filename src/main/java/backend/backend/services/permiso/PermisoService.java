package backend.backend.services.permiso;

import backend.backend.DTO.DTOActualizarRolesAPermisos;
import backend.backend.DTO.DTOPermiso;
import backend.backend.entities.Permiso;

import java.util.List;

public interface PermisoService {

    public Permiso crearPermiso(DTOActualizarRolesAPermisos dtoActualizarRolesAPermisos) throws Exception;

    public String actualizar(DTOActualizarRolesAPermisos dtoActualizarRolesAPermisos) throws Exception;

    public DTOPermiso ObtenerUnDTOPermiso(Long id) throws Exception;

    public List<DTOPermiso> listarPermisosDTO() throws Exception;

    //public boolean verificarAcceso(String urlPermiso,String nombreRol) throws Exception;

    public List<String> listaPermisosParaUnRol(String nombreRol) throws Exception;
}
