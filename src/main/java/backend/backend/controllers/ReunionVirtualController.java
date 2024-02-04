package backend.backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import backend.backend.DTO.DTORespuesta;
import backend.backend.services.cuenta.CuentaService;
import backend.backend.services.reunionvirtual.ReunionVirtualService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/reunionvirtual")
public class ReunionVirtualController {

    @Autowired
    private ReunionVirtualService reunionVirtualService;
    @Autowired 
    private CuentaService cuentaService;

    // Método encargado de generar una reunión virtual y devolver el link de la misma.
    @PostMapping("/crearsala")
    public ResponseEntity<?> crearSala(@RequestParam Long idCuenta) {
        try {
            cuentaService.actualizarUltimaConexion(idCuenta);
            // Se realiza la llamada al servicio de reunión virtual para retornar el link de la sala.
            return ResponseEntity.ok(reunionVirtualService.crearSala(idCuenta));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new DTORespuesta(e.getMessage()));
        }
    }

    @PutMapping("/unirseasala")
    public ResponseEntity<?> unirseASala(@RequestParam String linkReunion, @RequestParam Long idSegundoParticipante) {
        try {
            cuentaService.actualizarUltimaConexion(idSegundoParticipante);
            return ResponseEntity.ok(new DTORespuesta(reunionVirtualService.unirseASala(linkReunion, idSegundoParticipante)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new DTORespuesta(e.getMessage()));
        }
    }

    @PutMapping("/finalizarvideollamada")
    public ResponseEntity<?> finalizarVideollamada(@RequestParam String linkReunion) {
        try {
            return ResponseEntity.ok(new DTORespuesta(reunionVirtualService.finalizarVideollamada(linkReunion)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new DTORespuesta(e.getMessage()));
        }
    }

    @PutMapping("/finalizarvideollamadaPorRefrescar")
    public ResponseEntity<?> finalizarvideollamadaPorRefrescar(@RequestParam long idCuenta) {
        try {
            return ResponseEntity.ok(new DTORespuesta(reunionVirtualService.finalizarvideollamadaPorRefrescar(idCuenta)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new DTORespuesta(e.getMessage()));
        }
    }

    @GetMapping("/listarsalasactivas")
    public ResponseEntity<?> listarSalasActivas(@RequestParam Long idCuenta) {
        try {
            cuentaService.actualizarUltimaConexion(idCuenta);
            return ResponseEntity.ok(reunionVirtualService.listarSalasActivas(idCuenta));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new DTORespuesta(e.getMessage()));
        }
    }

    @GetMapping("/listarsalasactivasfiltradas")
    public ResponseEntity<?> listarSalasActivasFiltradas(
    @RequestParam Long idCuenta,
    @RequestParam String edadMinima,
    @RequestParam String edadMaxima,
    @RequestParam List<String> intereses,
    @RequestParam String nombreNivelIdioma,
    @RequestParam String nombrePais) {
        try {
            cuentaService.actualizarUltimaConexion(idCuenta);
            return ResponseEntity.ok(reunionVirtualService.listarSalasActivasFiltradas(idCuenta,edadMinima,edadMaxima,intereses,nombreNivelIdioma,nombrePais));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new DTORespuesta(e.getMessage()));
        }
    }

    @GetMapping("/obtenerUsuarioACalificar")
    public ResponseEntity<?> obtenerDatosParaCalificar(@RequestParam long idCuenta,@RequestParam String url) throws Exception{
        try {
            cuentaService.actualizarUltimaConexion(idCuenta);
            return ResponseEntity.status(HttpStatus.OK).body(reunionVirtualService.dtoPrevioCalificarUsuario(idCuenta,url));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new DTORespuesta(e.getMessage()));
        }
    }
}
