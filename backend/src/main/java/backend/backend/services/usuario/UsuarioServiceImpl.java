package backend.backend.services.usuario;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

import javax.transaction.Transactional;

import backend.backend.entities.*;
import backend.backend.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import backend.backend.DTO.DTOCalificarUsuario;
import backend.backend.DTO.DTOCambiarContrasenia;
import backend.backend.DTO.DTOInteres;
import backend.backend.DTO.DTOListarReunionesVirtuales;
import backend.backend.DTO.DTOMotivosCuentaEliminada;
import backend.backend.DTO.DTOPerfilOtroUsuario;
import backend.backend.DTO.DTORegistrarUsuario;
import backend.backend.DTO.DTOReunionVirtual;
import backend.backend.DTO.DTOTablaRanking;
import backend.backend.DTORepository.DTOListarReunionesVirtualesRepository;
import backend.backend.DTORepository.DTOPerfilOtroUsuarioRepository;
import backend.backend.authentication.mail.EmailSender;
import backend.backend.authentication.token.ConfirmationToken;
import backend.backend.authentication.token.ConfirmationTokenRepository;
import backend.backend.authentication.token.ConfirmationTokenService;
import backend.backend.utils.encryptation.EncryptionUtils;
import backend.backend.utils.functions.Functions;
import backend.backend.utils.validation.EmailValidator;
import backend.backend.utils.validation.FechaValidator;
import backend.backend.utils.validation.PasswordValidator;
import backend.backend.utils.validation.TextValidator;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private CuentaRepository cuentaRepository;
    @Autowired
    private EmailValidator emailValidator;
    @Autowired
    private PasswordValidator passwordValidator;
    @Autowired
    private TextValidator textValidator;
    @Autowired
    private FechaValidator fechaValidator;
    @Autowired
    private ConfirmationTokenService confirmationTokenService;
    @Autowired
    private EmailSender emailSender;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private PaisRepository paisRepository;
    @Autowired
    private IdiomaRepository idiomaRepository;
    @Autowired
    private NivelIdiomaRepository nivelIdiomaRepository;
    @Autowired
    private InteresRepository interesRepository;
    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private ReunionVirtualRepository reunionVirtualRepository;
    @Autowired
    private CalificacionRepository calificacionRepository;
    @Autowired
    private UsuarioCalificacionRepository usuarioCalificacionRepository;
    @Autowired
    private MotivoRepository motivoRepository;
    @Autowired
    private ReporteMotivoRepository reporteMotivoRepository;
    @Autowired
    private BloqueosRepository bloqueosRepository;
    @Autowired
    private PuntosPorActividadRepository puntosPorActividadRepository;

    @Autowired
    private LogroRepository logroRepository;
    // Método para registrar a un usuario y enviar correo de verificación de cuenta
    // a su email.
    @Override
    public String registroUsuario(DTORegistrarUsuario dtoRegistrarUsuario, String idCuenta)
            throws IllegalStateException {
        // Verificar si existe en la base de datos el correo ingresado
        boolean correoExistente = cuentaRepository.findByCorreo(dtoRegistrarUsuario.getCorreo()).isPresent();
        if (correoExistente) {
            throw new IllegalStateException("El correo ingresado ya existe");
        }
        // Instanciar objeto Usuario y realizar los 'Setters' correspondientes al mismo.
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(dtoRegistrarUsuario.getNombreUsuario());
        usuario.setApellidoUsuario(dtoRegistrarUsuario.getApellidoUsuario());

        // Se realizar la conversión del formato de la fecha ingresada a 'yyyy-MM-dd'.
        DateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaNacimiento = null;
        try {
            fechaNacimiento = formatoFecha.parse(dtoRegistrarUsuario.getFechaNacimiento());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        usuario.setFechaNacimiento(fechaNacimiento);
        usuario.setDescripcionUsuario(dtoRegistrarUsuario.getDescripcion());

        // Instanciar objeto Cuenta y realizar los 'Setters' correspondientes a la
        // misma.
        Cuenta cuenta = new Cuenta();
        cuenta.setCorreo(dtoRegistrarUsuario.getCorreo());
        // Encriptar contraseña de usuario
        String encodedPassword = bCryptPasswordEncoder
                .encode(dtoRegistrarUsuario.getContrasenia());
        cuenta.setContrasenia(encodedPassword);
        cuenta.setUrlFoto(dtoRegistrarUsuario.getUrlFoto());
        cuenta.setCuentaCreada(new Date());
        cuenta.setCuentaEliminada(null);
        cuenta.setCuentaVerificada(null);
        cuenta.setUltimaConexion(null);
        cuenta.setCantidadReferidos(0);

        // Buscar rol ingresado y verificar si existe.
        Optional<Rol> rol = rolRepository.findByNombreRolAndFechaHoraFinVigenciaRolIsNull("usuario");
        if (!rol.isPresent()) {
            throw new IllegalStateException("El rol ingresado no existe");
        }
        cuenta.setRol(rol.get());
        // Buscar pais ingresado y verificar si existe.
        Optional<Pais> pais = paisRepository
                .findByNombrePaisAndFechaHoraFinVigenciaPaisIsNull(dtoRegistrarUsuario.getNombrePais());
        if (!pais.isPresent()) {
            throw new IllegalStateException("El pais ingresado no existe");
        }
        // Buscar idioma aprendiz ingresado y verificar si existe.
        Optional<Idioma> idiomaAprendiz = idiomaRepository
                .findByNombreIdiomaAndFechaHoraFinVigenciaIdiomaIsNull(dtoRegistrarUsuario.getNombreIdiomaAprendiz());
        if (!idiomaAprendiz.isPresent()) {
            throw new IllegalStateException("El idioma a aprender ingresado no existe");
        }
        // Buscar idioma nativo ingresado y verificar si existe.
        Optional<Idioma> idiomaNativo = idiomaRepository
                .findByNombreIdiomaAndFechaHoraFinVigenciaIdiomaIsNull(dtoRegistrarUsuario.getNombreIdiomaNativo());
        if (!idiomaNativo.isPresent()) {
            throw new IllegalStateException("El idioma natal ingresado no existe");
        }
        // Buscar nivel de idioma nativo ingresado y verificar si existe.
        Optional<NivelIdioma> nivelIdiomaNativo = nivelIdiomaRepository
                .findByNombreNivelIdiomaAndFechaHoraFinVigenciaNivelIdiomaIsNull("Nativo");
        if (!nivelIdiomaNativo.isPresent()) {
            throw new IllegalStateException("El nivel del idioma nativo ingresado no existe");
        }
        // Buscar nivel de idioma aprendiz ingresado y verificar si existe.
        Optional<NivelIdioma> nivelIdiomaAprendiz = nivelIdiomaRepository
                .findByNombreNivelIdiomaAndFechaHoraFinVigenciaNivelIdiomaIsNull(
                        dtoRegistrarUsuario.getNombreNivelIdiomaAprendiz());
        if (!nivelIdiomaAprendiz.isPresent()) {
            throw new IllegalStateException("El nivel del idioma aprendiz ingresado no existe");
        }
        // Asignar el idioma a aprender.
        UsuarioIdioma usuarioIdiomaAprendiz = new UsuarioIdioma();
        usuarioIdiomaAprendiz.setIdioma(idiomaAprendiz.get());
        usuarioIdiomaAprendiz.setNivelIdioma(nivelIdiomaAprendiz.get());
        // Asignar el idioma natal
        UsuarioIdioma usuarioIdiomaNativo = new UsuarioIdioma();
        usuarioIdiomaNativo.setIdioma(idiomaNativo.get());
        usuarioIdiomaNativo.setNivelIdioma(nivelIdiomaNativo.get());
        // Asignar los intereses al usuario
        if (dtoRegistrarUsuario.getNombreIntereses() != null) {
            for (int i = 0; i < dtoRegistrarUsuario.getNombreIntereses().size(); i++) {
                Optional<Interes> interes = interesRepository
                        .findByNombreInteresAndFechaHoraFinVigenciaInteresIsNull(
                                dtoRegistrarUsuario.getNombreIntereses().get(i));
                if (!interes.isPresent()) {
                    throw new IllegalStateException("El interes ingresado no existe");
                }
                UsuarioInteres usuarioInteres = new UsuarioInteres(new Date(), null, interes.get());
                usuario.getListaIntereses().add(usuarioInteres);
            }
        }
        // Asignar puntuacion en 0 al usuario nuevo
        UsuarioPuntuacion usuarioPuntuacion = new UsuarioPuntuacion(0, null, null);
        // Asignar al usuario una cuenta, pais, idiomas
        usuario.setCuenta(cuenta);
        usuario.setPais(pais.get());
        usuario.setIdiomaAprendiz(usuarioIdiomaAprendiz);
        usuario.setIdiomaNativo(usuarioIdiomaNativo);
        usuario.setUsuarioPuntuacion(usuarioPuntuacion);
        // Validar todos los datos ingresados por el usuario
        validaciones(usuario);



        //Crear objetos de usuarioLogro con fecha nulla y  con los logros relacionados
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

        usuario.getListaUsuarioLogro().add(usuarioLogroElComunicador);
        usuario.getListaUsuarioLogro().add(usuarioLogroAprendizEjemplar);
        usuario.getListaUsuarioLogro().add(usuarioLogroMenteMultilingue);
        usuario.getListaUsuarioLogro().add(usuarioLogroElFilosofo);
        usuario.getListaUsuarioLogro().add(usuarioLogroElPopular);
        usuario.getListaUsuarioLogro().add(usuarioLogroElViajero);




        // Guardar usuario
        usuarioRepository.save(usuario);

        // Crear token e instanciar clase ConfirmationToken (Atributo de
        // cuentaVerificada null)
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                usuario.getCuenta());
        confirmationTokenService.saveConfirmationToken(
                confirmationToken);
        // Crear link para verificar cuenta
        String link = null;
        if (idCuenta != null) {
            link = "http://localhost:4200/cuenta-verificada?token=" + token + "&idRefiere=" + idCuenta;
        } else {
            link = "http://localhost:4200/cuenta-verificada?token=" + token;
        }
        // Llamar a método para enviar mail a su correo electrónico
        emailSender.send(
                usuario.getCuenta().getCorreo(),
                generarMailVerificarCuenta(usuario.getNombreUsuario(), link));
        System.out.println(link);
        return null;

    }

    // Método para verificar la cuenta de usuario.
    @Transactional
    public String confirmarTokenVerficacion(String token, String idRefiere) throws IllegalStateException {

        System.out.println(idRefiere);

        // Se realiza la búsqueda del token y se verifica si existe.
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() -> new IllegalStateException("El token no se encuentra"));

        // Se realiza la verificación si la cuenta ya está verificada o no.
        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("El correo ya ha sido verificado");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        // Se realiza la verificación del tiempo de expiración del token.
        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("El tiempo de verificación ha expirado");
        }
        // Se verifica la cuenta realizando el 'Set' del token en la base de datos.
        confirmationTokenService.setConfirmedAt(token);
        verificarUsuario(confirmationToken.getCuenta().getCorreo());

        // Se realizan las verificaciones sobre referidos para aumentarle un referido a
        // la cuenta
        if (idRefiere != null) {
            // Se realiza la desencriptación de el id del usuario enviado por el link.
            String idDesencriptado = EncryptionUtils.decrypt(idRefiere);

            Optional<Long> idUsuarioReferido = cuentaRepository.obtenerIdUsuario(Long.parseLong(idDesencriptado));
            if (idUsuarioReferido.isPresent()) {

                Optional<Usuario> usuarioReferido = usuarioRepository.findById(idUsuarioReferido.get());
                usuarioReferido.get().getCuenta()
                        .setCantidadReferidos(usuarioReferido.get().getCuenta().getCantidadReferidos() + 1);

                int puntosTotales = usuarioReferido.get().getUsuarioPuntuacion().getPuntosTotales();

                // SUMAR PUNTOS A PERSONA QUE REFIERE
                Optional<PuntosPorActividad> puntosPorActividad = puntosPorActividadRepository.findById(2l);
                // AÑADIR RELACION CON PUNTOSPORACTIVIDAD
                Puntuacion puntuacion = new Puntuacion(puntosPorActividad.get().getPuntosPorActividad(), new Date());
                // puntuacion.setPuntosPorActividad(puntosPorActividad);

                usuarioReferido.get().getUsuarioPuntuacion()
                        .setPuntosTotales(puntosTotales + puntosPorActividad.get().getPuntosPorActividad());
                usuarioReferido.get().getUsuarioPuntuacion().getListaPuntuacion().add(puntuacion);
                usuarioRepository.save(usuarioReferido.get());
            } else {
                System.out.println("La cuenta que refiere no existe");
            }
        }

        return null;
    }

    // Método para realizar la modificación en base de datos del atributo de
    // cuentaVerificada
    public boolean verificarUsuario(String correo) {
        int registrosActualizados = cuentaRepository.verificarCuentaPorEmail(correo, new Date());
        return registrosActualizados > 0;
    }

    // Método para reenviar el mail de verificación de cuenta.
    public String reenviarMail(String correo) {

        // Se realiza la búsqueda del usuario y del token ya enviado para modificarlo
        Optional<Cuenta> cuenta = cuentaRepository.findByCorreo(correo);
        if (!cuenta.isPresent()) {
            throw new IllegalStateException("El correo no existe.");
        }
        Cuenta cuentaReenviar = cuenta.get();

        // Verificar si la cuenta ya está verificada
        if (cuentaReenviar.getCuentaVerificada() != null) {
            throw new IllegalStateException("El correo ya ha sido verificado");
        }

        // Buscar el token ya existente para poder modificarlo.
        Optional<ConfirmationToken> confirmationToken = confirmationTokenRepository
                .findByIdCuenta(cuentaReenviar.getId());
        ConfirmationToken confirmationTokenModificar = confirmationToken.get();

        // Se realiza un 'Set' de la nueva fecha de expiración para el reenvio de mail.
        confirmationTokenModificar.setExpiresAt(LocalDateTime.now().plusMinutes(15));
        String token = confirmationTokenModificar.getToken();
        confirmationTokenService.saveConfirmationToken(
                confirmationTokenModificar);
        String link = "http://localhost:4200/cuenta-verificada?token=" + token;

        // Se reenvia en mail para verificar cuenta al usuario.
        emailSender.send(
                cuentaReenviar.getCorreo(),
                generarMailVerificarCuenta(cuentaReenviar.getCorreo(), link));
        System.out.println(link);
        return null;
    }

    // Método para generar el mail verificación en 'HTML' concatenando nombre de
    // usuario y link de verificación de cuenta.
    private String generarMailVerificarCuenta(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n"
                +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n"
                +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n"
                +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n"
                +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Activa tu cuenta!</span>\n"
                +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n"
                +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n"
                +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n"
                +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n"
                +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hola " + name
                + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Gracias por registrarte en Talk2gether. Por favor presiona en el siguiente link para activar tu cuenta: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\""
                + link
                + "\">Activar ahora!</a> </p></blockquote>\n El link va a expirar en 15 minutos. <p>Nos vemos luego.</p>"
                +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }

    // Método para llevar a cabo las validaciones correspondientes a datos que son
    // ingresados al sistema.
    public void validaciones(Usuario usuario) {

        // Validar correo ingresado.
        try {
            emailValidator.validarEmail(usuario.getCuenta().getCorreo());
        } catch (IllegalStateException e) {
            throw new IllegalStateException("Error al validar el correo: " + e.getMessage());
        }
        // Validar contraseña ingresada.
        try {
            passwordValidator.validarPassword(usuario.getCuenta().getContrasenia());
        } catch (IllegalStateException e) {
            throw new IllegalStateException("Error al validar la contraseña: " + e.getMessage());
        }
        // Validar nombre de usuario ingresado.
        try {
            textValidator.validarTexto(usuario.getNombreUsuario());
        } catch (IllegalStateException e) {
            throw new IllegalStateException("Error al validar el texto: " + e.getMessage());
        }
        // Validar apellido de usuario ingresado.
        try {
            textValidator.validarTexto(usuario.getApellidoUsuario());
        } catch (IllegalStateException e) {
            throw new IllegalStateException("Error al validar el texto: " + e.getMessage());
        }
        // Validar fecha de nacimiento de usuario ingresado.
        try {
            fechaValidator.validarFecha(usuario.getFechaNacimiento());
        } catch (IllegalStateException e) {
            throw new IllegalStateException("Error al validar la fecha: " + e.getMessage());
        }
        // Validar nombre del país.
        if (usuario.getPais() == null) {
            throw new IllegalStateException("El país no puede ser nulo");
        }
        try {
            textValidator.validarTexto(usuario.getPais().getNombrePais());
        } catch (IllegalStateException e) {
            throw new IllegalStateException("Error al validar el país: " + e.getMessage());
        }
        // Validar nombre del idioma aprendiz.
        if (usuario.getIdiomaAprendiz() == null) {
            throw new IllegalStateException("El idioma aprendiz no puede ser nulo");
        }
        try {
            textValidator.validarTexto(usuario.getIdiomaAprendiz().getIdioma().getNombreIdioma());
        } catch (IllegalStateException e) {
            throw new IllegalStateException("Error al validar el idioma aprendiz: " + e.getMessage());
        }
        // Validar nivel del idioma aprendiz.
        if (usuario.getIdiomaAprendiz().getNivelIdioma() == null) {
            throw new IllegalStateException("El nivel del idioma aprendiz no puede ser nulo");
        }
        try {
            textValidator.validarTexto(usuario.getIdiomaAprendiz().getNivelIdioma().getNombreNivelIdioma());
        } catch (IllegalStateException e) {
            throw new IllegalStateException("Error al validar el nivel del idioma aprendiz: " + e.getMessage());
        }
        // Validar nombre del idioma nativo.
        if (usuario.getIdiomaNativo() == null) {
            throw new IllegalStateException("El idioma aprendiz no puede ser nulo");
        }
        try {
            textValidator.validarTexto(usuario.getIdiomaNativo().getIdioma().getNombreIdioma());
        } catch (IllegalStateException e) {
            throw new IllegalStateException("Error al validar el idioma nativo: " + e.getMessage());
        }
        // Validar nivel del idioma nativo.
        if (usuario.getIdiomaNativo().getNivelIdioma() == null) {
            throw new IllegalStateException("El nivel del idioma nativo no puede ser nulo");
        }
        try {
            textValidator.validarTexto(usuario.getIdiomaNativo().getNivelIdioma().getNombreNivelIdioma());
        } catch (IllegalStateException e) {
            throw new IllegalStateException("Error al validar el nivel del idioma nativo: " + e.getMessage());
        }
        // Validar que no sea nula la URL de la foto.
        if (usuario.getCuenta().getUrlFoto() == null || usuario.getCuenta().getUrlFoto().equals("")) {
            throw new IllegalStateException("La URL de la foto no puede ser nula");
        }

    }

    // Método para generar un link de recuperación y enviar mail de recuperación de
    // contraseña al usuario.
    @Override
    public String recuperarContrasenia(String correo) {
        // Se realiza la búsqueda del usuario y del token ya enviado para modificarlo.
        Optional<Cuenta> cuenta = cuentaRepository.findByCorreo(correo);
        Cuenta cuentaRecuperar = cuenta.get();
        // Se realiza la encriptación del ID del usuario, la cuál es concatenada al link
        // de recuperación.
        String idEncriptado = EncryptionUtils.encrypt(cuentaRecuperar.getId().toString());
        System.out.println(idEncriptado);

        // Se crea el link de recuperación de contraseña, concatenando el id del usuario
        // encriptada.
        String link = "http://localhost:4200/nuevaContrasenia?correo=" + idEncriptado;

        // Se envia el mail de recuperación al usuario.
        emailSender.send(
                cuentaRecuperar.getCorreo(),
                generarMailRecuperarContrasenia(cuentaRecuperar.getCorreo(), link));
        System.out.println(link);
        return null;
    }

    // Método realizar el cambio de contraseña como usuario aprendiz.
    @Override
    public String confirmarCambioContrasenia(String idCuenta, String contrasenia) throws IllegalStateException {

        // Se realiza la validación de la contraseña ingresada.
        try {
            if (contrasenia == null) {
                contrasenia = "";
            }
            passwordValidator.validarPassword(contrasenia);
        } catch (IllegalStateException e) {
            throw new IllegalStateException("Error al validar la contraseña: " + e.getMessage());
        }
        // Se realiza la desencriptación de el id del usuario enviado por el link.
        String idDesencriptado = EncryptionUtils.decrypt(idCuenta);

        // Se realiza la búsqueda de la cuenta del usuario por id.
        Optional<Cuenta> cuenta = cuentaRepository.findById(Long.parseLong(idDesencriptado));

        // Se realiza la encriptación de la nueva contraseña para ser almacenada en la
        // base datos.
        Cuenta cuentaRecuperar = cuenta.get();
        String encodedPassword = bCryptPasswordEncoder
                .encode(contrasenia);
        cuentaRecuperar.setContrasenia(encodedPassword);
        cuentaRepository.save(cuentaRecuperar);
        return null;
    }

    // Método para generar el mail de recuperación en 'HTML' concatenando nombre de
    // usuario y link de recuperación de cuenta.
    private String generarMailRecuperarContrasenia(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n"
                +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n"
                +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n"
                +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n"
                +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Recuperar contraseña</span>\n"
                +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n"
                +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n"
                +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n"
                +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n"
                +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hola " + name
                + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Bienvenido al soporte de Talk2gether. Por favor presiona en el siguiente link para recuperar tu contraseña: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\""
                + link
                + "\">RECUPERAR AHORA</a> </p></blockquote>\n <p>Nos vemos luego.</p>"
                +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }

    // Método para visualizar el perfil de otro usuario aprendiz.
    @Override
    public DTOPerfilOtroUsuario visualizarOtroPerfil(Long idCuenta) throws Exception {
        // Verificar que exista la cuenta.
        Optional<Long> idUsuario = cuentaRepository.obtenerIdUsuario(idCuenta);
        if (!idUsuario.isPresent()) {
            throw new Exception("No existe la cuenta");
        }

        // Se realiza la búsqueda del usuario que se va a visitar el perfil.
        Optional<Usuario> usuario = usuarioRepository.findById(idUsuario.get());

        // Se instancia un objeto DTOPerfilOtroUsuario en donde se almacenan todos los
        // datos que serán mostrados en el perfil del otro usuario aprendiz.
        DTOPerfilOtroUsuario dtoPerfilOtroUsuario = new DTOPerfilOtroUsuario();

        dtoPerfilOtroUsuario.setNombreUsuario(usuario.get().getNombreUsuario());
        dtoPerfilOtroUsuario.setApellidoUsuario(usuario.get().getApellidoUsuario());
        dtoPerfilOtroUsuario.setEdadUsuario(usuario.get().calcularEdad());
        dtoPerfilOtroUsuario.setDescripcionUsuario(usuario.get().getDescripcionUsuario());
        dtoPerfilOtroUsuario.setRankingPosicion(calculoTablaRanking(usuario.get().getCuenta().getId()));
        dtoPerfilOtroUsuario.setPuntosTotales(usuario.get().calculoPuntosTotales());
        dtoPerfilOtroUsuario.setCantidadEstrellas(usuario.get().promedioCalificacion());
        dtoPerfilOtroUsuario.setUrlFoto(usuario.get().getCuenta().getUrlFoto());

        if (usuario.get().getIdiomaAprendiz().getIdioma() != null) {
            dtoPerfilOtroUsuario.setNombreIdiomaAprendiz(usuario.get().getIdiomaAprendiz().getIdioma().getNombreIdioma());
        } else {
            dtoPerfilOtroUsuario.setNombreIdiomaAprendiz("");
        }

        if (usuario.get().getIdiomaAprendiz().getNivelIdioma() != null) {
            dtoPerfilOtroUsuario.setNombreNivelIdiomaAprendiz(usuario.get().getIdiomaAprendiz().getNivelIdioma().getNombreNivelIdioma());
        } else {
            dtoPerfilOtroUsuario.setNombreNivelIdiomaAprendiz("");
        }

        if (usuario.get().getIdiomaNativo().getIdioma() != null) {
            dtoPerfilOtroUsuario.setNombreIdiomaNativo(usuario.get().getIdiomaNativo().getIdioma().getNombreIdioma());
        } else {
            dtoPerfilOtroUsuario.setNombreIdiomaNativo("");
        }

        if (!(usuario.get().interesesToString().isEmpty())) {
            dtoPerfilOtroUsuario.setIntereses(usuario.get().interesesToString());
        } else {
            List<String> lista = new ArrayList<>();
            dtoPerfilOtroUsuario.setIntereses(lista);
        }

        if (usuario.get().getPais() != null) {
            dtoPerfilOtroUsuario.setUrlBandera(usuario.get().getPais().getUrlBandera());
            dtoPerfilOtroUsuario.setNombrePais(usuario.get().getPais().getNombrePais());

        } else {
            dtoPerfilOtroUsuario.setUrlBandera("");
            dtoPerfilOtroUsuario.setNombrePais("");
        }

        return dtoPerfilOtroUsuario;
    }


    // Método para posicionar al usuario aprendiz en la tabla de ranking.
    public int calculoTablaRanking(Long idCuenta) {

        // Se realiza la búsqueda de todos los usuarios en la base de datos.
        List<DTOTablaRanking> listaUsuario = usuarioRepository.listaUsuariosTablaRanking();
        int contador = 1;
        int posicion = 0;
        
        // Se recorre la lista para poder ubicar la posición del usuario.
        for (DTOTablaRanking usuario : listaUsuario) {
            if (usuario.getId() == idCuenta) {
                posicion = contador;
            }
            contador++;
        }
        // Retorna la posición en la tabla de ranking del usuario aprendiz.
        return posicion;
    }

    // Método para cambiar la contraseña de la cuenta (Se encuentra en usuario por
    // motivos de dependencias).
    @Override
    public String cambiarContrasenia(DTOCambiarContrasenia dtoCambiarContrasenia) throws Exception {
        // Verificar la existencia de la cuenta
        if (!cuentaRepository.existsById(dtoCambiarContrasenia.getId())) {
            throw new Exception("La cuenta asociada a ese ID no existe.");
        }
        // Validar la contraseña nueva.
        try {
            passwordValidator.validarPassword(dtoCambiarContrasenia.getContraseniaNueva());
        } catch (IllegalStateException e) {
            throw new IllegalStateException("Error al validar la contraseña nueva: " + e.getMessage());
        }

        String contraseniaAntigua = cuentaRepository.findById(dtoCambiarContrasenia.getId()).get().getContrasenia();
        // Comparar si la contraseña antigüa coincide con la ingresada.
        boolean compararContraseniaAntigua = bCryptPasswordEncoder
                .matches(dtoCambiarContrasenia.getContraseniaAntigua(), contraseniaAntigua);
        if (!compararContraseniaAntigua) {
            throw new Exception("La contraseña actual es incorrecta.");
        }
        // Comparar si las contraseñas ingresadas son iguales (La antigüa y la nueva).
        boolean compararContraseniaNueva = bCryptPasswordEncoder.matches(dtoCambiarContrasenia.getContraseniaNueva(),
                contraseniaAntigua);
        if (compararContraseniaNueva) {
            throw new Exception("La contraseña nueva debe ser distinta de la actual.");
        }
        Cuenta cuenta = cuentaRepository.findById(dtoCambiarContrasenia.getId()).get();
        // Guardar contraseña nueva encriptada.
        cuenta.setContrasenia(bCryptPasswordEncoder.encode(dtoCambiarContrasenia.getContraseniaNueva()));
        cuentaRepository.save(cuenta);
        return "Contraseña cambiada correctamente.";
    }

    @Override
    public String calificarUsuario(DTOCalificarUsuario dtoCalificarUsuario) throws Exception {
        // Verificar que exista el usuario calificador.
        if (!cuentaRepository.existsById(dtoCalificarUsuario.getIdCuentaCalificador())) {
            throw new Exception("El usuario calificador no existe.");
        }
        // Verificar que exista el usuario que se quiere calificar.
        if (!cuentaRepository.existsById(dtoCalificarUsuario.getIdCuentaCalificado())) {
            throw new Exception("El usuario que intenta calificar no existe.");
        }
        // Verificar que exista la reunión virtual.
        if (!reunionVirtualRepository.existsById(dtoCalificarUsuario.getIdReunionVirtual())) {
            throw new Exception("La reunión virtual no existe.");
        }
        // Verificar que la reunion virtual NO siga activa
        Optional<ReunionVirtual> reunionVirtual = reunionVirtualRepository
                .findById(dtoCalificarUsuario.getIdReunionVirtual());
//        if (reunionVirtual.get().getFechaHoraFinVigenciaReunionVirtual() == null) {
//            throw new Exception("La reunión virtual todavia sigue activa.");
//        }
        // Verificar que ambos usuarios hayan participado en la reunión virtual antes de
        // calificarse.
        for (DetalleParticipante recorrerDetalleParticipante : reunionVirtual.get().getListaDetalleParticipantes()) {
            if (!(recorrerDetalleParticipante.getParticipante().getId() == dtoCalificarUsuario.getIdCuentaCalificador()
                    || recorrerDetalleParticipante.getParticipante().getId() == dtoCalificarUsuario
                    .getIdCuentaCalificado())) {
                throw new Exception("No participaste en esta reunión virtual.");
            }
        }
        // Verificar si el usuario que intenta calificar ya calificó antes.
        Optional<UsuarioCalificacion> usuarioCalificador = usuarioCalificacionRepository
                .findByIdUsuarioCalificadorAndIdReunionVirtual(dtoCalificarUsuario.getIdCuentaCalificador(),
                        dtoCalificarUsuario.getIdReunionVirtual());
        if (usuarioCalificador.isPresent()) {
            throw new Exception("Ya calificaste.");
        }
        // Verificar que exista la cantidad de estrellas seleccionadas.
        Optional<Calificacion> cantidadEntrellas = calificacionRepository
                .findByCantidadEstrellas(dtoCalificarUsuario.getCantidadEstrellas());
        if (!cantidadEntrellas.isPresent()) {
            throw new Exception("La cantidad seleccionada no existe.");
        }
        // Obtener id de la cuenta del usuario calificador.
        Optional<Long> idUsuarioCalificador = cuentaRepository
                .obtenerIdUsuario(dtoCalificarUsuario.getIdCuentaCalificador());
        // Obtener id de la cuenta del usuario calificado.
        Optional<Long> idUsuarioCalificado = cuentaRepository
                .obtenerIdUsuario(dtoCalificarUsuario.getIdCuentaCalificado());

        // Crear la calificación para luego guardarla en el usuario calificado.
        UsuarioCalificacion usuarioCalificacion = new UsuarioCalificacion(new Date(), cantidadEntrellas.get(),
                idUsuarioCalificador.get(), dtoCalificarUsuario.getIdReunionVirtual());
        Optional<Usuario> usuarioCalificado = usuarioRepository.findById(idUsuarioCalificado.get());
        usuarioCalificado.get().getListaUsuarioCalificacion().add(usuarioCalificacion);
        // Guardar calificación.
        usuarioRepository.save(usuarioCalificado.get());

        return "Usuario calificado correctamente.";
    }

    @Override
    public String modificarUsuarioDesdeCuentaAprendiz(Long idCuenta, DTORegistrarUsuario dtoModificarDatosUsuario)
            throws Exception {
        if (!cuentaRepository.existsById(idCuenta)) {
            throw new Exception("La cuenta que quiere modificar no existe.");
        }
        Optional<Long> idUsuario = cuentaRepository.obtenerIdUsuario(idCuenta);
        if (!idUsuario.isPresent()) {
            throw new Exception("La cuenta que quiere modificar los datos está eliminada o no verificada.");
        }
        Optional<Usuario> usuario = usuarioRepository.findById(idUsuario.get());
        if (dtoModificarDatosUsuario.getNombreUsuario() != null) {
            // Validar nombre de usuario ingresado.
            try {
                textValidator.validarTexto(dtoModificarDatosUsuario.getNombreUsuario());
                usuario.get().setNombreUsuario(dtoModificarDatosUsuario.getNombreUsuario());
            } catch (IllegalStateException e) {
                throw new IllegalStateException("Error al validar el texto: " + e.getMessage());
            }
        }

        if (dtoModificarDatosUsuario.getContrasenia() != null) {
            // Validar contraseña ingresada.
            try {
                passwordValidator.validarPassword(dtoModificarDatosUsuario.getContrasenia());
                // Encriptar contraseña de usuario
                String encodedPassword = bCryptPasswordEncoder
                        .encode(dtoModificarDatosUsuario.getContrasenia());
                usuario.get().getCuenta().setContrasenia(encodedPassword);
            } catch (IllegalStateException e) {
                throw new IllegalStateException("Error al validar la contraseña: " + e.getMessage());
            }
        }
        if (dtoModificarDatosUsuario.getApellidoUsuario() != null) {
            // Validar apellido de usuario ingresado.
            try {
                textValidator.validarTexto(dtoModificarDatosUsuario.getApellidoUsuario());
                usuario.get().setApellidoUsuario(dtoModificarDatosUsuario.getApellidoUsuario());
            } catch (IllegalStateException e) {
                throw new IllegalStateException("Error al validar el texto: " + e.getMessage());
            }
        }
        if (dtoModificarDatosUsuario.getFechaNacimiento() != null) {
            // Validar fecha de nacimiento de usuario ingresado.
            try {
                // Se realizar la conversión del formato de la fecha ingresada a 'yyyy-MM-dd'.
                DateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
                Date fechaNacimiento = null;
                fechaNacimiento = formatoFecha.parse(dtoModificarDatosUsuario.getFechaNacimiento());
                fechaValidator.validarFecha(fechaNacimiento);
                usuario.get().setFechaNacimiento(fechaNacimiento);
            } catch (IllegalStateException e) {
                throw new IllegalStateException("Error al validar la fecha: " + e.getMessage());
            }
        }

        if (dtoModificarDatosUsuario.getNombrePais() != null) {
            try {
                textValidator.validarTexto(dtoModificarDatosUsuario.getNombrePais());
                // Buscar pais ingresado y verificar si existe.
                Optional<Pais> pais = paisRepository
                        .findByNombrePaisAndFechaHoraFinVigenciaPaisIsNull(
                                dtoModificarDatosUsuario.getNombrePais());
                if (!pais.isPresent()) {
                    throw new IllegalStateException("El pais ingresado no existe");
                }
                usuario.get().setPais(pais.get());
            } catch (IllegalStateException e) {
                throw new IllegalStateException("Error al validar el país: " + e.getMessage());
            }
        }
        if (dtoModificarDatosUsuario.getNombreIdiomaAprendiz() != null) {
            try {
                textValidator.validarTexto(dtoModificarDatosUsuario.getNombreIdiomaAprendiz());
                // Buscar idioma aprendiz ingresado y verificar si existe.
                Optional<Idioma> idiomaAprendiz = idiomaRepository
                        .findByNombreIdiomaAndFechaHoraFinVigenciaIdiomaIsNull(
                                dtoModificarDatosUsuario.getNombreIdiomaAprendiz());
                if (!idiomaAprendiz.isPresent()) {
                    throw new IllegalStateException("El idioma a aprender ingresado no existe");
                }
                usuario.get().getIdiomaAprendiz().setIdioma(idiomaAprendiz.get());
            } catch (IllegalStateException e) {
                throw new IllegalStateException("Error al validar el idioma aprendiz: " + e.getMessage());
            }
        }
        if (dtoModificarDatosUsuario.getNombreNivelIdiomaAprendiz() != null) {
            try {
                textValidator.validarTexto(dtoModificarDatosUsuario.getNombreNivelIdiomaAprendiz());
                // Buscar nivel de idioma aprendiz ingresado y verificar si existe.
                Optional<NivelIdioma> nivelIdiomaAprendiz = nivelIdiomaRepository
                        .findByNombreNivelIdiomaAndFechaHoraFinVigenciaNivelIdiomaIsNull(
                                dtoModificarDatosUsuario.getNombreNivelIdiomaAprendiz());
                if (!nivelIdiomaAprendiz.isPresent()) {
                    throw new IllegalStateException("El nivel del idioma aprendiz ingresado no existe");
                }
                usuario.get().getIdiomaAprendiz().setNivelIdioma(nivelIdiomaAprendiz.get());
            } catch (IllegalStateException e) {
                throw new IllegalStateException("Error al validar el nivel del idioma aprendiz: " + e.getMessage());
            }
        }
        if (dtoModificarDatosUsuario.getNombreIdiomaNativo() != null) {
            try {
                textValidator.validarTexto(dtoModificarDatosUsuario.getNombreIdiomaNativo());
                // Buscar idioma nativo ingresado y verificar si existe.
                Optional<Idioma> idiomaNativo = idiomaRepository
                        .findByNombreIdiomaAndFechaHoraFinVigenciaIdiomaIsNull(
                                dtoModificarDatosUsuario.getNombreIdiomaNativo());
                if (!idiomaNativo.isPresent()) {
                    throw new IllegalStateException("El idioma natal ingresado no existe");
                }
                usuario.get().getIdiomaNativo().setIdioma(idiomaNativo.get());
            } catch (IllegalStateException e) {
                throw new IllegalStateException("Error al validar el idioma nativo: " + e.getMessage());
            }
        }
        // Asignar los intereses al usuario
        if (dtoModificarDatosUsuario.getNombreIntereses() != null
                || dtoModificarDatosUsuario.getNombreIntereses().isEmpty()) {
            for (UsuarioInteres interes : usuario.get().getListaIntereses()) {
                interes.setFechaHoraFinVigenciaInteres(new Date());
            }
            for (int i = 0; i < dtoModificarDatosUsuario.getNombreIntereses().size(); i++) {
                Optional<Interes> interes = interesRepository
                        .findByNombreInteresAndFechaHoraFinVigenciaInteresIsNull(
                                dtoModificarDatosUsuario.getNombreIntereses().get(i));
                if (!interes.isPresent()) {
                    throw new IllegalStateException("El interes "+ dtoModificarDatosUsuario.getNombreIntereses().get(i) +" ingresado no existe");
                }
                UsuarioInteres usuarioInteres = new UsuarioInteres(new Date(), null, interes.get());
                usuario.get().getListaIntereses().add(usuarioInteres);
            }
        }

        if (dtoModificarDatosUsuario.getUrlFoto() != null) {
            // Validar que no sea nula la URL de la foto.
            if (dtoModificarDatosUsuario.getUrlFoto() == null
                    || dtoModificarDatosUsuario.getUrlFoto().equals("")) {
                throw new IllegalStateException("La URL de la foto no puede ser nula");
            }
            usuario.get().getCuenta().setUrlFoto(dtoModificarDatosUsuario.getUrlFoto());
        }
        if(dtoModificarDatosUsuario.getDescripcion() !=null){
            usuario.get().setDescripcionUsuario(dtoModificarDatosUsuario.getDescripcion());
        }
        usuarioRepository.save(usuario.get());
        return "Datos modificados correctamente";
    }

    // Método para que el usuario aprendiz pueda eliminar su propia cuenta.
    @Override
    public String eliminarCuenta(DTOMotivosCuentaEliminada dtoMotivosCuentaEliminada)
            throws Exception {
        // Verificar si existe el usuario que desea eliminar su cuenta.
        if (!cuentaRepository.existsById(dtoMotivosCuentaEliminada.getIdCuenta())) {
            throw new Exception("La cuenta no existe.");
        }
        Optional<Cuenta> cuenta = cuentaRepository.findById(dtoMotivosCuentaEliminada.getIdCuenta());
        // Verificar si la contraseña ingresada coincide
        if (dtoMotivosCuentaEliminada.getContrasenia() == null) {
            throw new Exception("El campo contraseña no puede estar vacio.");
        }
        if (!(bCryptPasswordEncoder.matches(dtoMotivosCuentaEliminada.getContrasenia(), cuenta.get().getContrasenia()))) {
            throw new Exception("La contraseña es incorrecta");
        }
        // Verificar si el usuario que desea eliminar su cuenta, ha eliminado la misma
        // previamente.
        if (cuenta.get().getCuentaEliminada() != null) {
            throw new Exception("Ésta cuenta ya ha sido eliminada.");
        }
        List<ReporteMotivo> reporteMotivoRecibidos = reporteMotivoRepository
                .findByIdCuentaReportada(dtoMotivosCuentaEliminada.getIdCuenta());
        for (ReporteMotivo reporteMotivo : reporteMotivoRecibidos) {
            reporteMotivo.setFechaHoraFinVigenciaReporteMotivo(new Date());
            reporteMotivoRepository.save(reporteMotivo);
        }

        // Se realiza la busqueda de motivos seleccionados por el usuario apendiz a la
        // hora de eliminar su cuenta.
        List<CuentaEliminadaMotivo> listaCuentaEliminadaMotivo = new ArrayList<>();
        for (String motivo : dtoMotivosCuentaEliminada.getListaMotivos()) {
            Optional<Motivo> motivoAgregar = motivoRepository.findByNombreMotivo(motivo);
            // Verificar que exista el motivo seleccionado por el usuario.
            if (!motivoAgregar.isPresent()) {
                throw new Exception("El motivo no existe.");
            }
            if (!motivoAgregar.get().getTipoMotivo().getNombreTipoMotivo().equals("usuario")) {
                throw new Exception("El motivo seleccionado no pertenece a tipo usuario.");
            }
            // Añadir el motivo a la lista de motivos de cuenta eliminada.
            CuentaEliminadaMotivo cuentaEliminadaMotivo = new CuentaEliminadaMotivo(new Date(), null,
                    motivoAgregar.get());
            listaCuentaEliminadaMotivo.add(cuentaEliminadaMotivo);
        }
        // Se completa el atributo fechaHoraFinVigenciaBloqueo a los objetos de bloqueo
        // de la cuenta.
        List<Bloqueos> bloqueos = bloqueosRepository.listaCuentasBloqueadas(dtoMotivosCuentaEliminada.getIdCuenta());
        if (!bloqueos.isEmpty()) {
            for (Bloqueos bloqueo : bloqueos) {
                bloqueo.setFechaHoraFinVigenciaBloqueoRealizado(new Date());
            }
        }
        // Se instancia un objeto de cuentaEliminada para poder eliminar de forma lógica
        // la cuenta y éste se guarda en la base de datos.
        CuentaEliminada cuentaEliminada = new CuentaEliminada(new Date(), null, listaCuentaEliminadaMotivo,
                dtoMotivosCuentaEliminada.getDescripcionCuentaEliminada());
        cuenta.get().setCuentaEliminada(cuentaEliminada);
        cuentaRepository.save(cuenta.get());
        return "Cuenta eliminada correctamente";
    }

    @Override
    public List<DTOListarReunionesVirtuales> buscarUsuario(String parametroDeBusqueda, long idCuenta) throws Exception {
        List<DTOListarReunionesVirtualesRepository> dtoListarReunionesVirtuales = usuarioRepository.buscarUsuario(parametroDeBusqueda);
        List<DTOListarReunionesVirtuales> asignarUsuariosEncontrados = new ArrayList<>();

        List<Bloqueos> listaBloqueos = bloqueosRepository.listaCuentasQueMeBloquearon(idCuenta);
        Set<Long> idCuentasBloqueadas = new HashSet<>();
        for (Bloqueos bloqueo : listaBloqueos) {
            idCuentasBloqueadas.add(bloqueo.getUsuarioQueBloquea().getId());
        }


        for (DTOListarReunionesVirtualesRepository usuariosEncontrados : dtoListarReunionesVirtuales) {
            if(usuariosEncontrados.getIdCuenta()!=idCuenta) {
                if (!(idCuentasBloqueadas.contains(usuariosEncontrados.getIdCuenta()))) {

                    DTOListarReunionesVirtuales dto = new DTOListarReunionesVirtuales();
                    Optional<DTOReunionVirtual> reunionExistente = reunionVirtualRepository.obtenerLinkReunionVirtualActivaPorIdCuenta(usuariosEncontrados.getIdCuenta());
                    if (reunionExistente.isPresent()) {
                        dto.setIdReunionVirtual(reunionExistente.get().getId());
                        dto.setLinkReunionVirtual(reunionExistente.get().getLinkReunionVirtual());
                    } else {
                        dto.setIdReunionVirtual(-1);
                        dto.setLinkReunionVirtual(null);
                    }
                    dto.setIdCuenta(usuariosEncontrados.getIdCuenta());
                    dto.setNombreUsuario(usuariosEncontrados.getNombreUsuario());
                    dto
                            .setEdad(Functions.calcularEdad(usuariosEncontrados.getFechaNacimiento()));
                    dto.setUrlBandera(usuariosEncontrados.getUrlBandera());
                    dto.setUrlFoto(usuariosEncontrados.getUrlFoto());
                    List<Long> listaCalificacion = cuentaRepository
                            .obtenerCalificacionUsuario(usuariosEncontrados.getIdCuenta());
                    dto
                            .setCantidadEstrellas(Functions.promedioCalificacion(listaCalificacion));
                    dto.setNombrePais(usuariosEncontrados.getNombrePais());
                    dto.setNombreNivelIdioma(usuariosEncontrados.getNombreNivelIdioma());
                    List<DTOInteres> listaInteres = cuentaRepository.obtenerIntereses(usuariosEncontrados.getIdCuenta());
                    for (DTOInteres recorrerInteres : listaInteres) {
                        dto.getIntereses().add(recorrerInteres.getName());
                    }
                    asignarUsuariosEncontrados.add(dto);
                }
            }
        }
        return asignarUsuariosEncontrados;
    }
}
