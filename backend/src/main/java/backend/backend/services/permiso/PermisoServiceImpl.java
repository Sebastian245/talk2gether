package backend.backend.services.permiso;

import backend.backend.DTO.DTOActualizarRolesAPermisos;
import backend.backend.DTO.DTOPermiso;
import backend.backend.entities.Permiso;
import backend.backend.entities.Rol;
import backend.backend.repositories.PermisoRepository;
import backend.backend.repositories.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class PermisoServiceImpl implements PermisoService {

    public PermisoServiceImpl() {
    }

    @Autowired
    private PermisoRepository permisoRepository;

    @Autowired
    private RolRepository rolRepository;


    //Metodo para crear un permiso, este no se deberia utilizar nunca, solo cuando se quieren cargar en la base de datos
    public Permiso crearPermiso(DTOActualizarRolesAPermisos dtoActualizarRolesAPermisos) throws Exception {
        Optional<Permiso> permiso = permisoRepository.findByUrl(dtoActualizarRolesAPermisos.getNombrePermiso());
        if(permiso.isPresent()){
            throw new Exception("La url ya existe, vaya a editarla");
        }
        Permiso nuevoPermiso=new Permiso();
        for (String rol: dtoActualizarRolesAPermisos.getListaNombreRol()) {
            Optional<Rol> role = rolRepository.findByNombreRol(rol);
            if(!role.isPresent()){
                throw new Exception("El rol no existe");
            }
            nuevoPermiso.setUrl(dtoActualizarRolesAPermisos.getNombrePermiso());
            nuevoPermiso.setFechaHoraAltaPermiso(new Date());
            nuevoPermiso.getListaRol().add(role.get());
        }
        permisoRepository.save(nuevoPermiso);
        return nuevoPermiso;
    }

    //Metodo para añadir o quitar roles a un permiso
    public String actualizar(DTOActualizarRolesAPermisos dtoActualizarRolesAPermisos) throws Exception {
        if(!permisoRepository.findByUrl(dtoActualizarRolesAPermisos.getNombrePermiso()).isPresent()){
            throw new Exception("El permiso que se quiere modificar no existe");
        };
        Optional<Permiso> permiso = permisoRepository.findByUrl(dtoActualizarRolesAPermisos.getNombrePermiso());
        if(!permiso.isPresent()){
            throw new Exception("La url no existe");
        }
        
        permiso.get().getListaRol().clear();
        for (String rol: dtoActualizarRolesAPermisos.getListaNombreRol()) {
            Optional<Rol> role = rolRepository.findByNombreRol(rol);
            if(!role.isPresent()){
                throw new Exception("El rol no existe");
            }
            permiso.get().getListaRol().add(role.get());
        }
        permisoRepository.save(permiso.get());
        return "Permiso actualizado correctamente";
    }

    //Muestra un solo permiso con toda su informacion
    public DTOPermiso ObtenerUnDTOPermiso(Long id) throws Exception {
        if(!permisoRepository.existsById(id)){
            throw new Exception("El permiso que se busca no existe");
        }
        Optional<Permiso> permiso= permisoRepository.findById(id);
        DTOPermiso dtoPermiso=new DTOPermiso();
        dtoPermiso.setId(permiso.get().getId());
        dtoPermiso.setNombrePermiso(permiso.get().getUrl());
        dtoPermiso.setDescripcion(permiso.get().getNombrePermiso());
        for (Rol rol:
                permiso.get().getListaRol()) {
            dtoPermiso.getListaNombreRol().add(rol.getNombreRol());
        }
        return dtoPermiso;
    }

    //Muestra una lista detallada de cada permiso con sus roles respectivos
    public List<DTOPermiso> listarPermisosDTO() {
        List<Permiso> listaPermisos = permisoRepository.findAll();
        List<DTOPermiso> listaPermisosDTO=new ArrayList<>();
        for (Permiso permiso: listaPermisos) {
            Set<String> listaRoles = new HashSet<>();

            for (Rol rol: permiso.getListaRol()) {
                listaRoles.add(rol.getNombreRol());
            }

            listaPermisosDTO.add(new DTOPermiso(permiso.getId(), permiso.getUrl(),permiso.getNombrePermiso(), listaRoles));
        }
        return listaPermisosDTO;
    }

    /*public boolean verificarAcceso(String urlPermiso,String nombreRol) throws Exception{
        boolean tieneAcceso=permisoRepository.verificarSeguimiento(urlPermiso,nombreRol);
        return tieneAcceso ;
    }*/

    //Muestra un listado con todos los permisos a los que puede acceder ese rol
    @Override
    public List<String> listaPermisosParaUnRol(String nombreRol) throws Exception {
        if(!rolRepository.findByNombreRol(nombreRol).isPresent()){
            throw new Exception("El rol ingresado no existe");
        }
        return permisoRepository.listaPermisosParaUnRol(nombreRol);
    }
}
