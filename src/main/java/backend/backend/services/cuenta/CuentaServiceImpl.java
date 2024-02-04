package backend.backend.services.cuenta;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import backend.backend.DTO.DTOBloqueoUsuario;
import backend.backend.DTO.DTOCantidadSeguidosYSeguidores;
import backend.backend.DTO.DTODatosPersonales;
import backend.backend.DTO.DTODesbloqueoUsuario;
import backend.backend.DTO.DTOInteres;
import backend.backend.DTO.DTOReportarUsuario;
import backend.backend.DTO.DTOSeguirUsuario;
import backend.backend.DTO.DTOUsuarioBloqueado;
import backend.backend.DTO.DTOUsuarioChat;
import backend.backend.DTO.DTOVerificacion;
import backend.backend.DTO.DTOVisualizarEstadisticasAprendiz;
import backend.backend.DTORepository.DTOListarSeguidosRepository;
import backend.backend.entities.Bloqueos;
import backend.backend.entities.Cuenta;
import backend.backend.entities.Motivo;
import backend.backend.entities.Puntuacion;
import backend.backend.entities.ReporteMotivo;
import backend.backend.entities.Seguidores;
import backend.backend.entities.Seguidos;
import backend.backend.entities.Usuario;
import backend.backend.repositories.BloqueosRepository;
import backend.backend.repositories.CuentaRepository;
import backend.backend.repositories.MotivoRepository;
import backend.backend.repositories.ReporteMotivoRepository;
import backend.backend.repositories.SeguidoresRepository;
import backend.backend.repositories.SeguidosRepository;
import backend.backend.repositories.UsuarioRepository;
import backend.backend.utils.encryptation.EncryptionUtils;
import backend.backend.utils.functions.Functions;

@Service
public class CuentaServiceImpl implements CuentaService, UserDetailsService {

    // Constante utilizada en el método loadUserByUsername
    private final static String USER_NOT_FOUND = "El usuario con email %s no fue encontrado";

    @Autowired
    private CuentaRepository cuentaRepository;
    @Autowired
    private BloqueosRepository bloqueosRepository;
    @Autowired
    private MotivoRepository motivoRepository;
    @Autowired
    private SeguidosRepository seguidosRepository;
    @Autowired
    private SeguidoresRepository seguidoresRepository;
    @Autowired
    private ReporteMotivoRepository reporteMotivoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;


    // Método relacionado al inicio de sesión manejado por Spring Security.
    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        return cuentaRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND, correo)));
    }

    // Método para bloquear a otra cuenta.
    @Override
    public void bloquearCuenta(DTOBloqueoUsuario dtoBloquearUsuario) throws Exception {
        // Verificar si se está intentado bloquear a si mismo el usuario.
        if (dtoBloquearUsuario.getIdUsuarioQueBloquea() == dtoBloquearUsuario.getIdUsuarioBloqueado()) {
            throw new Exception("No se puede bloquear a sí mismo.");
        }
        // Búsqueda de ambas cuentas recibidas por el DTOBloqueaUsuario
        if (!cuentaRepository.existsById(dtoBloquearUsuario.getIdUsuarioQueBloquea())) {
            throw new Exception("El usuario bloqueador no existe.");
        }
        if (!cuentaRepository.existsById(dtoBloquearUsuario.getIdUsuarioBloqueado())) {
            throw new Exception("El usuario que se intenta bloquear no existe.");
        }

        // Una vez verificados se buscan los objetos de cuenta
        Optional<Cuenta> cuentaQueBloquea = cuentaRepository.findById(dtoBloquearUsuario.getIdUsuarioQueBloquea());

        Optional<Cuenta> cuentaBloqueada = cuentaRepository.findById(dtoBloquearUsuario.getIdUsuarioBloqueado());

        // Obtener id de usuario tanto de cuenta que bloquea como la cuenta que
        // bloqueada
        Long idUsuarioQueBloquea = cuentaQueBloquea.get().getId();
        Long idUsuarioBloqueado = cuentaBloqueada.get().getId();

        // Verificar si el usuario que se quiere bloquear ya ha sido bloqueado
        // previamente y sigue vigente el mismo.
        Bloqueos usuariosBloqueadosInversos = bloqueosRepository.encontrarBloqueosPorUsuarios(idUsuarioBloqueado,
                idUsuarioQueBloquea);
        if (usuariosBloqueadosInversos != null
                && usuariosBloqueadosInversos.getFechaHoraFinVigenciaBloqueoRealizado() == null) {
            throw new Exception("El usuario que quieres bloquear, ya te ha bloqueado.");
        }

        Bloqueos usuariosBloqueados = bloqueosRepository.encontrarBloqueosPorUsuarios(idUsuarioQueBloquea,
                idUsuarioBloqueado);

        // Verificar que no exista un bloqueo previo y vigente.
        if (usuariosBloqueados == null) {
            Bloqueos bloqueo = new Bloqueos(new Date(), null, cuentaQueBloquea.get(), cuentaBloqueada.get());
            bloqueosRepository.save(bloqueo);

            if (verificarSeguimiento(new DTOSeguirUsuario(idUsuarioQueBloquea, idUsuarioBloqueado))) {
                // Cuando lo bloqueo tambien lo dejo de seguir
                dejarDeSeguirUsuario(new DTOSeguirUsuario(idUsuarioQueBloquea, idUsuarioBloqueado));
            }
            if (verificarSeguimiento(new DTOSeguirUsuario(idUsuarioBloqueado, idUsuarioQueBloquea))) {
                // Cuando lo bloqueo el tambien me debe dejar de seguir
                dejarDeSeguirUsuario((new DTOSeguirUsuario(idUsuarioBloqueado, idUsuarioQueBloquea)));
            }
            // Verificar que si existe el bloqueo previo, pero ha sido desbloqueado el
            // usuario.
        } else if (usuariosBloqueados.getFechaHoraFinVigenciaBloqueoRealizado() != null) { // Si tiene fecha es porque
                                                                                           // se desbloqueo, se dio de
                                                                                           // baja al objeto
            // Se le elimina la fecha para que el objeto vuelva a estar vigente
            usuariosBloqueados.setFechaHoraFinVigenciaBloqueoRealizado(null);
            bloqueosRepository.save(usuariosBloqueados);

            if (verificarSeguimiento(new DTOSeguirUsuario(idUsuarioQueBloquea, idUsuarioBloqueado))) {
                // Cuando lo bloqueo tambien lo dejo de seguir
                dejarDeSeguirUsuario(new DTOSeguirUsuario(idUsuarioQueBloquea, idUsuarioBloqueado));
            }
            if (verificarSeguimiento(new DTOSeguirUsuario(idUsuarioBloqueado, idUsuarioQueBloquea))) {
                // Cuando lo bloqueo el tambien me debe dejar de seguir
                dejarDeSeguirUsuario((new DTOSeguirUsuario(idUsuarioBloqueado, idUsuarioQueBloquea)));
            }
        } else {
            throw new Exception("Ya has bloqueaste a éste usuario.");
        }
    }

    // Método para desbloquear a otra cuenta.
    @Override
    public void desbloquearCuenta(DTODesbloqueoUsuario dtoDesbloquearUsuario) throws Exception {
        // Verificar si se está intentado desbloquear a si mismo el usuario.
        if (dtoDesbloquearUsuario.getIdUsuarioQueDesbloquea() == dtoDesbloquearUsuario.getIdUsuarioDesbloqueado()) {
            throw new Exception("No se puede desbloquear a sí mismo.");
        }
        // Búsqueda de ambas cuentas (por id) recibidas por el DTOBloqueoUsuario.
        Optional<Cuenta> cuentaDesbloqueador = cuentaRepository
                .findById(dtoDesbloquearUsuario.getIdUsuarioQueDesbloquea());
        if (!cuentaDesbloqueador.isPresent()) {
            throw new Exception("El usuario desbloqueador no existe.");
        }
        Optional<Cuenta> cuentaDesbloqueado = cuentaRepository
                .findById(dtoDesbloquearUsuario.getIdUsuarioDesbloqueado());
        if (!cuentaDesbloqueado.isPresent()) {
            throw new Exception("El usuario que se intenta desbloquear no existe.");
        }
        // Verificar que existan ambas cuentas
        if (cuentaDesbloqueador.isPresent() && cuentaDesbloqueado.isPresent()) {
            Long idUsuarioQueBloquea = cuentaDesbloqueador.get().getId();
            Long idUsuarioBloqueado = cuentaDesbloqueado.get().getId();
            // Verificar si el usuario que se quiere bloquear ya ha sido bloqueado
            // previamente y sigue vigente el mismo.
            Bloqueos usuariosBloqueados = bloqueosRepository
                    .encontrarBloqueosPorUsuarios(
                            idUsuarioQueBloquea, idUsuarioBloqueado);
            // Verificar que si existe un bloqueo previo y vigente
            if (usuariosBloqueados != null && usuariosBloqueados.getFechaHoraFinVigenciaBloqueoRealizado() == null) {
                usuariosBloqueados.setFechaHoraFinVigenciaBloqueoRealizado(new Date());
                bloqueosRepository.save(usuariosBloqueados);
            } else {
                throw new Exception("El usuario no se encuentra bloqueado.");
            }
        } else {
            throw new Exception("Error al desbloquear.");
        }
    }

    // Método para verificar si otra cuenta está bloqueada.
    @Override
    public DTOVerificacion verificarBloqueo(DTOBloqueoUsuario dtoBloquearUsuario) throws Exception {
        // Verificar si se está intentado bloquear a si mismo el usuario.
        if (dtoBloquearUsuario.getIdUsuarioQueBloquea() == dtoBloquearUsuario.getIdUsuarioBloqueado()) {
            throw new Exception("No se puede verificar si se ha bloqueado a sí mismo.");
        }
        // Búsqueda de ambas cuentas (por id) recibidas por el DTOBloqueaUsuario
        Optional<Cuenta> cuentaQueBloquea = cuentaRepository.findById(dtoBloquearUsuario.getIdUsuarioQueBloquea());
        if (!cuentaQueBloquea.isPresent()) {
            throw new Exception("El usuario que quiere verificar el bloqueo a otro perfil no existe.");
        }
        Optional<Cuenta> cuentaBloqueada = cuentaRepository.findById(dtoBloquearUsuario.getIdUsuarioBloqueado());
        if (!cuentaBloqueada.isPresent()) {
            throw new Exception("El usuario que se intenta verificar si está bloqueado no existe.");
        }
        // Verificar que existan ambas cuentas
        if (cuentaQueBloquea.isPresent() && cuentaBloqueada.isPresent()) {
            Long idUsuarioQueBloquea = cuentaQueBloquea.get().getId();
            Long idUsuarioBloqueado = cuentaBloqueada.get().getId();

            Bloqueos usuariosBloqueados = bloqueosRepository
                    .encontrarBloqueosPorUsuarios(
                            idUsuarioQueBloquea, idUsuarioBloqueado);
            // Verificar que no exista un bloqueo previo y vigente
            if (usuariosBloqueados == null || usuariosBloqueados.getFechaHoraFinVigenciaBloqueoRealizado() != null) {
                return new DTOVerificacion("No está bloqueado", false);
            } else {
                return new DTOVerificacion("Si está bloqueado", true);
            }
        } else {
            throw new Exception("Error al verificar el bloqueo.");
        }
    }


    @Override
    public List<DTOUsuarioBloqueado> listaUsuariosBloqueados(Long idCuenta) throws Exception {
        List<Long> listaCuentasBloqueadas=cuentaRepository.listaIdUsuariosBloqueados(idCuenta);
        List<DTOUsuarioBloqueado> listaUsuarioBloqueados=new ArrayList<>();
        for (Long lista: listaCuentasBloqueadas) {
            listaUsuarioBloqueados.add(cuentaRepository.listaUsuariosBloqueados(lista));
        }
        return listaUsuarioBloqueados;
    }

    // Método para generar un link de referido.
    @Override
    public String referirUsuario(Long id) throws Exception {
        // Se verifica si existe la cuenta de usuario.
        Boolean cuentaExist = cuentaRepository.existsById(id);
        if (!cuentaExist) {
            throw new Exception("El id de la cuenta no existe.");
        }
        // Se realiza la encriptación del ID del usuario, la cuál es concatenada al link
        // de referido.
        String idEncriptado = EncryptionUtils.encrypt(id.toString());
        // Se crea el link de referido, concatenando el id del usuario
        // encriptada.
        String link = Functions.HOST + "registro?referido=" + idEncriptado; // -> reemplazar por pantalla de
                                                                            // front-end.

        return link;
    }

    // Método para seguir a otro usuario aprendiz.
    @Override
    public String seguirUsuario(DTOSeguirUsuario dtoSeguirUsuario) throws Exception {
        if (!cuentaRepository.existsById(dtoSeguirUsuario.getIdUsuarioSeguido())) {
            throw new Exception("El usuario que intenta seguir no existe.");
        }
        if (!cuentaRepository.existsById(dtoSeguirUsuario.getIdUsuarioSeguidor())) {
            throw new Exception("El usuario seguidor no existe.");
        }
        if (dtoSeguirUsuario.getIdUsuarioSeguido() == dtoSeguirUsuario.getIdUsuarioSeguidor()) {
            throw new Exception("No puedes seguirte.");
        }
        if (cuentaRepository.buscarUsuarioSeguido(dtoSeguirUsuario.getIdUsuarioSeguidor(),
                dtoSeguirUsuario.getIdUsuarioSeguido())) {
            throw new Exception("Ya sigues a éste usuario.");
        }
        // Agregar usuario a la lista de seguidos de la cuenta propia.
        Optional<Cuenta> cuentaSeguidor = cuentaRepository.findById(dtoSeguirUsuario.getIdUsuarioSeguidor());
        if (cuentaSeguidor.get().getCuentaEliminada() != null || cuentaSeguidor.get().getCuentaVerificada() == null) {
            throw new Exception("Su cuenta ha sido eliminada y/o no está verificada");
        }
        cuentaSeguidor.get().getListaSeguidos()
                .add(new Seguidos(null, new Date(), dtoSeguirUsuario.getIdUsuarioSeguido()));
        // Agregar mi usuario a la lista de seguidores de la cuenta a la que se siguió.
        Optional<Cuenta> cuentaSeguido = cuentaRepository.findById(dtoSeguirUsuario.getIdUsuarioSeguido());
        if (cuentaSeguido.get().getCuentaEliminada() != null || cuentaSeguido.get().getCuentaVerificada() == null) {
            throw new Exception("La cuenta que quiere seguir ha sido eliminada y/o no está verificada");
        }
        cuentaSeguido.get().getListaSeguidores()
                .add(new Seguidores(new Date(), null, dtoSeguirUsuario.getIdUsuarioSeguidor()));

        // Guardar el usuario seguido.
        cuentaRepository.save(cuentaSeguidor.get());
        // Guardar el usuario seguidor.
        cuentaRepository.save(cuentaSeguido.get());
        return "Usuario seguido correctamente.";
    }

    // Método para dejar de seguir a otro usuario aprendiz.
    @Override
    public String dejarDeSeguirUsuario(DTOSeguirUsuario dtoSeguirUsuario) throws Exception {
        if (!cuentaRepository.existsById(dtoSeguirUsuario.getIdUsuarioSeguido())) {
            throw new Exception("El usuario que intenta dejar de seguir no existe.");
        }
        if (!cuentaRepository.existsById(dtoSeguirUsuario.getIdUsuarioSeguidor())) {
            throw new Exception("El usuario seguidor no existe.");
        }
        if (dtoSeguirUsuario.getIdUsuarioSeguido() == dtoSeguirUsuario.getIdUsuarioSeguidor()) {
            throw new Exception("No puedes dejar de seguir a tu propio usuario.");
        }
        if (!cuentaRepository.buscarUsuarioSeguido(dtoSeguirUsuario.getIdUsuarioSeguidor(),
                dtoSeguirUsuario.getIdUsuarioSeguido())) {
            throw new Exception("No sigues a éste usuario.");
        }
        // Se pone una fechaHoraFinVigencia en el objeto seguido.
        Seguidos seguido = cuentaRepository
                .buscarUsuarioSeguidoId(dtoSeguirUsuario.getIdUsuarioSeguidor(), dtoSeguirUsuario.getIdUsuarioSeguido())
                .get();
        seguido.setFechaHoraFinVigenciaSeguido(new Date());

        // Se pone una fechaHoraFinVigencia en el objeto seguidor del usuario que se
        // dejó de seguir.
        Seguidores seguidor = cuentaRepository.buscarUsuarioSeguidorId(dtoSeguirUsuario.getIdUsuarioSeguido(),
                dtoSeguirUsuario.getIdUsuarioSeguidor()).get();
        seguidor.setFechaHoraFinVigenciaSeguidor(new Date());

        seguidosRepository.save(seguido);
        seguidoresRepository.save(seguidor);
        return "Dejó de seguir correctamente a éste usuario.";

    }

    // Método para verificar el seguimiento entre ambos usuarios.
    @Override
    public boolean verificarSeguimiento(DTOSeguirUsuario dtoSeguirUsuario) throws Exception {
        if (!cuentaRepository.existsById(dtoSeguirUsuario.getIdUsuarioSeguido())) {
            throw new Exception("El usuario seguido no existe.");
        }
        if (!cuentaRepository.existsById(dtoSeguirUsuario.getIdUsuarioSeguidor())) {
            throw new Exception("El usuario seguidor no existe.");
        }
        if (dtoSeguirUsuario.getIdUsuarioSeguido() == dtoSeguirUsuario.getIdUsuarioSeguidor()) {
            throw new Exception("No puedes verificar el seguimiento de tu propio usuario.");
        }
        return cuentaRepository.verificarSeguimiento(dtoSeguirUsuario.getIdUsuarioSeguidor(),
                dtoSeguirUsuario.getIdUsuarioSeguido());
    }

    // Método para listar los usuarios seguidos del aprendiz.
    @Override
    public List<DTOListarSeguidosRepository> listarSeguidos(Long idCuenta) throws Exception {
        if (!cuentaRepository.existsById(idCuenta)) {
            throw new Exception("La cuenta no existe.");
        }
        List<Long> listaIdSeguidos = cuentaRepository.listarIdSeguidos(idCuenta);
        List<DTOListarSeguidosRepository> listaSeguidos = new ArrayList<>();
        for (Long recorrerId : listaIdSeguidos) {
            if (cuentaRepository.estaEliminadaLaCuenta(recorrerId).isPresent()) {
                listaSeguidos.add(cuentaRepository.listaSeguidos(recorrerId));
            }
        }
        return listaSeguidos;
    }

    // Método para listar los usuarios que siguen al aprendiz.
    @Override
    public List<DTOListarSeguidosRepository> listarSeguidores(Long idCuenta) throws Exception {
        if (!cuentaRepository.existsById(idCuenta)) {
            throw new Exception("El usuario no existe.");
        }
        List<Long> listaIdSeguidores = cuentaRepository.listarIdSeguidores(idCuenta);
        List<DTOListarSeguidosRepository> listaSeguidores = new ArrayList<>();
        for (Long recorrerId : listaIdSeguidores) {
            if (cuentaRepository.estaEliminadaLaCuenta(recorrerId).isPresent()) {
                listaSeguidores.add(cuentaRepository.listaSeguidores(recorrerId));
            }
        }
        return listaSeguidores;
    }

    // Método para reportar el comportamiento de otro usuario aprendiz.
    @Override
    public String reportarUsuario(DTOReportarUsuario dtoReportarUsuario) throws Exception {
        if (!cuentaRepository.existsById(dtoReportarUsuario.getIdCuentaInformanteMotivo())) {
            throw new Exception("La cuenta que reporta no existe.");
        }
        if (!cuentaRepository.existsById(dtoReportarUsuario.getIdCuentaReportado())) {
            throw new Exception("La cuenta a la que quiere reportar no existe.");
        }
        if (dtoReportarUsuario.getDescripcionReporteMotivo().length() > 350) {
            throw new Exception("La descripción no puede ser mayor a 350 caracteres.");
        }
        if(dtoReportarUsuario.getNombreMotivo().isEmpty()){
            throw new Exception("Debe seleccionar como mínimo un motivo.");
        }
        for (String nombreMotivo : dtoReportarUsuario.getNombreMotivo()) {
         Optional<Motivo> nombreMotivoRecuperado = motivoRepository.findByNombreMotivo(nombreMotivo); 
         if(!nombreMotivoRecuperado.isPresent()){
            throw new Exception("El nombre motivo seleccionado no existe.");
         }
            ReporteMotivo reporteMotivo = new ReporteMotivo(
                dtoReportarUsuario.getDescripcionReporteMotivo(),
                new Date(),
                null,
                dtoReportarUsuario.getIdCuentaInformanteMotivo(),
                dtoReportarUsuario.getIdCuentaReportado(),
                nombreMotivoRecuperado.get());

        reporteMotivoRepository.save(reporteMotivo);
        }
        return "Usuario reportado correctamente.";
        
    }

    @Override
    public String obtenerIdiomaAprendiz(Long idCuenta) throws Exception {
        if (!cuentaRepository.existsById(idCuenta)) {
            throw new Exception("La cuenta no existe.");
        }
        Optional<String> idiomaAprendiz = cuentaRepository.obtenerIdiomaAprendiz(idCuenta);
        if (!idiomaAprendiz.isPresent()) {
            throw new Exception("El idioma aprendiz no existe");
        }
        return idiomaAprendiz.get();
    }

    @Override
    public DTOUsuarioChat obtenerUsuarioChat(Long idCuenta) throws Exception {
        if (!cuentaRepository.existsById(idCuenta)) {
            throw new Exception("La cuenta no existe.");
        }
        Optional<DTOUsuarioChat> dtoUsuarioChat = cuentaRepository.obtenerUsuarioChat(idCuenta);
        if (!dtoUsuarioChat.isPresent()) {
            throw new Exception("No se ha encontrado la información.");
        }
        return dtoUsuarioChat.get();
    }

    @Override
    public DTOVisualizarEstadisticasAprendiz visualizarEstadisticasAprendiz(Long idCuenta) throws Exception {

        /* ESTADÍSTICA DE CANTIDAD DE PUNTOS PARA ÚLTIMOS 7 DIAS */

        // Obtenemos la fecha y hora actual en la zona horaria de
        // "America/Argentina/Buenos_Aires"
        LocalDate fechaActual = LocalDate.now(ZoneId.of("America/Argentina/Buenos_Aires"));

        // Obtener la fecha hace 7 días desde la fecha actual
        LocalDate fechaHaceSieteDias = fechaActual.minusDays(6);

        // Crear un mapa para almacenar las puntuaciones por fecha con todas las fechas
        // en el intervalo de los últimos 7 días
        Map<LocalDate, Integer> puntuacionesPorFecha = new HashMap<>();
        LocalDate fechaRecorrida = fechaHaceSieteDias;
        while (!fechaRecorrida.isAfter(fechaActual)) {
            puntuacionesPorFecha.put(fechaRecorrida, 0);
            fechaRecorrida = fechaRecorrida.plusDays(1);
        }

        // Obtener todas las puntuaciones del usuario en la zona horaria de
        // "America/Argentina/Buenos_Aires"
        List<Puntuacion> puntos = cuentaRepository.obtenerCantidadDePuntosUltimosSieteDias(idCuenta);

        // Recorrer las puntuaciones y sumar los puntos por fecha
        for (Puntuacion puntuacion : puntos) {
            LocalDate fechaPuntuacion = puntuacion.getFechaPuntuacion().toInstant()
                    .atZone(ZoneId.of("America/Argentina/Buenos_Aires")).toLocalDate();

            // Verificar si la fecha está dentro del intervalo de los últimos 7 días
            if (!fechaPuntuacion.isBefore(fechaHaceSieteDias) && !fechaPuntuacion.isAfter(fechaActual)) {
                puntuacionesPorFecha.put(fechaPuntuacion,
                        puntuacionesPorFecha.getOrDefault(fechaPuntuacion, 0) + puntuacion.getCantidadPuntos());
            }
        }
        /* FINALIZA ESTADÍSTICA DE CANTIDAD DE PUNTOS PARA ÚLTIMOS 7 DIAS */
        // Convert the dates to the desired format
        Map<String, Integer> puntuacionesPorFechaFormatted = new TreeMap<>();
        for (Map.Entry<LocalDate, Integer> entry : puntuacionesPorFecha.entrySet()) {
            puntuacionesPorFechaFormatted.put(
                    String.format("%02d/%02d", entry.getKey().getDayOfMonth(), entry.getKey().getMonthValue()),
                    entry.getValue()
            );
        }
        // Instanciar de DTO para cada una de las estadísticas.
        DTOVisualizarEstadisticasAprendiz dto = new DTOVisualizarEstadisticasAprendiz();
        dto.setCantidadPuntos(cuentaRepository.obtenerCantidadPuntos(idCuenta));
        dto.setCantidadInteraccionesPaisesDif(cuentaRepository.obtenerCantidadInteraccionesPaisesDif(idCuenta));
        dto.setCantidadInteraccionesUsuariosDif(cuentaRepository.obtenerCantidadInteraccionesUsuariosDif(idCuenta));
        // Calculo de promedio de calificaciones
        List<Long> listaCalificacion = cuentaRepository
                .obtenerCalificacionUsuario(idCuenta);
        int promedio = Functions.promedioCalificacion(listaCalificacion);
        dto.setPromedioCalificaciones(promedio);
        
        double tiempoVideollamada = Functions
                .cortarADosDecimales(cuentaRepository.obtenerTiempoMaximoVideollamada(idCuenta));
        dto.setTiempoVideollamada(tiempoVideollamada);
        dto.setPuntuacionesPorFecha(puntuacionesPorFechaFormatted);
        
        return dto;
    }

    @Override
    public String puntuarUsuario(int cantidadPuntos, long idCuenta) {
        Optional<Long> idUsuario = cuentaRepository.obtenerIdUsuario(idCuenta);
        Optional<Usuario> usuario = usuarioRepository.findById(idUsuario.get());
        Puntuacion puntuacion = new Puntuacion(cantidadPuntos, new Date());
        usuario.get().getUsuarioPuntuacion().getListaPuntuacion().add(puntuacion);
        usuarioRepository.save(usuario.get());
        return "Puntuado con éxito.";
    }

    @Override
    public DTODatosPersonales obtenerDatosPersonales(Long idCuenta) throws Exception {
       /* if (!cuentaRepository.existsById(idCuenta)) {
            throw new Exception("La cuenta no existe");
        }
        DTODatosPersonales dtoDatosPersonales = cuentaRepository.obtenerDatosPersonales(idCuenta);
        List<DTOInteres> dtoInteres = cuentaRepository.obtenerIntereses(idCuenta);
        for (DTOInteres recorrerDTO : dtoInteres) {
            DTOInteres dtoInteresSet = new DTOInteres(recorrerDTO.getId(), recorrerDTO.getName(),
                    recorrerDTO.getIcon(), true);
            dtoDatosPersonales.getListaIntereses().add(dtoInteresSet);
        }
        return dtoDatosPersonales;
        */
        Optional<Long> idUsuario = cuentaRepository.obtenerIdUsuario(idCuenta);
        if (!idUsuario.isPresent()) {
            throw new Exception("No existe la cuenta");
        }

        // Se realiza la búsqueda del usuario que se va a visitar el perfil.
        Optional<Usuario> usuario = usuarioRepository.findById(idUsuario.get());

        // Se instancia un objeto DTOPerfilOtroUsuario en donde se almacenan todos los
        // datos que serán mostrados en el perfil del otro usuario aprendiz.
        DTODatosPersonales dtoDatosPersonales = new DTODatosPersonales();

        dtoDatosPersonales.setId(usuario.get().getCuenta().getId());
        dtoDatosPersonales.setCorreo(usuario.get().getCuenta().getCorreo());
        dtoDatosPersonales.setNombreUsuario(usuario.get().getNombreUsuario());
        dtoDatosPersonales.setApellidoUsuario(usuario.get().getApellidoUsuario());
        dtoDatosPersonales.setDescripcion(usuario.get().getDescripcionUsuario());
        dtoDatosPersonales.setFechaNacimiento(usuario.get().getFechaNacimiento());
        dtoDatosPersonales.setUrlFoto(usuario.get().getCuenta().getUrlFoto());

        if (usuario.get().getIdiomaAprendiz().getIdioma() != null) {
            dtoDatosPersonales.setNombreIdiomaAprender(usuario.get().getIdiomaAprendiz().getIdioma().getNombreIdioma());
        } else {
            dtoDatosPersonales.setNombreIdiomaAprender("");
        }

        if (usuario.get().getIdiomaAprendiz().getNivelIdioma() != null) {
            dtoDatosPersonales.setNombreNivelIdioma(usuario.get().getIdiomaAprendiz().getNivelIdioma().getNombreNivelIdioma());
        } else {
            dtoDatosPersonales.setNombreNivelIdioma("");
        }

        if (usuario.get().getIdiomaNativo().getIdioma() != null) {
            dtoDatosPersonales.setNombreIdiomaNativo(usuario.get().getIdiomaNativo().getIdioma().getNombreIdioma());
        } else {
            dtoDatosPersonales.setNombreIdiomaNativo("");
        }

        if (usuario.get().getPais() != null) {
            dtoDatosPersonales.setNombrePais(usuario.get().getPais().getNombrePais());

        } else {
            dtoDatosPersonales.setNombrePais("");
        }

        List<DTOInteres> dtoInteres = cuentaRepository.obtenerIntereses(idCuenta);
        for (DTOInteres recorrerDTO : dtoInteres) {
            DTOInteres dtoInteresSet = new DTOInteres(recorrerDTO.getId(), recorrerDTO.getName(),
                    recorrerDTO.getIcon(), true);
            dtoDatosPersonales.getListaIntereses().add(dtoInteresSet);
        }

        return dtoDatosPersonales;

    }

    @Override
    public DTOCantidadSeguidosYSeguidores obtenercantidadSeguidosSeguidores(Long idCuenta) throws Exception {
        if (!cuentaRepository.existsById(idCuenta)) {
            throw new Exception("La cuenta no existe");
        }
        Long cantidadSeguidos = cuentaRepository.obtenerCantidadSeguidos(idCuenta);
        Long cantidadSeguidores = cuentaRepository.obtenerCantidadSeguidores(idCuenta);
        DTOCantidadSeguidosYSeguidores dtoCantidadSeguidosYSeguidores = new DTOCantidadSeguidosYSeguidores(
                cantidadSeguidos, cantidadSeguidores);
        return dtoCantidadSeguidosYSeguidores;
    }
    // Método para actualizar la última conexión del usuario aprendiz.
    @Override
    public void actualizarUltimaConexion(Long idCuenta) throws Exception {
        Optional<Cuenta> cuenta = cuentaRepository.findById(idCuenta);
            cuenta.get().setUltimaConexion(new Date());
            cuentaRepository.save(cuenta.get());
           
    }
}
