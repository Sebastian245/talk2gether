package backend.backend.services.administrador;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import backend.backend.DTO.DTOAdministrarUsuario_Administrador;
import backend.backend.DTO.DTOCalificaciones;
import backend.backend.DTO.DTODatosCalificacion;
import backend.backend.DTO.DTOEliminarUsuario_Administrador;
import backend.backend.DTO.DTOModificarUsuario_Administrador;
import backend.backend.DTO.DTOMotivosCuentaEliminada;
import backend.backend.DTO.DTOUsuario_Administrador;
import backend.backend.DTO.DTOUsuariosPorMes;
import backend.backend.DTO.DTOUsuariosPorPais;
import backend.backend.DTO.DTOVisualizarEstadisticasPlataforma;
import backend.backend.entities.Bloqueos;
import backend.backend.entities.Cuenta;
import backend.backend.entities.CuentaEliminada;
import backend.backend.entities.CuentaEliminadaMotivo;
import backend.backend.entities.Motivo;
import backend.backend.entities.NivelIdioma;
import backend.backend.entities.ReporteMotivo;
import backend.backend.entities.Rol;
import backend.backend.entities.Usuario;
import backend.backend.entities.UsuarioIdioma;
import backend.backend.entities.UsuarioPuntuacion;
import backend.backend.repositories.BloqueosRepository;
import backend.backend.repositories.CuentaRepository;
import backend.backend.repositories.MotivoRepository;
import backend.backend.repositories.NivelIdiomaRepository;
import backend.backend.repositories.ReporteMotivoRepository;
import backend.backend.repositories.RolRepository;
import backend.backend.repositories.UsuarioAdministradorRepository;
import backend.backend.repositories.UsuarioRepository;
import backend.backend.utils.validation.EmailValidator;
import backend.backend.utils.validation.FechaValidator;
import backend.backend.utils.validation.PasswordValidator;
import backend.backend.utils.validation.TextValidator;

@Service
public class UsuarioAdministradorServiceImpl implements UsuarioAdministradorService {

    @Autowired
    private UsuarioAdministradorRepository usuarioAdministradorRepository;
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
    private UsuarioRepository usuarioRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private NivelIdiomaRepository nivelIdiomaRepository;
    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private ReporteMotivoRepository reporteMotivoRepository;
    @Autowired
    private MotivoRepository motivoRepository;
    @Autowired
    private BloqueosRepository bloqueosRepository;

    // Método para listar a los usuarios desde la cuenta administrador.
    @Override
    public List<DTOUsuario_Administrador> listarUsuariosDesdeCuentaAdministrador() throws Exception {
        return usuarioAdministradorRepository.obtenerUsuarios();
    }

    // Método dar de alta usuarios desde la cuenta administrador.
    @Override
    public String crearUsuarioDesdeCuentaAdministrador(
            DTOAdministrarUsuario_Administrador dtoCrearUsuario_Administrador) throws Exception {
        // Validar todos los datos ingresados por el usuario
        validaciones(dtoCrearUsuario_Administrador);
        // Verificar si existe en la base de datos el correo ingresado
        boolean correoExistente = cuentaRepository.findByCorreo(dtoCrearUsuario_Administrador.getCorreo()).isPresent();
        if (correoExistente) {
            throw new IllegalStateException("El correo ingresado ya existe");
        }
        // Instanciar objeto Usuario y realizar los 'Setters' correspondientes al mismo.
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(dtoCrearUsuario_Administrador.getNombreUsuario());
        usuario.setApellidoUsuario(dtoCrearUsuario_Administrador.getApellidoUsuario());
        usuario.setDescripcionUsuario("");

        // Se realizar la conversión del formato de la fecha ingresada a 'yyyy-MM-dd'.
        DateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaNacimiento = null;
        try {
            fechaNacimiento = formatoFecha.parse(dtoCrearUsuario_Administrador.getFechaNacimiento());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        usuario.setFechaNacimiento(fechaNacimiento);

        // Instanciar objeto Cuenta y realizar los 'Setters' correspondientes a la
        // misma.
        Cuenta cuenta = new Cuenta();
        cuenta.setCorreo(dtoCrearUsuario_Administrador.getCorreo());
        // Encriptar contraseña de usuario
        String encodedPassword = bCryptPasswordEncoder.encode(dtoCrearUsuario_Administrador.getContrasenia());
        cuenta.setContrasenia(encodedPassword);

        cuenta.setUrlFoto(
                "https://firebasestorage.googleapis.com/v0/b/talk2gether-a8b61.appspot.com/o/images%2Ficonousuario.png?alt=media&token=c6ed429e-701a-4f8b-bdee-4498ddaf970c");
        cuenta.setCuentaCreada(new Date());
        cuenta.setCuentaEliminada(null);
        cuenta.setCuentaVerificada(new Date());
        cuenta.setUltimaConexion(null);
        cuenta.setCantidadReferidos(0);

        // Buscar rol ingresado y verificar si existe.
        Optional<Rol> rol = rolRepository
                .findByNombreRolAndFechaHoraFinVigenciaRolIsNull(dtoCrearUsuario_Administrador.getNombreRol());
        if (!rol.isPresent()) {
            throw new IllegalStateException("El rol ingresado no existe");
        }
        cuenta.setRol(rol.get());

        // Buscar nivel de idioma nativo ingresado y verificar si existe.
        Optional<NivelIdioma> nivelIdiomaNativo = nivelIdiomaRepository.findByNombreNivelIdioma("Nativo");
        // if (!nivelIdiomaNativo.isPresent()) {
        // throw new IllegalStateException("El nivel del idioma nativo ingresado no
        // existe");
        // }
        UsuarioIdioma usuarioIdiomaAprendiz = new UsuarioIdioma();
        UsuarioIdioma usuarioIdiomaNativo = new UsuarioIdioma();
        // Optional<Idioma> idioma = idiomaRepository.findByNombreIdioma("Inglés");
        // usuarioIdiomaAprendiz.setIdioma(idioma.get());
        // usuarioIdiomaNativo.setIdioma(idioma.get());
        usuarioIdiomaNativo.setNivelIdioma(nivelIdiomaNativo.get());
        // usuarioIdiomaAprendiz.setNivelIdioma(nivelIdiomaAprendiz.get());

        // Optional<Pais> pais = paisRepository.findByNombrePais("Estados Unidos");
        // Asignar puntuacion en 0 al usuario nuevo
        UsuarioPuntuacion usuarioPuntuacion = new UsuarioPuntuacion(0, null, null);
        // Asignar al usuario una cuenta, pais, idiomas
        usuario.setCuenta(cuenta);
        usuario.setUsuarioPuntuacion(usuarioPuntuacion);

        // Guardar usuario
        // SETEAR EL CUENTA VERIFICADA Y INTERESES NULOS
        usuario.setListaIntereses(null);
        usuario.setIdiomaAprendiz(usuarioIdiomaAprendiz);
        usuario.setIdiomaNativo(usuarioIdiomaNativo);
        // usuario.setPais(pais.get());
        usuarioRepository.save(usuario);

        return "Usuario creado correctamente.";
    }

    // Método para llevar a cabo las validaciones correspondientes a datos que son
    // ingresados al sistema.
    public void validaciones(DTOAdministrarUsuario_Administrador dtoCrearUsuario_Administrador) {

        // Validar correo ingresado.
        try {
            emailValidator.validarEmail(dtoCrearUsuario_Administrador.getCorreo());
        } catch (IllegalStateException e) {
            throw new IllegalStateException("Error al validar el correo: " + e.getMessage());
        }
        // Validar contraseña ingresada.
        try {
            passwordValidator.validarPassword(dtoCrearUsuario_Administrador.getContrasenia());
        } catch (IllegalStateException e) {
            throw new IllegalStateException("Error al validar la contraseña: " + e.getMessage());
        }
        // Validar nombre de usuario ingresado.
        try {
            textValidator.validarTexto(dtoCrearUsuario_Administrador.getNombreUsuario());
        } catch (IllegalStateException e) {
            throw new IllegalStateException("Error al validar el texto: " + e.getMessage());
        }
        // Validar apellido de usuario ingresado.
        try {
            textValidator.validarTexto(dtoCrearUsuario_Administrador.getApellidoUsuario());
        } catch (IllegalStateException e) {
            throw new IllegalStateException("Error al validar el texto: " + e.getMessage());
        }
        // Validar fecha de nacimiento de usuario ingresado.
        try {
            // Se realizar la conversión del formato de la fecha ingresada a 'yyyy-MM-dd'.
            DateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaNacimiento = null;
            try {
                fechaNacimiento = formatoFecha.parse(dtoCrearUsuario_Administrador.getFechaNacimiento());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            fechaValidator.validarFecha(fechaNacimiento);
        } catch (IllegalStateException e) {
            throw new IllegalStateException("Error al validar la fecha: " + e.getMessage());
        }

    }

    @Override
    public String eliminarCuentaUsuario(DTOMotivosCuentaEliminada dtoMotivosCuentaEliminada) throws Exception {
        // Verificar si existe el usuario que desea eliminar su cuenta.
        if (!cuentaRepository.existsById(dtoMotivosCuentaEliminada.getIdCuenta())) {
            throw new Exception("La cuenta del usuario no existe.");
        }
        if (!cuentaRepository.existsById(dtoMotivosCuentaEliminada.getIdAdministradorResponsable())) {
            throw new Exception("La cuenta del administrador no existe.");
        }
        Optional<Cuenta> cuenta = cuentaRepository.findById(dtoMotivosCuentaEliminada.getIdCuenta());
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
            if (motivoAgregar.get().getTipoMotivo().getNombreTipoMotivo().equals("usuario")) {
                throw new Exception("El motivo seleccionado es de tipo usuario.");
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
        CuentaEliminada cuentaEliminada = new CuentaEliminada(new Date(),
                dtoMotivosCuentaEliminada.getIdAdministradorResponsable(), listaCuentaEliminadaMotivo,
                dtoMotivosCuentaEliminada.getDescripcionCuentaEliminada());
        cuenta.get().setCuentaEliminada(cuentaEliminada);
        cuentaRepository.save(cuenta.get());
        return "Cuenta eliminada correctamente";
    }

    @Override
    public List<DTOUsuario_Administrador> filtrarUsuarios(String parametroFiltro) throws Exception {

        List<DTOUsuario_Administrador> usuariosFiltrados = usuarioAdministradorRepository
                .filtrarUsuarios(parametroFiltro);

        return usuariosFiltrados;
    }

    @Override
    public String modificarUsuarioDesdeCuentaAdministrador(Long idCuenta,
            DTOModificarUsuario_Administrador dtoModificarUsuario_administrador) throws Exception {
        if (!cuentaRepository.existsById(idCuenta)) {
            throw new Exception("La cuenta que quiere modificar no existe.");
        }
        Optional<Long> idUsuario = cuentaRepository.obtenerIdUsuario(idCuenta);
        if (!idUsuario.isPresent()) {
            throw new Exception("La cuenta que quiere modificar los datos está eliminada o no verificada.");
        }
        Optional<Usuario> usuario = usuarioRepository.findById(idUsuario.get());

        if (dtoModificarUsuario_administrador.getNombreRol() != null) {
            Optional<Rol> rol = rolRepository
                    .findByNombreRolAndFechaHoraFinVigenciaRolIsNull(dtoModificarUsuario_administrador.getNombreRol());
            if (!rol.isPresent()) {
                throw new Exception("El rol ingresado no existe.");
            }
            usuario.get().getCuenta().setRol(rol.get());
        } else {
            throw new Exception("No se ingreso ningun rol");
        }

        usuarioRepository.save(usuario.get());
        return "Datos modificados correctamente";
    }

    // Método para mostrar informacion del usuario antes de ser eliminado.
    @Override
    public DTOEliminarUsuario_Administrador datoseliminarusuario(long idCuenta) throws Exception {
        if (!cuentaRepository.existsById(idCuenta)) {
            throw new Exception("La cuenta no existe");
        }
        DTOEliminarUsuario_Administrador dtoEliminarUsuario_administrador = usuarioAdministradorRepository
                .obtenerUsuario(idCuenta);
        return dtoEliminarUsuario_administrador;
    }

    @Override
    public DTOVisualizarEstadisticasPlataforma obtenerEstadisticasAdministrador() throws Exception {
        // Obtener total de usuarios registrados.
        int totalUsuarios = usuarioAdministradorRepository.obtenerCantidadUsuariosRegistrados();
        // Obtener total de calificaciones promediadas.
        double totalPromedioCalificaion = usuarioAdministradorRepository.obtenerPromedioCalificaciones();
        // Obtener total de duracion reunion virtual promediado.
        double totalPromedioDuracionReunionVirtual = usuarioAdministradorRepository.obtenerPromedioReunionVirtual();
        System.out.println(totalPromedioDuracionReunionVirtual);
        BigDecimal decimalValue = BigDecimal.valueOf(totalPromedioDuracionReunionVirtual);
        int totalPromedioDuracionReunionVirtualRedondeado = decimalValue.setScale(0, RoundingMode.HALF_UP).intValue();
        System.out.println(totalPromedioDuracionReunionVirtualRedondeado);
        // Obtener total de usuarios que utilizan videollamadas.
        int totalUsuariosQueUtilizanVideollamadas = usuarioAdministradorRepository
                .obtenerUsuariosQueUtilizanVideollamadas();
        // Obtener el total de usuario activos
        int totalUsuariosActivos = usuarioAdministradorRepository
                .obtenerUsuariosActivos(new Date(System.currentTimeMillis() - 15 * 60 * 1000));
        List<String> listaUsuarios= usuarioAdministradorRepository.obtenerUsuariosActivosCorreo(new Date(System.currentTimeMillis() - 15 * 60 * 1000));
        for (String lista: listaUsuarios
             ) {
            System.out.println(lista);
        }
        // Obtener el total de usuarios por pais.
        List<DTOUsuariosPorPais> usuariosPorPais = usuarioAdministradorRepository.obtenerUsuariosPorPais();
        // Obtener de usuarios por mes.
        List<DTOUsuariosPorMes> usuariosPorMes = usuarioAdministradorRepository.obtenerUsuariosPorMes();
        double porcentajeUsuariosQueUtilizanVideollamadas = ((double) totalUsuariosQueUtilizanVideollamadas
                / totalUsuarios) * 100.0;
        // Instanciar DTOVisualizarEstadisticasPlataforma y guardar los atributos.
        DTOVisualizarEstadisticasPlataforma dtoVisualizarEstadisticasPlataforma = new DTOVisualizarEstadisticasPlataforma();
        dtoVisualizarEstadisticasPlataforma.setUsuarioRegistradosTotal(totalUsuarios);
        dtoVisualizarEstadisticasPlataforma.setPromedioCalificacion(totalPromedioCalificaion);
        dtoVisualizarEstadisticasPlataforma
                .setTiempoPromedioUsuariosQueUtilizanVideollamada(totalPromedioDuracionReunionVirtualRedondeado);
        dtoVisualizarEstadisticasPlataforma.setUsuariosActivos(totalUsuariosActivos);
        dtoVisualizarEstadisticasPlataforma
                .setPorcentajeUsuariosQueUtilizanVideollamadas(porcentajeUsuariosQueUtilizanVideollamadas);

        /* LISTA DE USUARIOS POR MES */

        // Obtener la fecha actual
        Date fechaActual = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaActual);

        // Calcular el mes y año actual
        int mesActual = calendar.get(Calendar.MONTH) + 1; // Sumamos 1 porque los meses en Calendar van de 0 a 11
        int anioActual = calendar.get(Calendar.YEAR);

        // Crear una lista para almacenar los últimos 12 meses
        List<DTOUsuariosPorMes> ultimos12Meses = new ArrayList<>();

        // Calcular los últimos 12 meses
        for (int i = 0; i < 12; i++) {
            int mes = mesActual - i;
            int anio = anioActual;
            if (mes <= 0) {
                mes += 12;
                anio--;
            }

            boolean encontrado = false;
            for (DTOUsuariosPorMes dto : usuariosPorMes) {
                if (dto.getNumeroMes() == mes && dto.getAnio() == anio) {
                    ultimos12Meses.add(dto);
                    encontrado = true;
                    break;
                }
            }

            if (!encontrado) {
                ultimos12Meses.add(new DTOUsuariosPorMes(0L, mes, anio));
            }
        }

        // Ordenar la lista por año y mes ascendente
        ultimos12Meses.sort(Comparator.comparingInt(DTOUsuariosPorMes::getAnio)
                .thenComparingInt(DTOUsuariosPorMes::getNumeroMes));

        /* LISTA DE LOS 7 PAISES CON MAS USUARIOS REGISTRADOS */

        // Obtener los 7 países con más usuarios registrados
        List<DTOUsuariosPorPais> primerosPaises = usuariosPorPais.subList(0, Math.min(7, usuariosPorPais.size()));
        for (DTOUsuariosPorMes dtoUsuariosPorMes : ultimos12Meses) {
            switch (dtoUsuariosPorMes.getNumeroMes()) {
                case 1:
                    dtoUsuariosPorMes.setNombreMes("ENE");
                    break;
                case 2:
                    dtoUsuariosPorMes.setNombreMes("FEB");
                    break;
                case 3:
                    dtoUsuariosPorMes.setNombreMes("MAR");
                    break;
                case 4:
                    dtoUsuariosPorMes.setNombreMes("ABR");
                    break;
                case 5:
                    dtoUsuariosPorMes.setNombreMes("MAY");
                    break;
                case 6:
                    dtoUsuariosPorMes.setNombreMes("JUN");
                    break;
                case 7:
                    dtoUsuariosPorMes.setNombreMes("JUL");
                    break;
                case 8:
                    dtoUsuariosPorMes.setNombreMes("AGO");
                    break;
                case 9:
                    dtoUsuariosPorMes.setNombreMes("SEP");
                    break;
                case 10:
                    dtoUsuariosPorMes.setNombreMes("OCT");
                    break;
                case 11:
                    dtoUsuariosPorMes.setNombreMes("NOV");
                    break;
                case 12:
                    dtoUsuariosPorMes.setNombreMes("DIC");
                    break;
                default:
                    break;
            }
        }
        dtoVisualizarEstadisticasPlataforma.setUsuariosRegistradosPorMes(ultimos12Meses);
        dtoVisualizarEstadisticasPlataforma.setUsuariosRegistradosPorPais(primerosPaises);

        return dtoVisualizarEstadisticasPlataforma;
    }

    @Override
    public DTODatosCalificacion obtenerCalificaciones(long idCuenta) throws Exception {
        if (!cuentaRepository.existsById(idCuenta)) {
            throw new Exception("La cuenta no existe");
        }
        DTODatosCalificacion datosUsuario = usuarioAdministradorRepository.obtenerDatosUsuarioCalificacion(idCuenta);
        List<DTOCalificaciones> listaUsuarioCalificacion = usuarioAdministradorRepository
                .obtenerUsuariosCalificacion(idCuenta);
        for (DTOCalificaciones usuario : listaUsuarioCalificacion) {
            DTOCalificaciones dtoCalificaciones = usuarioAdministradorRepository
                    .obtenerUsuariosCalificaciones(usuario.getIdCuenta());
            usuario.setNombre(dtoCalificaciones.getNombre());
            usuario.setApellido(dtoCalificaciones.getApellido());
            usuario.setCorreo(dtoCalificaciones.getCorreo());
            usuario.setUrlFoto(dtoCalificaciones.getUrlFoto());
        }
        datosUsuario.setCalificaciones(listaUsuarioCalificacion);
        return datosUsuario;
    }

    @Override
    public DTODatosCalificacion filtrarCalificaciones(long idCuenta, String fechaDesdeStr, String fechaHastaStr)
            throws Exception {
        if (!cuentaRepository.existsById(idCuenta)) {
            throw new Exception("La cuenta no existe");
        }
        DTODatosCalificacion datosUsuario = usuarioAdministradorRepository.obtenerDatosUsuarioCalificacion(idCuenta);
        List<DTOCalificaciones> listaUsuarioCalificacion = usuarioAdministradorRepository
                .obtenerUsuariosCalificacion(idCuenta);
        List<DTOCalificaciones> calificacionesFiltradas = new ArrayList<>();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Formato de fecha

        for (DTOCalificaciones usuario : listaUsuarioCalificacion) {
            Date fechaCalificacion = usuario.getFechaCalificacion();

            // Convierte las fechas desde Strings a Date si no son nulas o vacías
            Date fechaDesde = null;
            if (fechaDesdeStr != null && !fechaDesdeStr.isEmpty()) {
                try {
                    fechaDesde = dateFormat.parse(fechaDesdeStr);
                } catch (Exception e) {
                    // Maneja cualquier excepción de parseo aquí
                }
            }

            Date fechaHasta = null;
            if (fechaHastaStr != null && !fechaHastaStr.isEmpty()) {
                try {
                    fechaHasta = dateFormat.parse(fechaHastaStr);
                } catch (Exception e) {
                    // Maneja cualquier excepción de parseo aquí
                }
            }

            // Verificar si fechaDesde y fechaHasta son nulos o no
            boolean cumpleFiltro = true;
            if (fechaDesde != null && fechaCalificacion.before(fechaDesde)) {
                cumpleFiltro = false;
            }
            if (fechaHasta != null && fechaCalificacion.after(fechaHasta)) {
                cumpleFiltro = false;
            }

            if (cumpleFiltro) {
                // Si cumple el filtro, obtenemos los detalles adicionales
                DTOCalificaciones dtoCalificaciones = usuarioAdministradorRepository
                        .obtenerUsuariosCalificaciones(usuario.getIdCuenta());
                usuario.setNombre(dtoCalificaciones.getNombre());
                usuario.setApellido(dtoCalificaciones.getApellido());
                usuario.setCorreo(dtoCalificaciones.getCorreo());
                usuario.setUrlFoto(dtoCalificaciones.getUrlFoto());
                calificacionesFiltradas.add(usuario);
            }
        }

        datosUsuario.setCalificaciones(calificacionesFiltradas);
        return datosUsuario;
    }

    @Override
    public DTOVisualizarEstadisticasPlataforma obtenerEstadisticasAdministradorGranuladas(String fechaDesde,
            String fechaHasta) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Define el formato de fecha
        Date fechaD = null;
        Date fechaH = null;
        if (fechaDesde.equals("") && fechaHasta.equals("")) {
            fechaD = dateFormat.parse("2000-01-01");
            fechaH = dateFormat.parse("3000-01-01");
        } else if (fechaDesde.equals("") && !(fechaHasta.equals(""))) {
            fechaD = dateFormat.parse("2000-01-01");
            fechaH = dateFormat.parse(fechaHasta);
        } else if (!(fechaDesde.equals("")) && fechaHasta.equals("")) {
            fechaD = dateFormat.parse(fechaDesde);
            fechaH = dateFormat.parse("3000-01-01");
        } else if (!(fechaDesde.equals("")) && !(fechaHasta.equals(""))) {
            fechaD = dateFormat.parse(fechaDesde);
            fechaH = dateFormat.parse(fechaHasta);
        }

        // Obtener total de usuarios registrados.
        int totalUsuarios = usuarioAdministradorRepository.obtenerCantidadUsuariosRegistradosGranulado(fechaD, fechaH);

        // Obtener total de calificaciones promediadas.
        double totalPromedioCalificacion = usuarioAdministradorRepository.obtenerPromedioCalificacionesGranulado(fechaD,
                fechaH);

        // Obtener total de duración reunión virtual promediado.
        double totalPromedioDuracionReunionVirtual = usuarioAdministradorRepository
                .obtenerPromedioReunionVirtualGranulado(fechaD, fechaH);
        int totalPromedioDuracionReunionVirtualRedondeado = (int) Math.round(totalPromedioDuracionReunionVirtual);

        // Obtener total de usuarios que utilizan videollamadas.
        int totalUsuariosQueUtilizanVideollamadas = usuarioAdministradorRepository
                .obtenerUsuariosQueUtilizanVideollamadasGranulado(fechaD, fechaH);

        System.out.println("usuarios que utilizaron videollamadas:"+totalUsuariosQueUtilizanVideollamadas);

        // Obtener el total de usuario activos
        int totalUsuariosActivos = usuarioAdministradorRepository
                .obtenerUsuariosActivos(new Date(System.currentTimeMillis() - 15 * 60 * 1000));

        // Obtener el total de usuarios por país.
        List<DTOUsuariosPorPais> usuariosPorPais = usuarioAdministradorRepository
                .obtenerUsuariosPorPaisGranulado(fechaD, fechaH);

        // Obtener de usuarios por mes en el rango de fechas
        List<DTOUsuariosPorMes> usuariosPorMes = new ArrayList<>();
        Calendar calDesde = Calendar.getInstance();
        calDesde.setTime(fechaD);
        Calendar calHasta = Calendar.getInstance();
        calHasta.setTime(fechaH);

        while (calDesde.before(calHasta) || calDesde.equals(calHasta)) {
            int mes = calDesde.get(Calendar.MONTH) + 1;
            int anio = calDesde.get(Calendar.YEAR);
            String nombreMes = obtenerNombreMesAbreviado(mes);
            usuariosPorMes.add(new DTOUsuariosPorMes(0L, mes, anio, nombreMes)); // Inicializa con 0 usuarios
            calDesde.add(Calendar.MONTH, 1);
        }

        // Actualizar los valores de usuarios registrados por mes según los datos reales
        List<DTOUsuariosPorMes> resultados = usuarioAdministradorRepository.obtenerUsuariosPorMesGranulado(fechaD,
                fechaH);

        for (DTOUsuariosPorMes resultado : resultados) {
            int mes = resultado.getNumeroMes();
            int anio = resultado.getAnio();
            long usuariosRegistrados = resultado.getCantidadUsuarios();

            for (DTOUsuariosPorMes dto : usuariosPorMes) {
                if (dto.getNumeroMes() == mes && dto.getAnio() == anio) {
                    dto.setCantidadUsuarios(usuariosRegistrados);
                    break;
                }
            }
        }

        Date fechaDesdeVideollamada= dateFormat.parse("2000-01-01");
        System.out.println("fecha desde cuando comieza a analizar videollamadas "+fechaDesdeVideollamada);
        System.out.println("fecha hasta donde se analizan las videollamadas "+fechaH);
        double porcentajeUsuariosQueUtilizanVideollamadas = 0;

        int usuariosTotalesParaVideollamada=usuarioAdministradorRepository.obtenerCantidadUsuariosRegistradosGranulado(fechaDesdeVideollamada, fechaH);
        System.out.println("usuarios totales hasta esa fecha "+usuariosTotalesParaVideollamada);

        if (usuariosTotalesParaVideollamada > 0) {
            porcentajeUsuariosQueUtilizanVideollamadas = ((double) totalUsuariosQueUtilizanVideollamadas
                    / usuariosTotalesParaVideollamada) * 100.0;
        }

        // Instanciar DTOVisualizarEstadisticasPlataforma y guardar los atributos.
        DTOVisualizarEstadisticasPlataforma dtoVisualizarEstadisticasPlataforma = new DTOVisualizarEstadisticasPlataforma();
        dtoVisualizarEstadisticasPlataforma.setUsuarioRegistradosTotal(totalUsuarios);
        dtoVisualizarEstadisticasPlataforma.setPromedioCalificacion(totalPromedioCalificacion);
        dtoVisualizarEstadisticasPlataforma
                .setTiempoPromedioUsuariosQueUtilizanVideollamada(totalPromedioDuracionReunionVirtualRedondeado);
        dtoVisualizarEstadisticasPlataforma.setUsuariosActivos(totalUsuariosActivos);
        dtoVisualizarEstadisticasPlataforma
                .setPorcentajeUsuariosQueUtilizanVideollamadas(porcentajeUsuariosQueUtilizanVideollamadas);

        // Lista de usuarios por mes
        dtoVisualizarEstadisticasPlataforma.setUsuariosRegistradosPorMes(usuariosPorMes);

        /* LISTA DE LOS 7 PAÍSES CON MÁS USUARIOS REGISTRADOS */

        // Obtener los 7 países con más usuarios registrados
        List<DTOUsuariosPorPais> primerosPaises = usuariosPorPais.subList(0, Math.min(7, usuariosPorPais.size()));

        dtoVisualizarEstadisticasPlataforma.setUsuariosRegistradosPorPais(primerosPaises);

        return dtoVisualizarEstadisticasPlataforma;
    }

    // Método para obtener el nombre abreviado de un mes a partir de su número
    private String obtenerNombreMesAbreviado(int numeroMes) {
        String[] nombresMeses = { "ENE", "FEB", "MAR", "ABR", "MAY", "JUN", "JUL", "AGO", "SEP", "OCT", "NOV", "DIC" };
        if (numeroMes >= 1 && numeroMes <= 12) {
            return nombresMeses[numeroMes - 1];
        } else {
            return "";
        }
    }

}
