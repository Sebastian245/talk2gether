package backend.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import backend.backend.DTO.DTOCrearPais;
import backend.backend.DTO.DTORespuesta;
import backend.backend.entities.Pais;
import backend.backend.services.pais.PaisService;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/paises")
public class PaisController {

    private final PaisService paisService;

    @Autowired
    public PaisController(PaisService paisService) {
        this.paisService = paisService;
    }

    //Listar todos los paises, incluyendo lo que se encuentran con fecha de fin vigencia no nula
    //Este metodo es util para el administrador
    @GetMapping("/listarpaises")
    public ResponseEntity<?> listarPaises() {
        try {
            List<Pais> paises = paisService.listarPaises();
            return ResponseEntity.ok(paises);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DTORespuesta(e.getMessage()));
        }
    }

    //Listar todos los paises que se encuentran con fecha de fin vigencia nula
    //Este metodo se utiliza al momento de registrar un usuario
    //NO SE REQUIERE TOKEN
    @GetMapping("/listarpaisesactivos")
    public ResponseEntity<?> listarPaisesActivos() {
        try {
            return ResponseEntity.ok(paisService.listarPaisesActivos());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DTORespuesta(e.getMessage()));
        }
    }

    //Muestra un pais en particular, puede estar con fecha de fin vigencia nulo o no
    //Este metodo es util para el administrador
    @GetMapping("/obtenerpais/{id}")
    public ResponseEntity<?> obtenerPaisPorId(@PathVariable Long id) {
        try {
            Pais pais = paisService.obtenerPaisPorId(id);
            return ResponseEntity.ok(pais);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new DTORespuesta(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DTORespuesta(e.getMessage()));
        }
    }

    //Crea un nuevo pais
    //Este metodo es util para el administrador
    @PostMapping("/crearpais")
    public ResponseEntity<?> crearPais(@RequestBody DTOCrearPais dtoCrearPais) {
        try {
            paisService.crearPais(dtoCrearPais);
            return ResponseEntity.ok(new DTORespuesta("El país se creó correctamente."));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new DTORespuesta(e.getMessage()));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(new DTORespuesta(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DTORespuesta(e.getMessage()));
        }
    }

    //Actualiza un pais en particular, puede estar con fecha de fin vigencia nulo o no
    //Este metodo es util para el administrador
    @PutMapping("/actualizarpais/{id}")
    public ResponseEntity<?> actualizarPais(@PathVariable Long id, @RequestBody DTOCrearPais dtoActualizarPais) {
        try {
            paisService.actualizarPais(dtoActualizarPais, id);
            return ResponseEntity.ok(new DTORespuesta("País actualizado correctamente"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new DTORespuesta(e.getMessage()));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(new DTORespuesta(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DTORespuesta(e.getMessage()));
        }
    }

    //Deshabilita un pais en particular, el pais se debe encontrar con fecha fin vigencia nula
    //Este metodo es util para el administrador
    @GetMapping("/deshabilitarpais/{id}")
    public ResponseEntity<?> deshabilitarPais(@PathVariable Long id) {
        try {
            paisService.deshabilitarPais(id);
            return ResponseEntity.ok(new DTORespuesta("País deshabilitado correctamente"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new DTORespuesta(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DTORespuesta(e.getMessage()));
        }
    }

    //Habilita un pais en particular, el pais se debe encontrar con fecha fin vigencia no nula
    //Este metodo es util para el administrador
    @GetMapping("/habilitarpais/{id}")
    public ResponseEntity<?> habilitarPais(@PathVariable Long id) {
        try {
            paisService.habilitarPais(id);
            return ResponseEntity.ok(new DTORespuesta("País habilitado correctamente"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new DTORespuesta(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DTORespuesta(e.getMessage()));
        }
    }
}