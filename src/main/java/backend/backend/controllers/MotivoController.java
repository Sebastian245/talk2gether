package backend.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import backend.backend.DTO.DTOCrearMotivo;
import backend.backend.DTO.DTORespuesta;
import backend.backend.entities.Motivo;
import backend.backend.services.motivo.MotivoService;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/motivos")
public class MotivoController {

    private final MotivoService motivoService;

    @Autowired
    public MotivoController(MotivoService motivoService) {
        this.motivoService = motivoService;
    }

    //Listar todos los motivos, incluyendo lo que se encuentran con fecha de fin vigencia no nula
    //Este metodo es util para el administrador
    @GetMapping("/listarmotivos")
    public ResponseEntity<?> listarMotivos() {
        try {
            List<Motivo> motivos = motivoService.listarMotivos();
            return ResponseEntity.ok(motivos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DTORespuesta(e.getMessage()));
        }
    }

    //Lista los motivos por lo que un usuario dejo la plataforma o lo sacaron de la plataforma
    @GetMapping("/listarmotivosactivos")
    public ResponseEntity<?> listarMotivosActivos(@RequestParam String nombreTipoMotivo) {
        try {
            return ResponseEntity.ok(motivoService.listarMotivosActivos(nombreTipoMotivo));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DTORespuesta(e.getMessage()));
        }
    }
    //Lista los motivos por lo que un usuario dejo la plataforma o lo sacaron de la plataforma
    @GetMapping("/listarmotivosactivosConID")
    public ResponseEntity<?> listarMotivosActivosConID(@RequestParam String nombreTipoMotivo) {
        try {
            return ResponseEntity.ok(motivoService.listarMotivosActivosConID(nombreTipoMotivo));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DTORespuesta(e.getMessage()));
        }
    }

    //Muestra un motivos en particular, puede estar con fecha de fin vigencia nulo o no
    //Este metodo es util para el administrador
    @GetMapping("/obtenermotivo/{id}")
    public ResponseEntity<?> obtenerMotivoPorId(@PathVariable Long id) {
        try {
            Motivo motivo = motivoService.obtenerMotivoPorId(id);
            return ResponseEntity.ok(motivo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new DTORespuesta(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DTORespuesta(e.getMessage()));
        }
    }

    //Crea un nuevo motivo
    //Este metodo es util para el administrador
    @PostMapping("/crearmotivo")
    public ResponseEntity<?> crearMotivo(@RequestBody DTOCrearMotivo dtoCrearMotivo) {
        try {
            motivoService.crearMotivo(dtoCrearMotivo);
            return ResponseEntity.ok(new DTORespuesta("El motivo se creó correctamente."));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new DTORespuesta(e.getMessage()));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(new DTORespuesta(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DTORespuesta(e.getMessage()));
        }
    }

    //Actualiza un motivo en particular, puede estar con fecha de fin vigencia nulo o no
    //Este metodo es util para el administrador
    @PutMapping("/actualizarmotivo/{id}")
    public ResponseEntity<?> actualizarMotivo(@PathVariable Long id, @RequestBody DTOCrearMotivo dtoActualizarMotivo) {
        try {
            motivoService.actualizarMotivo(id, dtoActualizarMotivo);
            return ResponseEntity.ok(new DTORespuesta("Motivo actualizado correctamente."));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new DTORespuesta(e.getMessage()));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(new DTORespuesta(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DTORespuesta(e.getMessage()));
        }
    }

    //Deshabilita un motivo en particular, el motivo se debe encontrar con fecha fin vigencia nula
    //Este metodo es util para el administrador
    @GetMapping("/deshabilitarmotivo/{id}")
    public ResponseEntity<?> deshabilitarMotivo(@PathVariable Long id) {
        try {
            String mensaje = motivoService.deshabilitarMotivo(id);
            return ResponseEntity.ok(new DTORespuesta(mensaje));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new DTORespuesta(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DTORespuesta(e.getMessage()));
        }
    }

    //Habilita un motivo en particular, el motivo se debe encontrar con fecha fin vigencia no nula
    //Este metodo es util para el administrador
    @GetMapping("/habilitarmotivo/{id}")
    public ResponseEntity<?> habilitarMotivo(@PathVariable Long id) {
        try {
            String mensaje = motivoService.habilitarMotivo(id);
            return ResponseEntity.ok(new DTORespuesta(mensaje));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new DTORespuesta(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DTORespuesta(e.getMessage()));
        }
    }

    //Lista los motivos por lo que un usuario dejo la plataforma o lo sacaron de la plataforma
    @GetMapping("/listarreportecomportamiento")
    public ResponseEntity<?> listarReporteComportamiento() {
        try {
            return ResponseEntity.ok(motivoService.listarReporteComportamiento());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DTORespuesta(e.getMessage()));
        }
    }
    //LISTAR DETALLE MOTIVO PARA UN APRENDIZ, SE NECESITA EL ID DE LA PERSONA REPORTADA Y EL ID DEL MOTIVO POR EL CUAL SE LA REPORTO
    @GetMapping("/detallemotivoreporte")
    public ResponseEntity<?> detalleMotivoRepository(@RequestParam long idCuentaReportada, @RequestParam long idMotivo){
            try{
                return ResponseEntity.status(HttpStatus.OK).body(motivoService.obtenerDetalleReporteMotivo(idCuentaReportada,idMotivo));
            }catch (Exception e){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DTORespuesta(e.getMessage()));
            }
    }

    //LISTAR TIPOS DE MOTIVOS
    @GetMapping("/listartipomotivo")
    public ResponseEntity<?> listarTipoMotivo(){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(motivoService.listarTipoMotivos());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DTORespuesta(e.getMessage()));
        }
    }
    @GetMapping("/filtrarReportePorNombre")
    public ResponseEntity<?> filtrarReportePorUsuario(@RequestParam String nombreUsuario){
            try{
                return ResponseEntity.status(HttpStatus.OK).body(motivoService.filtrarReportePorUsuario(nombreUsuario));
            }catch (Exception e){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DTORespuesta(e.getMessage()));
            }
    }
    @GetMapping("/filtrarReportePorMotivo")
    public ResponseEntity<?> filtrarReportePorMotivo(@RequestParam String idMotivo){
            try{
                return ResponseEntity.status(HttpStatus.OK).body(motivoService.filtrarReportePorMotivo(idMotivo));
            }catch (Exception e){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DTORespuesta(e.getMessage()));
            }
    }
}
