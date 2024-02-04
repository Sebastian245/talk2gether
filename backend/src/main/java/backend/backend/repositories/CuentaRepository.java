package backend.backend.repositories;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import backend.backend.DTO.DTOUsuarioBloqueado;
import backend.backend.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import backend.backend.DTO.DTODatosPersonales;
import backend.backend.DTO.DTOInteres;
import backend.backend.DTO.DTOUsuarioChat;
import backend.backend.DTORepository.DTOListarSeguidosRepository;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
        Optional<Cuenta> findByCorreo(String correo);

        @Transactional
        @Modifying(clearAutomatically = true)
        @Query("UPDATE Cuenta a " +
                        "SET a.cuentaVerificada = ?2 WHERE a.correo = ?1")
        int verificarCuentaPorEmail(String correo, Date date);

        // Consulta para verificar si existe al menos una fila de seguido.
        @Transactional
        @Query("SELECT case when count(*) > 0 then true else false end FROM Cuenta c JOIN c.listaSeguidos l WHERE c.id=?1 AND l.idCuenta=?2 AND l.fechaHoraFinVigenciaSeguido IS NULL")
        boolean buscarUsuarioSeguido(Long idUsuarioSeguidor, Long idUsuarioSeguido);

        // Consulta para obtener el objeto seguido de una cuenta con
        // fechaHoraFinVigencia nula.
        @Query("SELECT l FROM Cuenta c JOIN c.listaSeguidos l WHERE c.id=?1 AND l.idCuenta=?2 AND l.fechaHoraFinVigenciaSeguido IS NULL")
        Optional<Seguidos> buscarUsuarioSeguidoId(Long idSeguidor, Long idSeguido);

        // Consulta para obtener una lista de las id de las cuentas que sigue un
        // usuario. (Se verifica si la cuenta está eliminada y si la cuenta está
        // verificada)
        @Query("SELECT ls.idCuenta FROM Usuario u JOIN u.cuenta.listaSeguidos ls WHERE u.cuenta.id=?1 AND ls.fechaHoraFinVigenciaSeguido IS NULL")
        List<Long> listarIdSeguidos(Long idCuenta); 

        // Consulta para crear un DTO para mostrar información del seguido.
        @Query("SELECT new backend.backend.DTORepository.DTOListarSeguidosRepository(u.cuenta.id,u.nombreUsuario,u.apellidoUsuario,u.cuenta.urlFoto) FROM Usuario u WHERE u.cuenta.id=?1")
        DTOListarSeguidosRepository listaSeguidos(Long idCuenta);

        // Consulta para obtener una lista de las id de las cuentas que siguen a un
        // usuario. (Se verifica si la cuenta está eliminada y si la cuenta está
        // verificada)
        @Query("SELECT ls.idCuenta FROM Usuario u JOIN u.cuenta.listaSeguidores ls WHERE u.cuenta.id=?1 AND ls.fechaHoraFinVigenciaSeguidor IS NULL")
        List<Long> listarIdSeguidores(Long idCuenta);

        // Consulta para crear un DTO para mostrar información del seguidor.
        @Query("SELECT new backend.backend.DTORepository.DTOListarSeguidosRepository(u.cuenta.id,u.nombreUsuario,u.apellidoUsuario,u.cuenta.urlFoto) FROM Usuario u WHERE u.cuenta.id=?1")
        DTOListarSeguidosRepository listaSeguidores(Long idCuenta);

        // Consulta para obtener el objeto seguidor de una cuenta con
        // fechaHoraFinVigencia nula.
        @Query("SELECT l FROM Cuenta c JOIN c.listaSeguidores l WHERE c.id=?1 AND l.idCuenta=?2 AND l.fechaHoraFinVigenciaSeguidor IS NULL")
        Optional<Seguidores> buscarUsuarioSeguidorId(Long idSeguido, Long idSeguidor);

        // Consulta verificar seguimiento.
        @Query("SELECT case when count(*) > 0 then true else false end FROM Cuenta c JOIN c.listaSeguidos l WHERE c.id=?1 AND l.idCuenta=?2 AND l.fechaHoraFinVigenciaSeguido IS NULL")
        boolean verificarSeguimiento(Long idSeguidor, Long idSeguido);

        // Consulta para obtener un usuario a partir de idCuenta.
        @Query("SELECT u.id FROM Usuario u WHERE u.cuenta.id=?1 AND u.cuenta.cuentaVerificada IS NOT NULL AND u.cuenta.cuentaEliminada IS NULL")
        Optional<Long> obtenerIdUsuario(Long idCuenta);

        // Consulta para obtener un usuario a partir de idCuenta.
        @Query("SELECT uc.calificacion.cantidadEstrellas FROM Usuario u JOIN u.listaUsuarioCalificacion uc WHERE u.cuenta.id=?1 AND u.cuenta.cuentaVerificada IS NOT NULL AND u.cuenta.cuentaEliminada IS NULL GROUP BY uc.calificacion.cantidadEstrellas")
        List<Long> obtenerCalificacionUsuario(Long idCuenta);

        @Query("SELECT c.id FROM Cuenta c WHERE c.id=?1 AND c.cuentaEliminada IS NULL")
        Optional<Long> estaEliminadaLaCuenta(Long idCuenta);

        // Consulta obtener el idioma aprendiz para el filtro de salas
        @Query("SELECT u.idiomaAprendiz.idioma.nombreIdioma FROM Usuario u WHERE u.cuenta.id=?1")
        Optional<String> obtenerIdiomaAprendiz(Long idCuenta);

        // Consulta obtener nombre apellido, url foto y idCuenta
        @Query("SELECT new backend.backend.DTO.DTOUsuarioChat(u.cuenta.id,u.nombreUsuario,u.apellidoUsuario,u.cuenta.urlFoto) FROM Usuario u WHERE u.cuenta.id=?1")
        Optional<DTOUsuarioChat> obtenerUsuarioChat(Long idCuenta);

        // Método para obtener cantidad de puntos del usuario.
        @Transactional
        @Query("SELECT u.usuarioPuntuacion.puntosTotales FROM Usuario u WHERE u.cuenta.id=?1")
        int obtenerCantidadPuntos(Long idCuenta);

        // Método para obtener el tiempo en videollamada con mayor duración del usuario.
        @Transactional
        @Query("SELECT COALESCE (MAX(r.duracionReunionVirtual),0) FROM ReunionVirtual r JOIN r.listaDetalleParticipantes ldp WHERE ldp.participante.cuenta.id = ?1 AND r.fechaHoraFinVigenciaReunionVirtual IS NOT NULL")
        double obtenerTiempoMaximoVideollamada(Long idCuenta);

        // Método para obtener cantidad de interacciones del usuario por paises
        // diferentes.
        @Transactional
        @Query("SELECT COUNT(DISTINCT dp.participante.pais.nombrePais) " +
                        "FROM ReunionVirtual r " +
                        "JOIN r.listaDetalleParticipantes dp " +
                        "WHERE size(r.listaDetalleParticipantes) = 2 " +
                        "AND r.fechaHoraFinVigenciaReunionVirtual IS NOT NULL " +
                        "AND dp.participante.cuenta.id <> ?1 " +
                        "AND r.id IN (" +
                        "SELECT r2.id " +
                        "FROM ReunionVirtual r2 " +
                        "JOIN r2.listaDetalleParticipantes dp2 " +
                        "WHERE dp2.participante.cuenta.id = ?1" +
                        ")")
        int obtenerCantidadInteraccionesPaisesDif(Long idCuenta);

        // Método para obtener cantidad de interacciones del usuario por usuarios
        // diferentes.
        @Transactional
        @Query("SELECT COUNT(DISTINCT dp.participante.cuenta.id) " +
                        "FROM ReunionVirtual r " +
                        "JOIN r.listaDetalleParticipantes dp " +
                        "WHERE size(r.listaDetalleParticipantes) = 2 " +
                        "AND r.fechaHoraFinVigenciaReunionVirtual IS NOT NULL " +
                        "AND dp.participante.cuenta.id <> ?1 " +
                        "AND r.id IN (" +
                        "SELECT r2.id " +
                        "FROM ReunionVirtual r2 " +
                        "JOIN r2.listaDetalleParticipantes dp2 " +
                        "WHERE dp2.participante.cuenta.id = ?1" +
                        ")")
        int obtenerCantidadInteraccionesUsuariosDif(Long idCuenta);

        // Método para obtener la cantidad de puntos del usuario en los últimos 7 días.
        @Transactional
        @Query("SELECT lp FROM Usuario u JOIN u.usuarioPuntuacion.listaPuntuacion lp WHERE u.cuenta.id = ?1")
        List<Puntuacion> obtenerCantidadDePuntosUltimosSieteDias(Long idCuenta);

        // Consulta obtener datos personales
        @Transactional
        @Query("SELECT new backend.backend.DTO.DTODatosPersonales(u.cuenta.id,u.cuenta.correo,u.nombreUsuario,u.apellidoUsuario,u.descripcionUsuario,u.fechaNacimiento,u.pais.nombrePais,u.idiomaNativo.idioma.nombreIdioma as nombreIdiomaNativo,u.idiomaAprendiz.idioma.nombreIdioma as nombreIdiomaAprendiz,u.idiomaAprendiz.nivelIdioma.nombreNivelIdioma,u.cuenta.urlFoto) FROM Usuario u WHERE u.cuenta.id=?1")
        DTODatosPersonales obtenerDatosPersonales(Long idCuenta);

        // Consulta obtener intereses para datos personales
        @Transactional
        @Query("SELECT new backend.backend.DTO.DTOInteres(l.interes.id,l.interes.nombreInteres,l.interes.urlInteres) FROM Usuario u JOIN u.listaIntereses l WHERE u.cuenta.id=?1 AND l.fechaHoraFinVigenciaInteres IS NULL")
        List<DTOInteres> obtenerIntereses(Long idCuenta);

        //Obtener el correo de una cuenta
        @Transactional
        @Query("SELECT c.correo FROM Cuenta c WHERE c.id=?1")
        String obtenerCorreo(Long id);
        
        //Obtener cantidad de seguidos del aprendiz
        @Transactional
        @Query("SELECT count(lseguidos.idCuenta) " +
               "FROM Cuenta cuenta " +
               "JOIN cuenta.listaSeguidos lseguidos " +
               "WHERE cuenta.id = ?1 " +
               "AND lseguidos.fechaHoraFinVigenciaSeguido IS NULL")
        Long obtenerCantidadSeguidos(Long idCuenta);

        //Obtener cantidad de seguidores del aprendiz
        @Transactional
        @Query("SELECT count(lseguidores.idCuenta) " +
               "FROM Cuenta cuenta " +
               "JOIN cuenta.listaSeguidores lseguidores " +
               "WHERE cuenta.id = ?1 " +
               "AND lseguidores.fechaHoraFinVigenciaSeguidor IS NULL")
        Long obtenerCantidadSeguidores(Long idCuenta);

       
       @Transactional
       @Query("SELECT c FROM Cuenta c WHERE c.rol.id=?1")
        List<Cuenta> buscarUsuarioPorRol(Long id);

        // Consulta para obtener id usuarios bloqueados
        @Transactional
        @Query("SELECT b.usuarioBloqueado.id FROM Bloqueos b WHERE b.usuarioQueBloquea.id=?1 AND b.usuarioBloqueado.cuentaVerificada IS NOT NULL AND b.usuarioBloqueado.cuentaEliminada IS NULL AND b.fechaHoraFinVigenciaBloqueoRealizado IS NULL")
        List<Long> listaIdUsuariosBloqueados(Long idCuenta);

        // Consulta para obtener nombre, apellido, idCuenta, urlFoto a partir de un idCuenta.
        @Transactional
        @Query("SELECT new backend.backend.DTO.DTOUsuarioBloqueado(u.cuenta.id,u.nombreUsuario,u.apellidoUsuario,u.cuenta.urlFoto) FROM Usuario u WHERE u.cuenta.id=?1 AND u.cuenta.cuentaVerificada IS NOT NULL AND u.cuenta.cuentaEliminada IS NULL")
        DTOUsuarioBloqueado listaUsuariosBloqueados(Long idCuenta);

        //@Transactional
        //@Query("SELECT UsuarioLogro FROM Usuario u JOIN u.listaUsuarioLogro lul WHERE u.id=?1 AND lul.logro.id=1  AND u.cuenta.cuentaVerificada IS NOT NULL AND u.cuenta.cuentaEliminada IS NULL")
         //UsuarioLogro obtenerUsuarioLogroComunicador(Optional<Long> idUsuario);
}
