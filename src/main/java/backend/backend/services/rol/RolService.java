package backend.backend.services.rol;

import java.util.List;

import backend.backend.DTO.DTOCrearRol;
import backend.backend.entities.Rol;

public interface RolService {
    List<Rol> listarRoles();
    List<Rol> listarRolesActivos();
    Rol obtenerRolPorId(Long id);
    Rol crearRol(DTOCrearRol dtoCrearRol) throws Exception;
    Rol actualizarRol(DTOCrearRol dtoActualizarRol, Long id) throws Exception;
    String deshabilitarRol(Long id) throws Exception;
    String habilitarRol(Long id) throws Exception;
}
