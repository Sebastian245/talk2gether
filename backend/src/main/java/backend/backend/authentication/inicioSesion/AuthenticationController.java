package backend.backend.authentication.inicioSesion;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import backend.backend.DTO.DTOCuentaEliminadaMotivo;
import backend.backend.DTO.DTOVerificacion;
import backend.backend.entities.Cuenta;
import backend.backend.entities.CuentaEliminadaMotivo;
import backend.backend.repositories.CuentaRepository;
import backend.backend.services.cuenta.CuentaServiceImpl;
import backend.backend.utils.validation.EmailValidator;

@RestController
@CrossOrigin("*")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CuentaServiceImpl userDetailsService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private EmailValidator emailValidator;

    @Autowired
    private CuentaRepository cuentaRepository;

    @PostMapping("/iniciarsesion")
    public ResponseEntity<?> generarToken(@RequestBody JwtRequest jwtRequest) {
        try {
            emailValidator.validarEmail(jwtRequest.getCorreo());
            autenticar(jwtRequest.getCorreo(), jwtRequest.getContrasenia());
        } catch (DisabledException exception) {
            return ResponseEntity.badRequest().body(new DTOVerificacion("¿Has verificado tu cuenta?",false,2));
        } catch (LockedException exception) {
            Optional<Cuenta> cuenta = cuentaRepository.findByCorreo(jwtRequest.getCorreo());
            List<String> listaMotivosAgregar = new ArrayList<>();
            for (CuentaEliminadaMotivo cuentaEliminadaMotivo : cuenta.get().getCuentaEliminada().getListaCuentaEliminadaMotivo()) {
                listaMotivosAgregar.add(cuentaEliminadaMotivo.getMotivo().getNombreMotivo());
            }
             DTOCuentaEliminadaMotivo dtoCuentaEliminadaMotivo = new DTOCuentaEliminadaMotivo(true, listaMotivosAgregar,3);
            return ResponseEntity.badRequest().body(dtoCuentaEliminadaMotivo);
        } catch (BadCredentialsException exception) {
            return ResponseEntity.badRequest().body(new DTOVerificacion("¿El correo ingresado o la contraseña son válidos?",false,1));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(new DTOVerificacion("¿Correo validado correctamente?: " + e.getMessage(),false));
        }

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(jwtRequest.getCorreo());
        String token = this.jwtUtils.generateToken(userDetails);
        // Se actualiza la última conexión cada vez que se inicia sesión en la
        // plataforma
        Optional<Cuenta> cuenta = cuentaRepository.findByCorreo(jwtRequest.getCorreo());
        cuenta.get().setUltimaConexion(new Date());
        cuentaRepository.save(cuenta.get());
        // Devuelve token si inició sesión correctamente.
        return ResponseEntity.ok(new JwtResponse(token,cuenta.get().getRol().getNombreRol()));
    }

    private void autenticar(String username, String password)
            throws DisabledException, LockedException, BadCredentialsException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    @GetMapping("/actual-usuario")
    public Cuenta obtenerUsuarioActual(Principal principal) {
        return (Cuenta) this.userDetailsService.loadUserByUsername(principal.getName());
    }
}
