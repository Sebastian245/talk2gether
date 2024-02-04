package backend.backend.controllers;

import backend.backend.entities.Cuenta;
import backend.backend.entities.Usuario;
import backend.backend.entities.UsuarioLogro;
import backend.backend.repositories.LogroRepository;
import backend.backend.repositories.UsuarioRepository;
import backend.backend.services.cuenta.CuentaServiceImpl;
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

import backend.backend.DTO.DTOCalificarUsuario;
import backend.backend.DTO.DTOCambiarContrasenia;
import backend.backend.DTO.DTOMotivosCuentaEliminada;
import backend.backend.DTO.DTORecuperarContraseniaCorreo;
import backend.backend.DTO.DTORegistrarUsuario;
import backend.backend.DTO.DTORespuesta;
import backend.backend.services.cuenta.CuentaService;
import backend.backend.services.usuario.UsuarioService;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/usuario")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;
    @Autowired
    CuentaService cuentaService;

    @Autowired
    private CuentaServiceImpl userDetailsService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LogroRepository logroRepository;
    @PostMapping("/actualizarUsuario")
    public ResponseEntity<?> actualizarUsuario() {

            List<Usuario> listaUsuarios = usuarioRepository.findAll();

            UsuarioLogro usuarioLogroElComunicador=new UsuarioLogro();
            usuarioLogroElComunicador.setLogro(logroRepository.findById(1l).get());

            UsuarioLogro usuarioLogroAprendizEjemplar=new UsuarioLogro();
            usuarioLogroAprendizEjemplar.setLogro(logroRepository.findById(2l).get());

            UsuarioLogro usuarioLogroMenteMultilingue=new UsuarioLogro();
            usuarioLogroMenteMultilingue.setLogro(logroRepository.findById(3l).get());

            UsuarioLogro usuarioLogroElFilosofo=new UsuarioLogro();
            usuarioLogroElFilosofo.setLogro(logroRepository.findById(4l).get());

            UsuarioLogro usuarioLogroElPopular=new UsuarioLogro();
            usuarioLogroElPopular.setLogro(logroRepository.findById(5l).get());

            UsuarioLogro usuarioLogroElViajero=new UsuarioLogro();
            usuarioLogroElViajero.setLogro(logroRepository.findById(6l).get());

            for (Usuario u: listaUsuarios ) {

                u.getListaUsuarioLogro().add(usuarioLogroElComunicador);
                u.getListaUsuarioLogro().add(usuarioLogroAprendizEjemplar);
                u.getListaUsuarioLogro().add(usuarioLogroMenteMultilingue);
                u.getListaUsuarioLogro().add(usuarioLogroElFilosofo);
                u.getListaUsuarioLogro().add(usuarioLogroElPopular);
                u.getListaUsuarioLogro().add(usuarioLogroElViajero);
                usuarioRepository.save(u);
            }

            return ResponseEntity.status(HttpStatus.OK).body("NADA");

    }






    // Método para registrar a un usuario y enviar correo de verificación de cuenta
    // a su email.
    @PostMapping(path = "/registrarse")
    public ResponseEntity<?> registroUsuario(@RequestBody DTORegistrarUsuario dtoRegistrarUsuario,
            @RequestParam(required = false) String idCuenta) {
        try {
            String token = usuarioService.registroUsuario(dtoRegistrarUsuario, idCuenta);
            return ResponseEntity.ok(token);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new DTORespuesta(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DTORespuesta(e.getMessage()));
        }
    }

    // Método para verificar la cuenta de usuario.
    @GetMapping(path = "confirm")
    public ResponseEntity<String> confirmarTokenVerficacion(@RequestParam("token") String token, @RequestParam(required = false, name="idRefiere") String idRefiere) {
        try {
            String confirmationMessage = usuarioService.confirmarTokenVerficacion(token,idRefiere);
            return ResponseEntity.ok(confirmationMessage);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado: " + e.getMessage());
        }
    }

    // Método para reenviar el mail de verificación de cuenta.
    @PutMapping(path = "reenviarMail")
    public ResponseEntity<String> reenviarMail(@RequestBody DTORecuperarContraseniaCorreo dtoReenviarCorreo) {
        try {
            String reenviarMessage = usuarioService.reenviarMail(dtoReenviarCorreo.getCorreo());
            return ResponseEntity.ok(reenviarMessage);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado: " + e.getMessage());
        }
    }

    // Método para generar un link de recuperación y enviar mail de recuperación de
    // contraseña al usuario.
    @PutMapping(path = "recuperarContrasenia")
    public ResponseEntity<?> recuperarContrasenia(
            @RequestBody DTORecuperarContraseniaCorreo dtoRecuperarContraseniaCorreo) {
        try {
            String recuperarMessage = usuarioService.recuperarContrasenia(dtoRecuperarContraseniaCorreo.getCorreo());
            return ResponseEntity.ok(recuperarMessage);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DTORespuesta("El mail ingresado no existe."));
        }
    }

    // Método realizar el cambio de contraseña como usuario aprendiz.
    @PutMapping(path = "recuperar")
    public ResponseEntity<?> confirmarCambioContrasenia(@RequestParam("correo") String correo,
            @RequestBody(required = false) DTORecuperarContraseniaCorreo dtoRecuperarContraseniaCorreo) {
        try {
            String resultado = usuarioService.confirmarCambioContrasenia(correo,
                    dtoRecuperarContraseniaCorreo.getContrasenia());
            return ResponseEntity.ok(resultado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DTORespuesta("El mail ingresado no existe."));
        }
    }

    // Método para visualizar el perfil de otro usuario aprendiz.
    @GetMapping("/visualizarotroperfil")
    public ResponseEntity<?> visualizarOtroPerfil(@RequestParam("idCuenta") Long idCuenta) {
        try {
            return ResponseEntity.ok(usuarioService.visualizarOtroPerfil(idCuenta));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }



    // Método para cambiar la contraseña de la cuenta (Se encuentra en usuario por
    // motivos de dependencias).
    @PutMapping(path = "/cambiarcontrasenia")
    public ResponseEntity<?> cambiarContrasenia(@RequestBody DTOCambiarContrasenia dtoCambiarContrasenia) {
        try {
            cuentaService.actualizarUltimaConexion(dtoCambiarContrasenia.getId());
            return ResponseEntity.ok(new DTORespuesta(usuarioService.cambiarContrasenia(dtoCambiarContrasenia)));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new DTORespuesta("Error: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DTORespuesta(e.getMessage()));
        }
    }

    // Método para calificar a un usuario aprendiz al finalizar una videollamada
    @PutMapping(path = "/calificarusuario")
    public ResponseEntity<?> calificarUsuario(@RequestBody DTOCalificarUsuario dtoCalificarUsuario) {
        try {
            cuentaService.actualizarUltimaConexion(dtoCalificarUsuario.getIdCuentaCalificador());
            cuentaService.actualizarUltimaConexion(dtoCalificarUsuario.getIdCuentaCalificador());
            return ResponseEntity.ok(new DTORespuesta(usuarioService.calificarUsuario(dtoCalificarUsuario)));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado: " + e.getMessage());
        }
    }

    // Método dar de alta usuarios desde la cuenta administrador.
    @PutMapping(path = "/modificarUsuario")
    public ResponseEntity<?> modificarUsuarioDesdeCuentaAprendiz(@RequestParam Long idCuenta,
            @RequestBody DTORegistrarUsuario dtoModificarDatosUsuario) {
        try {
            cuentaService.actualizarUltimaConexion(idCuenta);
            return ResponseEntity.ok(new DTORespuesta(
                    usuarioService.modificarUsuarioDesdeCuentaAprendiz(idCuenta, dtoModificarDatosUsuario)));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // Método para que el usuario aprendiz pueda eliminar su propia cuenta.
    @PutMapping(path = "/eliminarcuenta")
    public ResponseEntity<?> eliminarCuenta(@RequestBody DTOMotivosCuentaEliminada dtoMotivosCuentaEliminada) {
        try {
            cuentaService.actualizarUltimaConexion(dtoMotivosCuentaEliminada.getIdCuenta());
            return ResponseEntity
                    .ok(new DTORespuesta(usuarioService.eliminarCuenta(dtoMotivosCuentaEliminada)));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    // Método para visualizar el perfil de otro usuario aprendiz.
    @GetMapping("/buscarUsuario")
    public ResponseEntity<?> buscarUsuario(@RequestParam("parametroDeBusqueda") String parametroDeBusqueda, Principal principal) {
        try {
            Cuenta cuenta = (Cuenta) this.userDetailsService.loadUserByUsername(principal.getName());
            cuentaService.actualizarUltimaConexion(cuenta.getId());
            return ResponseEntity.ok(usuarioService.buscarUsuario(parametroDeBusqueda, cuenta.getId()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
