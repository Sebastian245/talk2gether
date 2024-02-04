package backend.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.backend.DTO.DTOActualizarRolesAPermisos;
import backend.backend.DTO.DTORespuesta;
import backend.backend.services.permiso.PermisoService;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/permisos")
public class PermisoController {

    @Autowired
    private PermisoService permisoService;

    //Metodo para crear un permiso, este no se deberia utilizar nunca, solo cuando se quieren cargar en la base de datos
    @PostMapping("/crearPermiso")
    public ResponseEntity<?> crearPermiso( @RequestBody DTOActualizarRolesAPermisos dtoActualizarRolesAPermisos){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(permisoService.crearPermiso(dtoActualizarRolesAPermisos));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new DTORespuesta(e.getMessage()));
        }
    }

    //Metodo para añadir o quitar roles a un permiso
    @PutMapping("/editarpermiso")
    public ResponseEntity<?> editarpermiso( @RequestBody DTOActualizarRolesAPermisos dtoActualizarRolesAPermisos){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(new DTORespuesta(permisoService.actualizar(dtoActualizarRolesAPermisos)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new DTORespuesta(e.getMessage()));
        }
    }

    //Muestra una lista detallada de cada permiso con sus roles respectivos
    @GetMapping("/listarpermisos")
    public ResponseEntity<?> listarPermisosDTO(){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(permisoService.listarPermisosDTO());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new DTORespuesta(e.getMessage()));
        }
    }

    //Muestra un solo permiso con toda su informacion
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerUnPermiso(@PathVariable Long id) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(permisoService.ObtenerUnDTOPermiso(id));

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new DTORespuesta(e.getMessage()));
        }
    }

    //@GetMapping("/verificar")
    /*public ResponseEntity<?> obtenerUnPermiso(@RequestParam String url, @RequestParam String nombreRol) throws Exception {
        boolean tieneAcceso= permisoService.verificarAcceso(url, nombreRol);
        return ResponseEntity.status(HttpStatus.OK).body(tieneAcceso);
    }*/

    //Muestra un listado con todos los permisos a los que puede acceder ese rol
    @GetMapping("/listarPermisosParaUnRol")
    public ResponseEntity<?> listaPermisosParaUnRol(Authentication authentication){
        try {
            UserDetails userDetails=(UserDetails) authentication.getPrincipal();
            String nombreRol=userDetails.getAuthorities().toString();
            nombreRol=nombreRol.substring(1,nombreRol.length()-1);
            System.out.println(nombreRol);
            return ResponseEntity.status(HttpStatus.OK).body(permisoService.listaPermisosParaUnRol(nombreRol));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new DTORespuesta(e.getMessage()));
        }
    }

}
