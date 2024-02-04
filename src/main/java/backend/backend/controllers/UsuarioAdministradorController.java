package backend.backend.controllers;

import java.security.Principal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import backend.backend.DTO.DTOAdministrarUsuario_Administrador;
import backend.backend.DTO.DTOModificarUsuario_Administrador;
import backend.backend.DTO.DTOMotivosCuentaEliminada;
import backend.backend.DTO.DTORespuesta;
import backend.backend.entities.Cuenta;
import backend.backend.services.administrador.UsuarioAdministradorService;
import backend.backend.services.cuenta.CuentaService;
import backend.backend.services.cuenta.CuentaServiceImpl;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/administrador")
public class UsuarioAdministradorController {
    
    @Autowired
    CuentaService cuentaService;
    @Autowired
    private CuentaServiceImpl userDetailsService;
    @Autowired
    private UsuarioAdministradorService usuarioAdministradorService;

    // Método para listar a los usuarios desde la cuenta administrador.
    @GetMapping(path = "/listarUsuarios")
    public ResponseEntity<?> listarUsuariosDesdeCuentaAdministrador() {
        try {
            return ResponseEntity.ok(usuarioAdministradorService.listarUsuariosDesdeCuentaAdministrador());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    
    // Método dar de alta usuarios desde la cuenta administrador.
    @PostMapping(path = "/crearUsuario")
    public ResponseEntity<?> crearUsuarioDesdeCuentaAdministrador(@RequestBody DTOAdministrarUsuario_Administrador dtoCrearUsuario_Administrador) {
        try {
            return ResponseEntity.ok(new DTORespuesta(usuarioAdministradorService.crearUsuarioDesdeCuentaAdministrador(dtoCrearUsuario_Administrador)));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    
    // Método para que el administrador pueda eliminar una cuenta.
    @PutMapping(path = "/eliminarCuentaUsuario")
    public ResponseEntity<?> eliminarCuentaUsuario(@RequestBody DTOMotivosCuentaEliminada dtoMotivosCuentaEliminada) {
        try {
            return ResponseEntity.ok(new DTORespuesta(usuarioAdministradorService.eliminarCuentaUsuario(dtoMotivosCuentaEliminada)));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    // Método para listar a los usuarios desde la cuenta administrador.
    @GetMapping(path = "/filtrarusuarios")
    public ResponseEntity<?> filtrarUsuarios(@RequestParam String filtroParametro) {
        try {
            return ResponseEntity.ok(usuarioAdministradorService.filtrarUsuarios(filtroParametro));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @PutMapping(path = "/modificarUsuario")
    public ResponseEntity<?> modificarUsuarioDesdeCuentaAdministrador(@RequestParam Long idCuenta,@RequestBody DTOModificarUsuario_Administrador dtoModificarUsuario_administrador ) {
        try {
            return ResponseEntity.ok(new DTORespuesta(usuarioAdministradorService.modificarUsuarioDesdeCuentaAdministrador(idCuenta,dtoModificarUsuario_administrador)));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DTORespuesta(e.getMessage()));
        }
    }
    // Método para mostrar informacion del usuario antes de ser eliminado.
    @GetMapping(path = "/datoseliminarusuario")
    public ResponseEntity<?> datoseliminarusuario(@RequestParam long idCuenta) {
        try {
            return ResponseEntity.ok(usuarioAdministradorService.datoseliminarusuario(idCuenta));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new DTORespuesta( e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    // Método para mostrar las estadísticas del usuario administrador.
    @GetMapping(path = "/obtenerestadisticas")
    public ResponseEntity<?> obtenerEstadisticasAdministrador(Principal principal) {
        try {
            Cuenta cuenta = (Cuenta) this.userDetailsService.loadUserByUsername(principal.getName());
            cuentaService.actualizarUltimaConexion(cuenta.getId());
            return ResponseEntity.ok(usuarioAdministradorService.obtenerEstadisticasAdministrador());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new DTORespuesta( e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    // Método para mostrar las estadísticas del usuario administrador GRANULADAS.
    @GetMapping(path = "/obtenerestadisticasGranuladas")
    public ResponseEntity<?> obtenerEstadisticasAdministradorGranuladas(@RequestParam String fechaDesde,@RequestParam String fechaHasta) {
        try {
            return ResponseEntity.ok(usuarioAdministradorService.obtenerEstadisticasAdministradorGranuladas(fechaDesde,fechaHasta));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new DTORespuesta( e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    // Método para mostrar las estadísticas del usuario administrador.
    @GetMapping(path = "/obtenercalificaciones")
    public ResponseEntity<?> obtenerCalificaciones(@RequestParam long idCuenta) {
        try {
            return ResponseEntity.ok(usuarioAdministradorService.obtenerCalificaciones(idCuenta));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new DTORespuesta( e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    // Método para mostrar las estadísticas del usuario administrador.
    @GetMapping(path = "/filtrarcalificaciones")
    public ResponseEntity<?> filtrarCalificaciones(@RequestParam long idCuenta,@RequestParam String fechaDesde,@RequestParam String fechaHasta) {
        try {
            return ResponseEntity.ok(usuarioAdministradorService.filtrarCalificaciones(idCuenta,fechaDesde,fechaHasta));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new DTORespuesta( e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
