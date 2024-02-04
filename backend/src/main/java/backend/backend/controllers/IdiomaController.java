package backend.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import backend.backend.DTO.DTOCrearIdioma;
import backend.backend.DTO.DTORespuesta;
import backend.backend.entities.Idioma;
import backend.backend.services.idioma.IdiomaService;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/idiomas")
public class IdiomaController {

    private final IdiomaService idiomaService;

    @Autowired
    public IdiomaController(IdiomaService idiomaService) {
        this.idiomaService = idiomaService;
    }

    //Listar todos los idiomas, incluyendo lo que se encuentran con fecha de fin vigencia no nula
    //Este metodo es util para el administrador
    @GetMapping("/listaridiomas")
    public ResponseEntity<?> listarIdiomas() {
        try {
            List<Idioma> idiomas = idiomaService.listarIdiomas();
            return ResponseEntity.ok(idiomas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DTORespuesta(e.getMessage()));
        }
    }


    //Listar todos los idiomas que se encuentran con fecha de fin vigencia nula
    //Este metodo se utiliza al momento de registrar un usuario
    //NO SE REQUIERE TOKEN
    @GetMapping("/listaridiomasactivos")
    public ResponseEntity<?> listarIdiomasActivos() {
        try {
            return ResponseEntity.ok(idiomaService.listarIdiomasActivos());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DTORespuesta(e.getMessage()));
        }
    }

    //Muestra un idioma en particular, puede estar con fecha de fin vigencia nulo o no
    //Este metodo es util para el administrador
    @GetMapping("/obteneridioma/{id}")
    public ResponseEntity<?> obtenerIdiomaPorId(@PathVariable Long id) {
        try {
            Idioma idioma = idiomaService.obtenerIdiomaPorId(id);
            return ResponseEntity.ok(idioma);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new DTORespuesta(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DTORespuesta(e.getMessage()));
        }
    }

    //Crea un nuevo idioma
    //Este metodo es util para el administrador
    @PostMapping("/crearidioma")
    public ResponseEntity<?> crearPais(@RequestBody DTOCrearIdioma dtoCrearIdioma) {
        try {
            idiomaService.crearIdioma(dtoCrearIdioma);
            return ResponseEntity.ok(new DTORespuesta("El idioma se creó correctamente."));
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
    @PutMapping("/actualizaridioma/{id}")
    public ResponseEntity<?> actualizarIdioma(@PathVariable Long id, @RequestBody DTOCrearIdioma dtoCrearIdioma) {
        try {
            idiomaService.actualizarIdioma(dtoCrearIdioma, id);
            return ResponseEntity.ok(new DTORespuesta("Idioma actualizado correctamente"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new DTORespuesta(e.getMessage()));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new DTORespuesta(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DTORespuesta(e.getMessage()));
        }
    }


    //Deshabilita un idioma en particular, el idioma se debe encontrar con fecha fin vigencia nula
    //Este metodo es util para el administrador
    @GetMapping("/deshabilitaridioma/{id}")
    public ResponseEntity<?> deshabilitarIdioma(@PathVariable Long id) {
        try {
            idiomaService.deshabilitarIdioma(id);
            return ResponseEntity.ok(new DTORespuesta("Idioma deshabilitado correctamente"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new DTORespuesta(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DTORespuesta(e.getMessage()));
        }
    }


    //Habilita un idioma en particular, el idioma se debe encontrar con fecha fin vigencia no nula
    //Este metodo es util para el administrador
    @GetMapping("/habilitaridioma/{id}")
    public ResponseEntity<?> habilitarIdioma(@PathVariable Long id) {
        try {
            idiomaService.habilitarIdioma(id);
            return ResponseEntity.ok(new DTORespuesta("Idioma habilitado correctamente"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new DTORespuesta(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DTORespuesta(e.getMessage()));
        }
    }
}