package backend.backend.services.rol;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.backend.DTO.DTOCrearRol;
import backend.backend.entities.Cuenta;
import backend.backend.entities.Permiso;
import backend.backend.entities.Rol;
import backend.backend.repositories.CuentaRepository;
import backend.backend.repositories.PermisoRepository;
import backend.backend.repositories.RolRepository;
import backend.backend.utils.validation.TextValidator;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RolServiceImpl implements RolService {

    private final RolRepository rolRepository;
    @Autowired
    private TextValidator textValidator;
    @Autowired
    PermisoRepository permisoRepository;
    @Autowired
    public RolServiceImpl(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }
    @Autowired
    CuentaRepository cuentaRepository;

    @Override
    public List<Rol> listarRoles() {
        try {
            return rolRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error al listar los roles");
        }
    }

    @Override
    public Rol obtenerRolPorId(Long id) {
        try {
            return rolRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado"));
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener el rol por ID");
        }
    }

    @Override
    public Rol crearRol(DTOCrearRol dtoCrearRol) throws Exception {
        try {
            Optional<Rol> existingRol = rolRepository.findByNombreRol(dtoCrearRol.getNombreRol());
            if (existingRol.isPresent()) {
                throw new IllegalArgumentException("Ya existe un rol con el mismo nombre.");
            }
            Rol rol = new Rol();
            rol.setFechaHoraAltaRol(new Date());
            rol.setNombreRol(dtoCrearRol.getNombreRol());
            // Asignar permisos al rol según la lista de permisos recibida en el DTO
            // Puedes implementar esta lógica según tus necesidades
            textValidator.validarTexto(dtoCrearRol.getNombreRol());
            return rolRepository.save(rol);
        } catch (IllegalArgumentException e) {
            throw e;
        } 
         catch (IllegalStateException e) {
            throw new IllegalStateException("Error al validar el texto: " + e.getMessage());
        } 
        catch (Exception e) {
            throw new RuntimeException("Error al crear el rol", e);
        }
    }


    @Override
    public Rol actualizarRol(DTOCrearRol dtoActualizarRol, Long id) throws Exception {
        try {
            Optional<Rol> rolExistente = rolRepository.findById(id);
            if (!rolExistente.isPresent()) {
                throw new IllegalArgumentException("El rol que intenta modificar no existe.");
            }
            Optional<Rol> existingRol = rolRepository.findByNombreRol(dtoActualizarRol.getNombreRol());
            if (existingRol.isPresent() && !existingRol.get().getId().equals(id)) {
                throw new IllegalArgumentException("Ya existe un rol con el mismo nombre.");
            }
            Rol rol = rolExistente.get();
            textValidator.validarTexto(dtoActualizarRol.getNombreRol());
            rol.setNombreRol(dtoActualizarRol.getNombreRol());
            // Actualizar permisos del rol según la lista de permisos recibida en el DTO
            // Puedes implementar esta lógica según tus necesidades

            return rolRepository.save(rol);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (IllegalStateException e){
            throw e;
        }
        catch (Exception e) {
            throw new RuntimeException("Error al actualizar el rol", e);
        }
    }

    @Override
    public String deshabilitarRol(Long id) throws Exception {
        try {
            Optional<Rol> rolExistente = rolRepository.findById(id);
            if (!rolExistente.isPresent()) {
                throw new IllegalArgumentException("El rol que intenta deshabilitar no existe.");
            }
            Rol rol = rolExistente.get();
            if (rol.getFechaHoraFinVigenciaRol() != null) {
                throw new IllegalArgumentException("El rol ya está deshabilitado");
            }
            rol.setFechaHoraFinVigenciaRol(new Date());
            List<Permiso> permisos = permisoRepository.findAll();
            for (Permiso permisoRecorrer : permisos) {
                if(permisoRecorrer.getListaRol().contains(rol)){
                    permisoRecorrer.getListaRol().remove(rol);
                    permisoRepository.save(permisoRecorrer);
                }
            }
            Optional<Rol> rolUsuario = rolRepository.findById(1l);
            List<Cuenta> cuentaRol = cuentaRepository.buscarUsuarioPorRol(id);
            for (Cuenta cuenta : cuentaRol) {
                cuenta.setRol(rolUsuario.get());
                cuentaRepository.save(cuenta);
            }
            rolRepository.save(rol);
            return "Rol deshabilitado correctamente";
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al deshabilitar el rol", e);
        }
    }

    @Override
    public String habilitarRol(Long id) throws Exception {
        try {
            Optional<Rol> rolExistente = rolRepository.findById(id);
            if (!rolExistente.isPresent()) {
                throw new IllegalArgumentException("El rol que intenta habilitar no existe.");
            }
            Rol rol = rolExistente.get();
            if (rol.getFechaHoraFinVigenciaRol() == null) {
                throw new IllegalArgumentException("El rol ya está habilitado");
            }
            rol.setFechaHoraFinVigenciaRol(null);
            rolRepository.save(rol);
            return "Rol habilitado correctamente";
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al habilitar el rol", e);
        }
    }

    @Override
    public List<Rol> listarRolesActivos() {
        try {
            return rolRepository.findByFechaHoraFinVigenciaRolIsNull();
        } catch (Exception e) {
            throw new RuntimeException("Error al listar los roles activos");
        }    
    }
}
