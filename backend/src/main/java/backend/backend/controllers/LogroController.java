package backend.backend.controllers;

import backend.backend.DTO.DTOCrearLogro;
import backend.backend.entities.Logro;
import backend.backend.services.logro.LogroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/logros")
public class LogroController {

    private final LogroService logroService;

    @Autowired
    public LogroController(LogroService logroService) {
        this.logroService = logroService;
    }

    @GetMapping("/activos")
    public ResponseEntity<List<Logro>> listarLogrosActivos() {
        List<Logro> logrosActivos = logroService.listarLogrosActivos();
        return new ResponseEntity<>(logrosActivos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Logro> obtenerLogroPorId(@PathVariable Long id) {
        Logro logro = logroService.obtenerLogroPorId(id);
        return new ResponseEntity<>(logro, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Logro> crearLogro(@RequestBody DTOCrearLogro dtoCrearLogro) throws Exception {
        Logro nuevoLogro = logroService.crearLogro(dtoCrearLogro);
        return new ResponseEntity<>(nuevoLogro, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Logro> actualizarLogro(@PathVariable Long id, @RequestBody DTOCrearLogro dtoActualizarLogro) throws Exception {
        Logro logroActualizado = logroService.actualizarLogro(id, dtoActualizarLogro);
        return new ResponseEntity<>(logroActualizado, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deshabilitarLogro(@PathVariable Long id) throws Exception {
        String mensaje = logroService.deshabilitarLogro(id);
        return new ResponseEntity<>(mensaje, HttpStatus.OK);
    }

    @PostMapping("/{id}/habilitar")
    public ResponseEntity<String> habilitarLogro(@PathVariable Long id) throws Exception {
        String mensaje = logroService.habilitarLogro(id);
        return new ResponseEntity<>(mensaje, HttpStatus.OK);
    }
}
