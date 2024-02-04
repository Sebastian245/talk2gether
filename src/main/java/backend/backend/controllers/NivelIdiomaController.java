package backend.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import backend.backend.DTO.DTOCrearNivelIdioma;
import backend.backend.DTO.DTORespuesta;
import backend.backend.entities.NivelIdioma;
import backend.backend.services.nivelidioma.NivelIdiomaService;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/nivelesidioma")
public class NivelIdiomaController {

    private final NivelIdiomaService nivelIdiomaService;

    @Autowired
    public NivelIdiomaController(NivelIdiomaService nivelIdiomaService) {
        this.nivelIdiomaService = nivelIdiomaService;
    }

    //Listar todos los niveles de idiomas, incluyendo lo que se encuentran con fecha de fin vigencia no nula
    //Este metodo es util para el administrador
    @GetMapping("/listarnivelesidioma")
    public ResponseEntity<?> listarNivelesIdioma() {
        try {
            List<NivelIdioma> nivelIdiomas = nivelIdiomaService.listarNivelesIdioma();
            return ResponseEntity.ok(nivelIdiomas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DTORespuesta(e.getMessage()));
        }
    }

    //Listar todos los niveles de idiomas que se encuentran con fecha de fin vigencia nula
    //Este metodo se utiliza al momento de registrar un usuario
    //NO SE REQUIERE TOKEN
    @GetMapping("/listarnivelesidiomaactivos")
    public ResponseEntity<?> listarNivelesIdiomaActivos() {
        try {
            return ResponseEntity.ok(nivelIdiomaService.listarNivelesIdiomaActivos());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DTORespuesta(e.getMessage()));
        }
    }

    //Muestra un nivel de idioma en particular, puede estar con fecha de fin vigencia nulo o no
    //Este metodo es util para el administrador
    @GetMapping("/obtenernivelidioma/{id}")
    public ResponseEntity<?> obtenerNivelIdiomaPorId(@PathVariable Long id) {
        try {
            NivelIdioma nivel = nivelIdiomaService.obtenerNivelIdiomaPorId(id);
            return ResponseEntity.ok(nivel);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DTORespuesta(e.getMessage()));
        }
    }

    //Crea un nuevo nivel de idioma
    //Este metodo es util para el administrador
    @PostMapping("/crearnivelidioma")
    public ResponseEntity<?> crearNivelIdioma(@RequestBody DTOCrearNivelIdioma dtoCrearNivelIdioma) {
        try {
            nivelIdiomaService.crearNivelIdioma(dtoCrearNivelIdioma);
            return ResponseEntity.ok(new DTORespuesta("El nivel de idioma se creó correctamente."));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new DTORespuesta(e.getMessage()));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(new DTORespuesta(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DTORespuesta(e.getMessage()));
        }
    }

    //Actualiza un nivel de idioma en particular, puede estar con fecha de fin vigencia nulo o no
    //Este metodo es util para el administrador
    @PutMapping("/actualizarnivelidioma/{id}")
    public ResponseEntity<?> actualizarNivelIdioma(@PathVariable Long id, @RequestBody DTOCrearNivelIdioma dtoCrearNivelIdioma) {
        try {
            nivelIdiomaService.actualizarNivelIdioma(dtoCrearNivelIdioma, id);
            return ResponseEntity.ok(new DTORespuesta("Nivel de idioma actualizado correctamente"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new DTORespuesta(e.getMessage()));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(new DTORespuesta(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DTORespuesta(e.getMessage()));
        }
    }

    //Deshabilita un nive de idioma en particular, el nive de idioma se debe encontrar con fecha fin vigencia nula
    //Este metodo es util para el administrador
    @GetMapping("/deshabilitarnivelidioma/{id}")
    public ResponseEntity<?> deshabilitarNivelIdioma(@PathVariable Long id) {
        try {
            nivelIdiomaService.deshabilitarNivelIdioma(id);
            return ResponseEntity.ok(new DTORespuesta("Nivel de idioma deshabilitado correctamente"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new DTORespuesta(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DTORespuesta(e.getMessage()));
        }
    }

    //Habilita un nive de idioma en particular, el nive de idioma se debe encontrar con fecha fin vigencia no nula
    //Este metodo es util para el administrador
    @GetMapping("/habilitarnivelidioma/{id}")
    public ResponseEntity<?> habilitarNivelIdioma(@PathVariable Long id) {
        try {
            nivelIdiomaService.habilitarNivelIdioma(id);
            return ResponseEntity.ok(new DTORespuesta("Nivel de idioma habilitado correctamente"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new DTORespuesta(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DTORespuesta(e.getMessage()));
        }
    }
}
