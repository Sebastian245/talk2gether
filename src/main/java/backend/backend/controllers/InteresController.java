package backend.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import backend.backend.DTO.DTOCrearInteres;
import backend.backend.DTO.DTORespuesta;
import backend.backend.entities.Interes;
import backend.backend.services.interes.InteresService;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/intereses")
public class InteresController {

    private final InteresService interesService;

    @Autowired
    public InteresController(InteresService interesService) {
        this.interesService = interesService;
    }

    //Listar todos los intereses, incluyendo lo que se encuentran con fecha de fin vigencia no nula
    //Este metodo es util para el administrador
    @GetMapping("/listarintereses")
    public ResponseEntity<?> listarIntereses() {
        try {
            List<Interes> intereses = interesService.listarIntereses();
            return ResponseEntity.ok(intereses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DTORespuesta(e.getMessage()));
        }
    }

    //Listar todos los intereses que se encuentran con fecha de fin vigencia nula
    //Este metodo se utiliza al momento de registrar un usuario
    //NO SE REQUIERE TOKEN
    @GetMapping("/listarinteresesactivos")
    public ResponseEntity<?> listarInteresesActivos() {
        try {
            return ResponseEntity.ok(interesService.listarInteresesActivos());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DTORespuesta(e.getMessage()));
        }
    }

    //Muestra un interes en particular, puede estar con fecha de fin vigencia nulo o no
    //Este metodo es util para el administrador
    @GetMapping("/obtenerinteres/{id}")
    public ResponseEntity<?> obtenerInteresPorId(@PathVariable Long id) {
        try {
            Interes interes = interesService.obtenerInteresPorId(id);
            return ResponseEntity.ok(interes);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DTORespuesta(e.getMessage()));
        }
    }

    //Crea un nuevo interes
    //Este metodo es util para el administrador
    @PostMapping("/crearinteres")
    public ResponseEntity<?> crearInteres(@RequestBody DTOCrearInteres dtoCrearInteres) {
        try {
            interesService.crearInteres(dtoCrearInteres);
            return ResponseEntity.ok(new DTORespuesta("El interés se creó correctamente."));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new DTORespuesta(e.getMessage()));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new DTORespuesta(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DTORespuesta(e.getMessage()));
        }
    }

    //Actualiza un idioma en particular, puede estar con fecha de fin vigencia nulo o no
    //Este metodo es util para el administrador
    @PutMapping("/actualizarinteres/{id}")
    public ResponseEntity<?> actualizarInteres(@PathVariable Long id, @RequestBody DTOCrearInteres dtoCrearInteres) {
        try {
            interesService.actualizarInteres(id, dtoCrearInteres);
            return ResponseEntity.ok(new DTORespuesta("Interés actualizado correctamente"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new DTORespuesta(e.getMessage()));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(new DTORespuesta(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DTORespuesta(e.getMessage()));
        }
    }

    //Deshabilita un interes en particular, el interes se debe encontrar con fecha fin vigencia nula
    //Este metodo es util para el administrador
    @GetMapping("/deshabilitarinteres/{id}")
    public ResponseEntity<?> deshabilitarInteres(@PathVariable Long id) {
        try {
            interesService.deshabilitarInteres(id);
            return ResponseEntity.ok(new DTORespuesta("Interés deshabilitado correctamente"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new DTORespuesta(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    //Habilita un interes en particular, el interes se debe encontrar con fecha fin vigencia no nula
    //Este metodo es util para el administrador
    @GetMapping("/habilitarinteres/{id}")
    public ResponseEntity<?> habilitarInteres(@PathVariable Long id) {
        try {
            interesService.habilitarInteres(id);
            return ResponseEntity.ok(new DTORespuesta("Interés habilitado correctamente"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new DTORespuesta(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
