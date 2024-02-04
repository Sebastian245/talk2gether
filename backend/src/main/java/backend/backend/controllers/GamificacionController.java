package backend.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import backend.backend.services.gamificacion.GamificacionService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/gamificacion")
public class GamificacionController {
   

    @Autowired
    private GamificacionService gamificacionService;
    
    // Método para mostrar la tabla de ranking de usuarios por puntuación.
    @GetMapping("/tablaranking")
    public ResponseEntity<?> tablaRanking(@RequestParam Long idCuenta,@RequestParam int cantidadFilas) {
        try {
            return ResponseEntity.ok(gamificacionService.tablaRanking(idCuenta,cantidadFilas));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // Método para obtener los logros de un usuario aprendiz tanto para el mismo como para otro perfil
    @GetMapping("/obtenerlogros")
    public ResponseEntity<?> obtenerLogros(@RequestParam Long idCuenta) {
        try {
            return ResponseEntity.ok(gamificacionService.obtenerLogros(idCuenta));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
