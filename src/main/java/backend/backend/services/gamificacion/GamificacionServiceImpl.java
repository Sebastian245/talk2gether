package backend.backend.services.gamificacion;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import backend.backend.entities.Usuario;
import backend.backend.entities.UsuarioLogro;
import backend.backend.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import backend.backend.DTO.DTOLogrosAprendiz;
import backend.backend.DTO.DTOTablaRanking;
import backend.backend.entities.PuntosPorActividad;
import backend.backend.services.usuario.UsuarioService;

@Service
public class GamificacionServiceImpl implements GamificacionService{
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private CuentaRepository cuentaRepository;
    @Autowired
    private UsuarioService  usuarioService;
    @Autowired
    private GamificacionRepository gamificacionRepository;
    @Autowired
    private PuntosPorActividadRepository puntosPorActividadRepository;

    @Autowired
    private LogroRepository logroRepository;
    
    @Override
    public List<DTOTablaRanking> tablaRanking(Long idCuenta,int cantidadFilas) throws Exception {
        if(!cuentaRepository.existsById(idCuenta)){
            throw new Exception("La cuenta no existe.");
        }
        if(cantidadFilas<1){
            throw new Exception("La cantidad de filas a mostrar debe ser un número mayor a 0.");
        }
        // Obtener los primeros 25 usuarios con más puntos.
        Pageable pageable = PageRequest.of(0, cantidadFilas); // Limitar a las primeras 25 filas
        List<DTOTablaRanking> usuarios = usuarioRepository.listaUsuariosOrdenadosPorPuntosLimitados(pageable);
        // Obtener el usuario actual para poder posicionarlo
        DTOTablaRanking usuarioPosicion = usuarioRepository.posicionUsuarioTablaRaking(idCuenta);
        // Validad que el usuario se encuentre dentro de los primeros 25.
        if (!usuarios.contains(usuarioPosicion)) {
            usuarioPosicion.setPosicion(usuarioService.calculoTablaRanking(idCuenta));
            usuarios.add(usuarioPosicion);
        }
        List<DTOTablaRanking> usuariosPosicionados = new ArrayList<>();
        int contador = 1;
        // Agregar la posición a cada una de las filas.
        for (DTOTablaRanking usuarioPosicionAgregar : usuarios) {
            if (usuarioPosicionAgregar.getPosicion()==0) {
                usuarioPosicionAgregar.setPosicion(contador);
            }
            usuariosPosicionados.add(usuarioPosicionAgregar);
            contador++;
        }
        return usuariosPosicionados;
    }

    @Override
    public List<DTOLogrosAprendiz> obtenerLogros(Long idCuenta) throws Exception { 
        if(!cuentaRepository.existsById(idCuenta)){
            throw new Exception("La cuenta no existe.");
        }

        Optional<Long> idUsuario=cuentaRepository.obtenerIdUsuario(idCuenta);
        Optional<Usuario> usuario = usuarioRepository.findById(idUsuario.get());

        if(usuario.get().getListaUsuarioLogro()==null){
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

            usuario.get().getListaUsuarioLogro().add(usuarioLogroElComunicador);
            usuario.get().getListaUsuarioLogro().add(usuarioLogroAprendizEjemplar);
            usuario.get().getListaUsuarioLogro().add(usuarioLogroMenteMultilingue);
            usuario.get().getListaUsuarioLogro().add(usuarioLogroElFilosofo);
            usuario.get().getListaUsuarioLogro().add(usuarioLogroElPopular);
            usuario.get().getListaUsuarioLogro().add(usuarioLogroElViajero);
            usuarioRepository.save(usuario.get());
        }


        List<DTOLogrosAprendiz> dtoLogrosAprendiz = new ArrayList<>();
        Optional<PuntosPorActividad> puntosPorActividadComunicador = puntosPorActividadRepository.findById(3l);
        DTOLogrosAprendiz elComunicador = new DTOLogrosAprendiz();
        int valorActualElComunicador = gamificacionRepository.obtenerLogroComunicador(idCuenta);
        int valorMaximoElComunicador = puntosPorActividadComunicador.get().getPuntosMaximos();
        elComunicador.setValorActual(valorActualElComunicador);
        elComunicador.setValorMaximo(valorMaximoElComunicador);
        elComunicador.setPorcentajeLogro(valorActualElComunicador*100/valorMaximoElComunicador);
        elComunicador.setNombreLogro(puntosPorActividadComunicador.get().getNombrePuntosPorActividad());
        elComunicador.setDescripcionLogro(puntosPorActividadComunicador.get().getDescripcionPuntosPorActividad());
        elComunicador.setPuntosQueOtorga(puntosPorActividadComunicador.get().getPuntosPorActividad());


        //SI EL USUARIO CONSIGUE EL LOGRO SE LE CARGA LA FECHA DE CUANDO LO LOGRO Y SE LE SUMAN LOS PUNTOS
        for (UsuarioLogro usuarioLogro: usuario.get().getListaUsuarioLogro()) {
            if(usuarioLogro.getLogro().getId()==1){
                if(usuarioLogro.getFechaLogroConseguido() == null && valorActualElComunicador>= valorMaximoElComunicador){
                    usuarioLogro.setFechaLogroConseguido(new Date());

                    usuario.get().getUsuarioPuntuacion().setPuntosTotales(usuario.get().getUsuarioPuntuacion().getPuntosTotales()+ puntosPorActividadComunicador.get().getPuntosPorActividad());
                }
                if(usuarioLogro.getFechaLogroConseguido()!=null){
                    elComunicador.setElLogroSeConsiguio(true);
                }
            }

        }


        dtoLogrosAprendiz.add(elComunicador);

        Optional<PuntosPorActividad> puntosPorActividadAprendiz = puntosPorActividadRepository.findById(4l);
        DTOLogrosAprendiz aprendizEjemplar = new DTOLogrosAprendiz();
        int valorActualAprendizEjemplar = gamificacionRepository.obtenerLogroAprendizEjemplar(idCuenta);
        int valorMaximoAprendizEjemplar = puntosPorActividadAprendiz.get().getPuntosMaximos();
        aprendizEjemplar.setValorActual(valorActualAprendizEjemplar);
        aprendizEjemplar.setValorMaximo(valorMaximoAprendizEjemplar);
        aprendizEjemplar.setPorcentajeLogro((valorActualAprendizEjemplar*100/valorMaximoAprendizEjemplar));
        aprendizEjemplar.setNombreLogro(puntosPorActividadAprendiz.get().getNombrePuntosPorActividad());
        aprendizEjemplar.setDescripcionLogro(puntosPorActividadAprendiz.get().getDescripcionPuntosPorActividad());
        aprendizEjemplar.setPuntosQueOtorga(puntosPorActividadAprendiz.get().getPuntosPorActividad());

        //SI EL USUARIO CONSIGUE EL LOGRO SE LE CARGA LA FECHA DE CUANDO LO LOGRO Y SE LE SUMAN LOS PUNTOS
        for (UsuarioLogro usuarioLogro: usuario.get().getListaUsuarioLogro()) {
            if(usuarioLogro.getLogro().getId()==2){
                if(usuarioLogro.getFechaLogroConseguido() == null && valorActualAprendizEjemplar>= valorMaximoAprendizEjemplar){
                    usuarioLogro.setFechaLogroConseguido(new Date());
                    usuario.get().getUsuarioPuntuacion().setPuntosTotales(usuario.get().getUsuarioPuntuacion().getPuntosTotales()+ puntosPorActividadAprendiz.get().getPuntosPorActividad());
                }
                if(usuarioLogro.getFechaLogroConseguido()!=null){
                    aprendizEjemplar.setElLogroSeConsiguio(true);
                }
            }
        }
        dtoLogrosAprendiz.add(aprendizEjemplar);

        Optional<PuntosPorActividad> puntosPorActividadMenteMultilingue = puntosPorActividadRepository.findById(5l);
        DTOLogrosAprendiz menteMultilingue = new DTOLogrosAprendiz();
        int valorActualMenteMultilingue = gamificacionRepository.obtenerLogroMenteMultilingue(idCuenta);
        int valorMaximoMenteMultilingue = puntosPorActividadMenteMultilingue.get().getPuntosMaximos();
        menteMultilingue.setValorActual(valorActualMenteMultilingue);
        menteMultilingue.setValorMaximo(valorMaximoMenteMultilingue);
        menteMultilingue.setPorcentajeLogro((valorActualMenteMultilingue*100/valorMaximoMenteMultilingue));
        menteMultilingue.setNombreLogro(puntosPorActividadMenteMultilingue.get().getNombrePuntosPorActividad());
        menteMultilingue.setDescripcionLogro(puntosPorActividadMenteMultilingue.get().getDescripcionPuntosPorActividad());
        menteMultilingue.setPuntosQueOtorga(puntosPorActividadMenteMultilingue.get().getPuntosPorActividad());

        //SI EL USUARIO CONSIGUE EL LOGRO SE LE CARGA LA FECHA DE CUANDO LO LOGRO Y SE LE SUMAN LOS PUNTOS
        for (UsuarioLogro usuarioLogro: usuario.get().getListaUsuarioLogro()) {
            if(usuarioLogro.getLogro().getId()==3){
                if(usuarioLogro.getFechaLogroConseguido() == null && valorActualMenteMultilingue>= valorMaximoMenteMultilingue){
                    usuarioLogro.setFechaLogroConseguido(new Date());
                    usuario.get().getUsuarioPuntuacion().setPuntosTotales(usuario.get().getUsuarioPuntuacion().getPuntosTotales()+ puntosPorActividadMenteMultilingue.get().getPuntosPorActividad());
                }
                if(usuarioLogro.getFechaLogroConseguido()!=null){
                    menteMultilingue.setElLogroSeConsiguio(true);
                }
            }
        }
        dtoLogrosAprendiz.add(menteMultilingue);
        
        Optional<PuntosPorActividad> puntosPorActividadElPopular = puntosPorActividadRepository.findById(7l); 
        DTOLogrosAprendiz elPopular = new DTOLogrosAprendiz();
        int valorActualElPopular = gamificacionRepository.obtenerLogroElPopular(idCuenta);
        int valorMaximoElPopular = puntosPorActividadElPopular.get().getPuntosMaximos();
        elPopular.setValorActual(valorActualElPopular);
        elPopular.setValorMaximo(valorMaximoElPopular);
        elPopular.setPorcentajeLogro((valorActualElPopular*100/valorMaximoElPopular));
        elPopular.setNombreLogro(puntosPorActividadElPopular.get().getNombrePuntosPorActividad());
        elPopular.setDescripcionLogro(puntosPorActividadElPopular.get().getDescripcionPuntosPorActividad());
        elPopular.setPuntosQueOtorga(puntosPorActividadElPopular.get().getPuntosPorActividad());

        //SI EL USUARIO CONSIGUE EL LOGRO SE LE CARGA LA FECHA DE CUANDO LO LOGRO Y SE LE SUMAN LOS PUNTOS
        for (UsuarioLogro usuarioLogro: usuario.get().getListaUsuarioLogro()) {
            if(usuarioLogro.getLogro().getId()==4){
                if(usuarioLogro.getFechaLogroConseguido() == null && valorActualElPopular>= valorMaximoElPopular){
                    usuarioLogro.setFechaLogroConseguido(new Date());
                    usuario.get().getUsuarioPuntuacion().setPuntosTotales(usuario.get().getUsuarioPuntuacion().getPuntosTotales()+ puntosPorActividadElPopular.get().getPuntosPorActividad());
                }
                if(usuarioLogro.getFechaLogroConseguido()!=null){
                    elPopular.setElLogroSeConsiguio(true);
                }
            }
        }
        dtoLogrosAprendiz.add(elPopular);
        
        Optional<PuntosPorActividad> puntosPorActividadElFilosofo = puntosPorActividadRepository.findById(6l); 
        DTOLogrosAprendiz elFilosofo = new DTOLogrosAprendiz();
        int valorActualElFilosofo = gamificacionRepository.obtenerLogroElFilosofo(idCuenta);
        int valorMaximoElFilosofo = puntosPorActividadElFilosofo.get().getPuntosMaximos();
        elFilosofo.setValorActual(valorActualElFilosofo);
        elFilosofo.setValorMaximo(valorMaximoElFilosofo);
        elFilosofo.setPorcentajeLogro((valorActualElFilosofo*100/valorMaximoElFilosofo));
        elFilosofo.setNombreLogro(puntosPorActividadElFilosofo.get().getNombrePuntosPorActividad());
        elFilosofo.setDescripcionLogro(puntosPorActividadElFilosofo.get().getDescripcionPuntosPorActividad());
        elFilosofo.setPuntosQueOtorga(puntosPorActividadElFilosofo.get().getPuntosPorActividad());

        //SI EL USUARIO CONSIGUE EL LOGRO SE LE CARGA LA FECHA DE CUANDO LO LOGRO Y SE LE SUMAN LOS PUNTOS
        for (UsuarioLogro usuarioLogro: usuario.get().getListaUsuarioLogro()) {
            if(usuarioLogro.getLogro().getId()==5){
                if(usuarioLogro.getFechaLogroConseguido() == null && valorActualElFilosofo>= valorMaximoElFilosofo){
                    usuarioLogro.setFechaLogroConseguido(new Date());
                    usuario.get().getUsuarioPuntuacion().setPuntosTotales(usuario.get().getUsuarioPuntuacion().getPuntosTotales()+ puntosPorActividadElFilosofo.get().getPuntosPorActividad());
                }
                if(usuarioLogro.getFechaLogroConseguido()!=null){
                    elFilosofo.setElLogroSeConsiguio(true);
                }
            }
        }
        dtoLogrosAprendiz.add(elFilosofo);

        Optional<PuntosPorActividad> puntosPorActividadElViajero = puntosPorActividadRepository.findById(8l); 
        DTOLogrosAprendiz elViajero = new DTOLogrosAprendiz();
        int valorActualElViajero = gamificacionRepository.obtenerLogroElViajero(idCuenta);
        int valorMaximoElViajero = puntosPorActividadElViajero.get().getPuntosMaximos();
        elViajero.setValorActual(valorActualElViajero);
        elViajero.setValorMaximo(valorMaximoElViajero);
        elViajero.setPorcentajeLogro((valorActualElViajero*100/valorMaximoElViajero));
        elViajero.setNombreLogro(puntosPorActividadElViajero.get().getNombrePuntosPorActividad());
        elViajero.setDescripcionLogro(puntosPorActividadElViajero.get().getDescripcionPuntosPorActividad());
        elViajero.setPuntosQueOtorga(puntosPorActividadElViajero.get().getPuntosPorActividad());

        //SI EL USUARIO CONSIGUE EL LOGRO SE LE CARGA LA FECHA DE CUANDO LO LOGRO Y SE LE SUMAN LOS PUNTOS
        for (UsuarioLogro usuarioLogro: usuario.get().getListaUsuarioLogro()) {
            if(usuarioLogro.getLogro().getId()==6){
                if(usuarioLogro.getFechaLogroConseguido() == null && valorActualElViajero>= valorMaximoElViajero){
                    usuarioLogro.setFechaLogroConseguido(new Date());
                    usuario.get().getUsuarioPuntuacion().setPuntosTotales(usuario.get().getUsuarioPuntuacion().getPuntosTotales()+ puntosPorActividadElViajero.get().getPuntosPorActividad());
                }
                if(usuarioLogro.getFechaLogroConseguido()!=null){
                    elViajero.setElLogroSeConsiguio(true);
                }
            }
        }
        dtoLogrosAprendiz.add(elViajero);

        usuarioRepository.save(usuario.get());

        return dtoLogrosAprendiz;
    }
    
}
