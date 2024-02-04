package backend.backend.controllers;

import java.util.List;

import backend.backend.DTO.DTORespuesta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.backend.DTO.DTOCrearPuntosPorActividad;
import backend.backend.entities.PuntosPorActividad;
import backend.backend.services.puntosporactividad.PuntosPorActividadService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/puntosporactividad")
public class PuntosPorActividadController {

    private final PuntosPorActividadService puntosPorActividadService;

    @Autowired
    public PuntosPorActividadController(PuntosPorActividadService puntosPorActividadService) {
        this.puntosPorActividadService = puntosPorActividadService;
    }

    //Listar todos los puntos por actividad, incluyendo lo que se encuentran con fecha de fin vigencia no nula
    //Este metodo es util para el administrador
    @GetMapping("/listarpuntosporactividad")
    public ResponseEntity<?> listarPuntosPorActividad() {
        try {
            List<PuntosPorActividad> puntosPorActividad = puntosPorActividadService.listarPuntosPorActividad();
            return ResponseEntity.ok(puntosPorActividad);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DTORespuesta(e.getMessage()));
        }
    }

    //Listar todos los puntos por actividad que se encuentran con fecha de fin vigencia nula
    //Este metodo se utiliza al momento de registrar un usuario
    @GetMapping("/listarpuntosporactividadactivos")
    public ResponseEntity<?> listarPuntosPorActividadActivos() {
        try {
            List<PuntosPorActividad> puntosPorActividad = puntosPorActividadService.listarPuntosPorActividadActivos();
            return ResponseEntity.ok(puntosPorActividad);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DTORespuesta(e.getMessage()));
        }
    }

    //Muestra un punto por actividad en particular, puede estar con fecha de fin vigencia nulo o no
    //Este metodo es util para el administrador
    @GetMapping("/obtenerpuntosporactividad/{id}")
    public ResponseEntity<?> obtenerPuntosPorActividadPorId(@PathVariable Long id) {
        try {
            PuntosPorActividad puntosPorActividad = puntosPorActividadService.obtenerPuntosPorActividadPorId(id);
            return ResponseEntity.ok(puntosPorActividad);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new DTORespuesta(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DTORespuesta(e.getMessage()));
        }
    }

    //Crea un nuevo punto por actividad
    //Este metodo es util para el administrador
   /*  @PostMapping("/crearpuntosporactividad")
    public ResponseEntity<?> crearPuntosPorActividad(@RequestBody DTOCrearPuntosPorActividad dtoCrearPuntosPorActividad) {
        try {
            puntosPorActividadService.crearPuntosPorActividad(dtoCrearPuntosPorActividad);
            return ResponseEntity.ok("Los puntos por actividad se crearon correctamente.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new DTORespuesta(e.getMessage()));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new DTORespuesta(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DTORespuesta(e.getMessage()));
        }
    } */

    //Actualiza un punto por actividad en particular, puede estar con fecha de fin vigencia nulo o no
    //Este metodo es util para el administrador
    @PutMapping("/actualizarpuntosporactividad/{id}")
    public ResponseEntity<?> actualizarPuntosPorActividad(@PathVariable Long id, @RequestBody DTOCrearPuntosPorActividad dtoActualizarPuntosPorActividad) {
        try {
            puntosPorActividadService.actualizarPuntosPorActividad(dtoActualizarPuntosPorActividad, id);
            return ResponseEntity.ok(new DTORespuesta("Los puntos por actividad se actualizaron correctamente"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new DTORespuesta(e.getMessage()));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(new DTORespuesta(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DTORespuesta(e.getMessage()));
        }
    }

    //Deshabilita un punto por actividad en particular, el punto por actividad se debe encontrar con fecha fin vigencia nula
    //Este metodo es util para el administrador
   /*  @PutMapping("/deshabilitarpuntosporactividad/{id}")
    public ResponseEntity<?> deshabilitarPuntosPorActividad(@PathVariable Long id) {
        try {
            puntosPorActividadService.deshabilitarPuntosPorActividad(id);
            return ResponseEntity.ok("Puntos por actividad deshabilitados correctamente");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new DTORespuesta(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DTORespuesta(e.getMessage()));
        }
    }

    //Habilita un punto por actividad en particular, el punto por actividad se debe encontrar con fecha fin vigencia no nula
    //Este metodo es util para el administrador
    @PutMapping("/habilitarpuntosporactividad/{id}")
    public ResponseEntity<?> habilitarPuntosPorActividad(@PathVariable Long id) {
        try {
            puntosPorActividadService.habilitarPuntosPorActividad(id);
            return ResponseEntity.ok("Puntos por actividad habilitados correctamente");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new DTORespuesta(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DTORespuesta(e.getMessage()));
        }
    } */
}