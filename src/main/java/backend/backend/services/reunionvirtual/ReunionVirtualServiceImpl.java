package backend.backend.services.reunionvirtual;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import backend.backend.DTO.DTOPrevioCalificarUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.backend.DTO.DTOCrearSala;
import backend.backend.DTO.DTOListarReunionesVirtuales;
import backend.backend.DTORepository.DTOListarReunionesVirtualesRepository;
import backend.backend.entities.Bloqueos;
import backend.backend.entities.DetalleParticipante;
import backend.backend.entities.PuntosPorActividad;
import backend.backend.entities.Puntuacion;
import backend.backend.entities.ReunionVirtual;
import backend.backend.entities.Usuario;
import backend.backend.repositories.BloqueosRepository;
import backend.backend.repositories.CuentaRepository;
import backend.backend.repositories.DetalleParticipanteRepository;
import backend.backend.repositories.PuntosPorActividadRepository;
import backend.backend.repositories.ReunionVirtualRepository;
import backend.backend.repositories.UsuarioRepository;
import backend.backend.utils.functions.Functions;

@Service
public class ReunionVirtualServiceImpl implements ReunionVirtualService {

    @Autowired
    private ReunionVirtualRepository reunionVirtualRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private DetalleParticipanteRepository detalleParticipanteRepository;

    @Autowired
    private BloqueosRepository bloqueosRepository;
    
    @Autowired
    private PuntosPorActividadRepository puntosPorActividadRepository;

    // Se guarda el link que concatenaremos para crear una sala
    private String host = "https://meet.jit.si/"; // -> reemplazar por nuestro propio link de host

    // Método encargado de generar una reunión virtual y devolver el link de la misma.
    @Override
    public DTOCrearSala crearSala(Long idCuenta) throws Exception {
        // Verificar si la cuenta existe.
        if (!cuentaRepository.existsById(idCuenta)) {
            throw new Exception("La cuenta no existe.");
        }
        // Obtener id del usuario a partir del id de la cuenta.
        Optional<Long> idUsuario = cuentaRepository.obtenerIdUsuario(idCuenta);
        if (!idUsuario.isPresent()) {
            throw new Exception("La cuenta no se encuentra verificada y/o está eliminada.");
        }
        // Validar si el usuario que quiere crear sala existe
        Optional<Usuario> usuario = usuarioRepository.findById(idUsuario.get());
        if (!usuario.isPresent()) {
            throw new Exception("El usuario que quiere crear la sala, no existe.");
        }
        // Validad que no se haya unido a otra sala, o tenga una sala activa y quiera
        // crear otra sala.
        Optional<DetalleParticipante> detalleParticipanteVerificar = detalleParticipanteRepository
                .encontrarUsuarioDetalleParticipanteNoNulo(usuario.get().getId());
        if (detalleParticipanteVerificar.isPresent()) {
            throw new Exception("Ya te encuentras en una reunión virtual.");
        }
        
        // Generar link aleatorio y verificar si existe previamente el mismo
        String linkReunionVirtual = UUID.randomUUID().toString();
        Optional<ReunionVirtual> linkReunion = reunionVirtualRepository
                .findByLinkReunionVirtualAndFechaHoraFinVigenciaReunionVirtualIsNull(linkReunionVirtual);
        
        //En el caso de que ya exista el mismo link, se genera otro de manera aleatoria.
        if (linkReunion.isPresent()) {
            linkReunionVirtual = UUID.randomUUID().toString();
        }

        // Se instancia un objeto detalle participante para almacenar QUIEN y CUANDO se creó la sala.
        List<DetalleParticipante> listaDetalleParticipante = new ArrayList<>();
        DetalleParticipante detalleParticipante = new DetalleParticipante(
        null, new Date(), usuario.get());
        listaDetalleParticipante.add(detalleParticipante);

        // Se crea una reunion virtual
        ReunionVirtual reunionVirtual = new ReunionVirtual(0, new Date(), 
        null, linkReunionVirtual,listaDetalleParticipante);

        // Se guarda la reunion virtual
        reunionVirtualRepository.save(reunionVirtual);

        // Se instancia un objeto de DTOCrearSala para retornar el link de la reunión virtual.
        DTOCrearSala dtoCrearSala = new DTOCrearSala(linkReunionVirtual);
        return dtoCrearSala;
    }

    // Corregido 13/7/23.
    @Override
    public String unirseASala(String linkReunion, Long idSegundoParticipante) throws Exception {
        // Verificar si la cuenta existe.
        if (!cuentaRepository.existsById(idSegundoParticipante)) {
            throw new Exception("La cuenta no existe.");
        }
        // Obtener id del usuario a partir del id de la cuenta.
        Optional<Long> idUsuario = cuentaRepository.obtenerIdUsuario(idSegundoParticipante);
        if (!idUsuario.isPresent()) {
            throw new Exception("La cuenta no se encuentra verificada y/o está eliminada.");
        }
        // Validar si el usuario que quiere crear sala existe
        Optional<Usuario> usuario = usuarioRepository.findById(idUsuario.get());
        if (!usuario.isPresent()) {
            throw new Exception("El usuario que quiere unirse a la sala no existe.");
        }
        // Validar que no se haya unido a otra sala o tenga una sala creada activa.
        Optional<DetalleParticipante> detalleParticipanteVerificar = detalleParticipanteRepository
                .encontrarUsuarioDetalleParticipanteNoNulo(usuario.get().getId());
        if (detalleParticipanteVerificar.isPresent()) {
            throw new Exception("Ya te encuentras en una reunión virtual.");
        }

        Optional<ReunionVirtual> reunionVirtual = reunionVirtualRepository
                .findByLinkReunionVirtualAndFechaHoraFinVigenciaReunionVirtualIsNull(linkReunion);
        if (!reunionVirtual.isPresent()) {
            throw new Exception("La sala a la que intentas unirte no existe.");
        }
        if (reunionVirtual.isPresent() && reunionVirtual.get().getListaDetalleParticipantes().size() >= 2) {
            throw new Exception("La sala se encuentra llena.");
        }
        DetalleParticipante detalleParticipante = new DetalleParticipante(null, new Date(), usuario.get());
        reunionVirtual.get().getListaDetalleParticipantes().add(detalleParticipante);
        reunionVirtualRepository.save(reunionVirtual.get());
        return "Se unió correctamente a la sala.";
    }
   
    @Override
    public String finalizarVideollamada(String linkReunion) throws Exception {
        Optional<ReunionVirtual> reunionVirtual = reunionVirtualRepository
                .findByLinkReunionVirtualAndFechaHoraFinVigenciaReunionVirtualIsNull(linkReunion);
        if (!reunionVirtual.isPresent()) {
            throw new Exception("No puedes finalizar una reunión que no existe.");
        }
        for (DetalleParticipante detalleParticipante : reunionVirtual.get().getListaDetalleParticipantes()) {
            detalleParticipante.setFechaHoraFinDetalleParticipante(new Date());
        }
        reunionVirtual.get().setFechaHoraFinVigenciaReunionVirtual(new Date());
        if (reunionVirtual.get().getListaDetalleParticipantes().size() == 1) {
            reunionVirtual.get().setDuracionReunionVirtual(0);
        } else {
            Double minutosLlamada = 0.0;
            Date finReunion = reunionVirtual.get().getFechaHoraFinVigenciaReunionVirtual();
            Date inicioReunion = reunionVirtual.get().getListaDetalleParticipantes().get(1)
                    .getFechaHoraInicioDetalleParticipante();
            long duracionEnMilisegundos = finReunion.getTime() - inicioReunion.getTime();
            minutosLlamada = duracionEnMilisegundos / 60000.0;
            reunionVirtual.get().setDuracionReunionVirtual(minutosLlamada);



            Optional<PuntosPorActividad> puntosVideollamada=puntosPorActividadRepository.findById(1l);
            //Calculo de puntos obtenidos en la videollamada
            Double calculoPuntosConDecimal=minutosLlamada*puntosVideollamada.get().getPuntosPorActividad();
            //Conversion a numero entero, redondeado
            BigDecimal decimalValue = BigDecimal.valueOf(calculoPuntosConDecimal);
            int calculoPuntosEntero = decimalValue.setScale(0, RoundingMode.HALF_UP).intValue();
            System.out.println("Los puntos que deben sumar son: "+calculoPuntosEntero);
            //Creacion de objeto puntuacion
            Puntuacion puntuacion=new Puntuacion(calculoPuntosEntero,new Date());
            //Asignacion de objeto puntos por actividad a puntuacion
            //Esto no esta diseñado en la clase
            //puntuacion.setPuntosPorActividad(puntosVideollamada);

            for (DetalleParticipante detalleParticipante: reunionVirtual.get().getListaDetalleParticipantes()
                 ) {
                Optional<Usuario> usuario=usuarioRepository.findById(detalleParticipante.getParticipante().getId());
                int puntosTotales=usuario.get().getUsuarioPuntuacion().getPuntosTotales();
                usuario.get().getUsuarioPuntuacion().setPuntosTotales(puntosTotales+calculoPuntosEntero);
                usuario.get().getUsuarioPuntuacion().getListaPuntuacion().add(puntuacion);
                usuarioRepository.save(usuario.get());
            }



        }
        reunionVirtualRepository.save(reunionVirtual.get());
        return "Videollamada finalizada correctamente.";
    }

    @Override
    public List<DTOListarReunionesVirtuales> listarSalasActivas(Long idCuenta) throws Exception {
        Optional<Long> idUsuario=cuentaRepository.obtenerIdUsuario(idCuenta);
        String idiomaAprenderUsuario=usuarioRepository.obtenerIdiomaAprenderUsuario(idUsuario.get());
        String idiomaNativoUsuario=usuarioRepository.obtenerIdiomaNativoUsuario(idUsuario.get());


        System.out.println(idiomaAprenderUsuario);
        System.out.println(idiomaNativoUsuario);


        List<DTOListarReunionesVirtualesRepository> salasActivas = reunionVirtualRepository
                .listarReunionesVirtualesActivas(idiomaAprenderUsuario,idiomaNativoUsuario);
        Map<Long, DTOListarReunionesVirtuales> mapaDTOListarReunionesVirtuales = new HashMap<>();
        List<Bloqueos> listaBloqueos = bloqueosRepository.listaCuentasBloqueadas(idCuenta);
        Set<Long> idCuentasBloqueadas = new HashSet<>();
        for (Bloqueos bloqueo : listaBloqueos) {
            idCuentasBloqueadas.add(bloqueo.getUsuarioBloqueado().getId());
            idCuentasBloqueadas.add(bloqueo.getUsuarioQueBloquea().getId());
        }

        for (DTOListarReunionesVirtualesRepository salasActivasRecorrer : salasActivas) {
            if (!(idCuentasBloqueadas.contains(salasActivasRecorrer.getIdCuenta()))) {
                if (salasActivasRecorrer.getIdCuenta() != idCuenta) {
                    Long idReunionVirtual = salasActivasRecorrer.getId();
                    DTOListarReunionesVirtuales dtoListarReunionesVirtuales;

                    if (mapaDTOListarReunionesVirtuales.containsKey(idReunionVirtual)) {
                        dtoListarReunionesVirtuales = mapaDTOListarReunionesVirtuales.get(idReunionVirtual);
                    } else {
                        dtoListarReunionesVirtuales = new DTOListarReunionesVirtuales();
                        dtoListarReunionesVirtuales.setIdReunionVirtual(idReunionVirtual);
                        dtoListarReunionesVirtuales.setIdCuenta(salasActivasRecorrer.getIdCuenta());
                        dtoListarReunionesVirtuales.setLinkReunionVirtual(salasActivasRecorrer.getLinkReunionVirtual());
                        dtoListarReunionesVirtuales.setNombreUsuario(salasActivasRecorrer.getNombreUsuario());
                        dtoListarReunionesVirtuales
                                .setEdad(Functions.calcularEdad(salasActivasRecorrer.getFechaNacimiento()));
                        dtoListarReunionesVirtuales.setUrlBandera(salasActivasRecorrer.getUrlBandera());
                        dtoListarReunionesVirtuales.setUrlFoto(salasActivasRecorrer.getUrlFoto());
                        List<Long> listaCalificacion = cuentaRepository
                                .obtenerCalificacionUsuario(salasActivasRecorrer.getIdCuenta());
                        dtoListarReunionesVirtuales
                                .setCantidadEstrellas(Functions.promedioCalificacion(listaCalificacion));
                        dtoListarReunionesVirtuales.setNombrePais(salasActivasRecorrer.getNombrePais());
                        dtoListarReunionesVirtuales.setNombreNivelIdioma(salasActivasRecorrer.getNombreNivelIdioma());
                    }

                    dtoListarReunionesVirtuales.getIntereses().add(salasActivasRecorrer.getNombreInteres());
                    mapaDTOListarReunionesVirtuales.put(idReunionVirtual, dtoListarReunionesVirtuales);
                }
            }
        }
        return new ArrayList<>(mapaDTOListarReunionesVirtuales.values());
    }

    @Override
    public List<DTOListarReunionesVirtuales> listarSalasActivasFiltradas(Long idCuenta,
            String edadMinima,
            String edadMaxima,
            List<String> intereses,
            String nombreNivelIdioma,
            String nombrePais)
            throws Exception {

        Optional<Long> idUsuario=cuentaRepository.obtenerIdUsuario(idCuenta);
        String idiomaAprenderUsuario=usuarioRepository.obtenerIdiomaAprenderUsuario(idUsuario.get());
        String idiomaNativoUsuario=usuarioRepository.obtenerIdiomaNativoUsuario(idUsuario.get());


        System.out.println(idiomaAprenderUsuario);
        System.out.println(idiomaNativoUsuario);

        List<DTOListarReunionesVirtualesRepository> salasActivas = reunionVirtualRepository
                .listarReunionesVirtualesActivas(idiomaAprenderUsuario,idiomaNativoUsuario);
        Map<Long, DTOListarReunionesVirtuales> mapaDTOListarReunionesVirtuales = new HashMap<>();
        List<Bloqueos> listaBloqueos = bloqueosRepository.listaCuentasBloqueadas(idCuenta);
        Set<Long> idCuentasBloqueadas = new HashSet<>();
        for (Bloqueos bloqueo : listaBloqueos) {
            idCuentasBloqueadas.add(bloqueo.getUsuarioBloqueado().getId());
            idCuentasBloqueadas.add(bloqueo.getUsuarioQueBloquea().getId());
        }
        for (DTOListarReunionesVirtualesRepository salaActiva : salasActivas) {
            if (!(idCuentasBloqueadas.contains(salaActiva.getIdCuenta()))) {
                if (salaActiva.getIdCuenta() != idCuenta) {
                    Long idReunionVirtual = salaActiva.getId();
                    DTOListarReunionesVirtuales dtoListarReunionesVirtuales;

                    if (mapaDTOListarReunionesVirtuales.containsKey(idReunionVirtual)) {
                        dtoListarReunionesVirtuales = mapaDTOListarReunionesVirtuales.get(idReunionVirtual);
                    } else {
                        dtoListarReunionesVirtuales = new DTOListarReunionesVirtuales();
                        dtoListarReunionesVirtuales.setIdReunionVirtual(idReunionVirtual);
                        dtoListarReunionesVirtuales.setIdCuenta(salaActiva.getIdCuenta());
                        dtoListarReunionesVirtuales.setLinkReunionVirtual(salaActiva.getLinkReunionVirtual());
                        dtoListarReunionesVirtuales.setNombreUsuario(salaActiva.getNombreUsuario());
                        dtoListarReunionesVirtuales.setEdad(Functions.calcularEdad(salaActiva.getFechaNacimiento()));
                        dtoListarReunionesVirtuales.setUrlBandera(salaActiva.getUrlBandera());
                        dtoListarReunionesVirtuales.setUrlFoto(salaActiva.getUrlFoto());
                        List<Long> listaCalificacion = cuentaRepository
                                .obtenerCalificacionUsuario(salaActiva.getIdCuenta());
                        dtoListarReunionesVirtuales
                                .setCantidadEstrellas(Functions.promedioCalificacion(listaCalificacion));
                        dtoListarReunionesVirtuales.setNombrePais(salaActiva.getNombrePais());
                        dtoListarReunionesVirtuales.setNombreNivelIdioma(salaActiva.getNombreNivelIdioma());
                    }

                    dtoListarReunionesVirtuales.getIntereses().add(salaActiva.getNombreInteres());
                    mapaDTOListarReunionesVirtuales.put(idReunionVirtual, dtoListarReunionesVirtuales);
                }
            }
        }
        List<DTOListarReunionesVirtuales> salasFiltradas = new ArrayList<>();

        for (DTOListarReunionesVirtuales dtoListarReunionesVirtuales : mapaDTOListarReunionesVirtuales.values()) {
            boolean interesesValidos = intereses.isEmpty()
                    || dtoListarReunionesVirtuales.getIntereses().containsAll(intereses);
            boolean edadValida = (edadMinima.equals("")
                    || dtoListarReunionesVirtuales.getEdad() >= Integer.parseInt(edadMinima))
                    && (edadMaxima.equals("")
                            || dtoListarReunionesVirtuales.getEdad() <= Integer.parseInt(edadMaxima));
            boolean paisValido = nombrePais.equals("")
                    || dtoListarReunionesVirtuales.getNombrePais().equals(nombrePais);
            boolean nombreNivelIdiomaValido = nombreNivelIdioma.equals("")
                    || dtoListarReunionesVirtuales.getNombreNivelIdioma().equals(nombreNivelIdioma);
            if (interesesValidos && edadValida && paisValido && nombreNivelIdiomaValido) {
                salasFiltradas.add(dtoListarReunionesVirtuales);
            }
        }
        return salasFiltradas;
    }

    @Override
    public DTOPrevioCalificarUsuario dtoPrevioCalificarUsuario(Long idCuenta, String url) throws Exception {
        //Verificar existencia de cuenta
        if(!cuentaRepository.existsById(idCuenta)){
            throw new Exception("El usuario ingresado no existe");
        }
        //Obtener reunion a partir de la url
        List<DTOPrevioCalificarUsuario> dtoPrevioCalificarUsuario=reunionVirtualRepository.obtenerDatosParaCalificar(url);
        if(dtoPrevioCalificarUsuario.isEmpty()){
            throw new Exception("Nadie se unio a la reunion o nunca se creo esta reunion");
        }
        //Eliminar usuario que se paso por parametro para que quede el otro participante
        DTOPrevioCalificarUsuario usuarioACalificar=new DTOPrevioCalificarUsuario();
        List<Long> idParticipantes=new ArrayList<Long>();
        for (DTOPrevioCalificarUsuario dto: dtoPrevioCalificarUsuario) {
            idParticipantes.add(dto.getIdParticipanteDos());
            if(dto.getIdParticipanteDos()!=idCuenta){
                usuarioACalificar=dto;
            }
        }
        if(!idParticipantes.contains(idCuenta)){
            throw new Exception("La cuenta no participo de la reunion mencionada");
        }
        return usuarioACalificar;
    }

    @Override
    public String finalizarvideollamadaPorRefrescar(long idCuenta) throws Exception {

        Optional<Long> idReunion= reunionVirtualRepository.obtenerIdReunionVirtualActivaPorIdCuenta(idCuenta);
        if(!idReunion.isPresent()){
            throw new Exception("No existe una reunion activa para esta cuenta");
        }

        Optional<ReunionVirtual> reunionVirtual = reunionVirtualRepository.findById(idReunion.get());

        if (!reunionVirtual.isPresent()) {
            throw new Exception("No puedes finalizar una reunión que no existe.");
        }
        for (DetalleParticipante detalleParticipante : reunionVirtual.get().getListaDetalleParticipantes()) {
            detalleParticipante.setFechaHoraFinDetalleParticipante(new Date());
        }
        reunionVirtual.get().setFechaHoraFinVigenciaReunionVirtual(new Date());
        if (reunionVirtual.get().getListaDetalleParticipantes().size() == 1) {
            reunionVirtual.get().setDuracionReunionVirtual(0);
        } else {
            Double minutosLlamada = 0.0;
            Date finReunion = reunionVirtual.get().getFechaHoraFinVigenciaReunionVirtual();
            Date inicioReunion = reunionVirtual.get().getListaDetalleParticipantes().get(1)
                    .getFechaHoraInicioDetalleParticipante();
            long duracionEnMilisegundos = finReunion.getTime() - inicioReunion.getTime();
            minutosLlamada = duracionEnMilisegundos / 60000.0;
            reunionVirtual.get().setDuracionReunionVirtual(minutosLlamada);



            Optional<PuntosPorActividad> puntosVideollamada=puntosPorActividadRepository.findById(1l);
            //Calculo de puntos obtenidos en la videollamada
            Double calculoPuntosConDecimal=minutosLlamada*puntosVideollamada.get().getPuntosPorActividad();
            //Conversion a numero entero, redondeado
            BigDecimal decimalValue = BigDecimal.valueOf(calculoPuntosConDecimal);
            int calculoPuntosEntero = decimalValue.setScale(0, RoundingMode.HALF_UP).intValue();
            System.out.println("Los puntos que deben sumar son: "+calculoPuntosEntero);
            //Creacion de objeto puntuacion
            Puntuacion puntuacion=new Puntuacion(calculoPuntosEntero,new Date());
            //Asignacion de objeto puntos por actividad a puntuacion
            //Esto no esta diseñado en la clase
            //puntuacion.setPuntosPorActividad(puntosVideollamada);

            for (DetalleParticipante detalleParticipante: reunionVirtual.get().getListaDetalleParticipantes()
            ) {
                Optional<Usuario> usuario=usuarioRepository.findById(detalleParticipante.getParticipante().getId());
                int puntosTotales=usuario.get().getUsuarioPuntuacion().getPuntosTotales();
                usuario.get().getUsuarioPuntuacion().setPuntosTotales(puntosTotales+calculoPuntosEntero);
                usuario.get().getUsuarioPuntuacion().getListaPuntuacion().add(puntuacion);
                usuarioRepository.save(usuario.get());
            }



        }
        reunionVirtualRepository.save(reunionVirtual.get());
        return "Videollamada finalizada correctamente.";
    }

}
