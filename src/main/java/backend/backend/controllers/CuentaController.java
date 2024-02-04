package backend.backend.controllers;

import backend.backend.DTO.*;
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

import backend.backend.services.cuenta.CuentaService;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/cuenta")
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;

    // Método para bloquear a otra cuenta.
    @PostMapping(path = "/bloquear")
    public ResponseEntity<?> bloquearCuenta(@RequestBody DTOBloqueoUsuario dtoBloquearUsuario) {
        try {
            cuentaService.bloquearCuenta(dtoBloquearUsuario);
            return ResponseEntity.ok(new DTORespuesta("Cuenta bloqueada."));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // Método para desbloquear a otra cuenta.
    @PostMapping(path = "/desbloquear")
    public ResponseEntity<?> desbloquearCuenta(@RequestBody DTODesbloqueoUsuario dtoDesbloquearUsuario) {
        try {
            cuentaService.desbloquearCuenta(dtoDesbloquearUsuario);
            return ResponseEntity.ok(new DTORespuesta("Cuenta desbloqueada."));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // Método para verificar si otra cuenta está bloqueada.
    @PostMapping(path = "/verificarbloqueo")
    public ResponseEntity<?> VerificarBloqueo(@RequestBody DTOBloqueoUsuario dtoBloquearUsuario) {
        try {
            return ResponseEntity.ok(cuentaService.verificarBloqueo(dtoBloquearUsuario));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping(path="/usuariosbloqueados")
    public ResponseEntity<?> listaUsuariosBloqueados(@RequestParam Long idCuenta){
        try {
            List<DTOUsuarioBloqueado> listaUsuarioBloqueado=cuentaService.listaUsuariosBloqueados(idCuenta);
            return ResponseEntity.status(HttpStatus.OK).body(listaUsuarioBloqueado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    // Método para seguir a otro usuario aprendiz.
    @PostMapping("/seguirusuario")
    public ResponseEntity<?> seguirUsuario(@RequestBody DTOSeguirUsuario dtoSeguirUsuario) {
        try {
            cuentaService.actualizarUltimaConexion(dtoSeguirUsuario.getIdUsuarioSeguidor());
            return ResponseEntity.ok(new DTORespuesta(cuentaService.seguirUsuario(dtoSeguirUsuario)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // Método para dejar de seguir a otro usuario aprendiz.
    @PostMapping("/dejardeseguirusuario")
    public ResponseEntity<?> dejarDeSeguirUsuario(@RequestBody DTOSeguirUsuario dtoSeguirUsuario) {
        try {
            cuentaService.actualizarUltimaConexion(dtoSeguirUsuario.getIdUsuarioSeguidor());
            return ResponseEntity.ok(new DTORespuesta(cuentaService.dejarDeSeguirUsuario(dtoSeguirUsuario)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // Método para verificar el seguimiento entre ambos usuarios.
    @PostMapping("/verificarseguimiento")
    public ResponseEntity<?> verificarSeguimiento(@RequestBody DTOSeguirUsuario dtoSeguirUsuario) {
        try {
            return ResponseEntity.ok(new DTOVerificacion("¿Sigues a éste usuario?",
                    cuentaService.verificarSeguimiento(dtoSeguirUsuario)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // Método para listar los usuarios seguidos del aprendiz.
    @GetMapping("/listarseguidos")
    public ResponseEntity<?> listarSeguidos(@RequestParam Long idCuenta) {
        try {
            return ResponseEntity.ok(cuentaService.listarSeguidos(idCuenta));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // Método para listar los usuarios que siguen al aprendiz.
    @GetMapping("/listarseguidores")
    public ResponseEntity<?> listarSeguidores(@RequestParam Long idCuenta) {
        try {
            return ResponseEntity.ok(cuentaService.listarSeguidores(idCuenta));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // Método para reportar el comportamiento de otro usuario aprendiz.
    @PostMapping("/reportarusuario")
    public ResponseEntity<?> reportarUsuario(@RequestBody DTOReportarUsuario dtoReportarUsuario) {
        try {
            cuentaService.actualizarUltimaConexion(dtoReportarUsuario.getIdCuentaInformanteMotivo());
            return ResponseEntity.ok(new DTORespuesta(cuentaService.reportarUsuario(dtoReportarUsuario)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DTORespuesta(e.getMessage()));
        }
    }

    @GetMapping("/obteneridiomaaprendiz")
    public ResponseEntity<?> obtenerIdiomaAprendiz(@RequestParam Long idCuenta) {
        try {
            return ResponseEntity.ok(new DTORespuesta(cuentaService.obtenerIdiomaAprendiz(idCuenta)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/obtenerusuariochat")
    public ResponseEntity<?> obtenerUsuarioChat(@RequestParam Long idCuenta) {
        try {
            return ResponseEntity.ok(cuentaService.obtenerUsuarioChat(idCuenta));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/referirusuario")
    public ResponseEntity<?> referirUsuario(@RequestParam Long idCuenta) {
        try {
            cuentaService.actualizarUltimaConexion(idCuenta);
            return ResponseEntity.ok(new DTORespuesta(cuentaService.referirUsuario(idCuenta)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // Método para visualizar las estadísticas de un aprendiz.
    @GetMapping("/visualizarestadisticasaprendiz")
    public ResponseEntity<?> visualizarEstadisticasAprendiz(@RequestParam("idCuenta") Long idCuenta) {
        try {
            return ResponseEntity.ok(cuentaService.visualizarEstadisticasAprendiz(idCuenta));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // Método para puntuar a usuario aprendiz.
    @PostMapping("/puntuarusuario")
    public ResponseEntity<?> puntuarUsuario(@RequestBody DTORespuesta dtoRespuesta, @RequestParam long idCuenta) {
        try {
            cuentaService.actualizarUltimaConexion(idCuenta);
            return ResponseEntity.ok(new DTORespuesta(
                    cuentaService.puntuarUsuario(Integer.parseInt(dtoRespuesta.getMensaje()), idCuenta)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // Método para obtener datos personales de un aprendiz.
    @GetMapping("/obtenerdatospersonales")
    public ResponseEntity<?> obtenerDatosPersonales(@RequestParam("idCuenta") Long idCuenta) {
        try {
            cuentaService.actualizarUltimaConexion(idCuenta);
            return ResponseEntity.ok(cuentaService.obtenerDatosPersonales(idCuenta));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // Método obtener cantidad de seguidos y seguidores de un aprendiz
    @GetMapping("/obtenercantidadSeguidosSeguidores")
    public ResponseEntity<?> obtenercantidadSeguidosSeguidores(@RequestParam("idCuenta") Long idCuenta) {
        try {
            return ResponseEntity.ok(cuentaService.obtenercantidadSeguidosSeguidores(idCuenta));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // Método para actualizar la última conexión del usuario aprendiz.
    @PutMapping(path = "actualizarultimaconexion")
    public ResponseEntity<?> actualizarUltimaConexion(@RequestParam Long idCuenta) {
        try {
            cuentaService.actualizarUltimaConexion(idCuenta);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado: " + e.getMessage());
        }
    }

}
