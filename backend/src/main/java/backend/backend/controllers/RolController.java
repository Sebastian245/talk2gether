package backend.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import backend.backend.DTO.DTOCrearRol;
import backend.backend.DTO.DTORespuesta;
import backend.backend.entities.Rol;
import backend.backend.services.rol.RolService;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/roles")
public class RolController {

    private final RolService rolService;

    @Autowired
    public RolController(RolService rolService) {
        this.rolService = rolService;
    }

    //Listar todos los roles, incluyendo lo que se encuentran con fecha de fin vigencia no nula
    //Este metodo es util para el administrador
    @GetMapping("/listarroles")
    public ResponseEntity<?> listarRoles() {
        try {
            List<Rol> roles = rolService.listarRoles();
            return ResponseEntity.ok(roles);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DTORespuesta(e.getMessage()));
        }
    }

    //Listar todos los roles que se encuentran con fecha de fin vigencia nula
    //Este metodo se utiliza al momento de registrar un usuario
    @GetMapping("/listarrolesactivos")
    public ResponseEntity<?> listarRolesActivos() {
        try {
            List<Rol> roles = rolService.listarRolesActivos();
            return ResponseEntity.ok(roles);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DTORespuesta(e.getMessage()));
        }
    }

    //Muestra un rol en particular, puede estar con fecha de fin vigencia nulo o no
    //Este metodo es util para el administrador
    @GetMapping("/obtenerrol/{id}")
    public ResponseEntity<?> obtenerRolPorId(@PathVariable Long id) {
        try {
            Rol rol = rolService.obtenerRolPorId(id);
            return ResponseEntity.ok(rol);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new DTORespuesta(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DTORespuesta(e.getMessage()));
        }
    }

    //Crea un nuevo rol
    //Este metodo es util para el administrador
    @PostMapping("/crearrol")
    public ResponseEntity<?> crearRol(@RequestBody DTOCrearRol dtoCrearRol) {
        try {
            rolService.crearRol(dtoCrearRol);
            return ResponseEntity.ok(new DTORespuesta("El rol se creó correctamente."));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new DTORespuesta(e.getMessage()));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new DTORespuesta(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DTORespuesta(e.getMessage()));
        }
    }

    //Actualiza un rol en particular, puede estar con fecha de fin vigencia nulo o no
    //Este metodo es util para el administrador
    @PutMapping("/actualizarrol/{id}")
    public ResponseEntity<?> actualizarRol(@PathVariable Long id, @RequestBody DTOCrearRol dtoCrearRol) {
        try {
            rolService.actualizarRol(dtoCrearRol, id);
            return ResponseEntity.ok(new DTORespuesta("Rol actualizado correctamente"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new DTORespuesta(e.getMessage()));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new DTORespuesta(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DTORespuesta(e.getMessage()));
        }
    }

    //Deshabilita un rol en particular, el rol se debe encontrar con fecha fin vigencia nula
    //Este metodo es util para el administrador
    @GetMapping("/deshabilitarrol/{id}")
    public ResponseEntity<?> deshabilitarRol(@PathVariable Long id) {
        try {
            rolService.deshabilitarRol(id);
            return ResponseEntity.ok(new DTORespuesta("Rol deshabilitado correctamente"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new DTORespuesta(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DTORespuesta(e.getMessage()));
        }
    }

    //Habilita un rol en particular, el rol se debe encontrar con fecha fin vigencia no nula
    //Este metodo es util para el administrador
    @GetMapping("/habilitarrol/{id}")
    public ResponseEntity<?> habilitarRol(@PathVariable Long id) {
        try {
            rolService.habilitarRol(id);
            return ResponseEntity.ok(new DTORespuesta("Rol habilitado correctamente"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new DTORespuesta(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DTORespuesta(e.getMessage()));
        }
    }
}
