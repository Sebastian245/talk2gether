package backend.backend.repositories;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import backend.backend.DTO.DTOCalificaciones;
import backend.backend.DTO.DTODatosCalificacion;
import backend.backend.DTO.DTOEliminarUsuario_Administrador;
import backend.backend.DTO.DTOUsuario_Administrador;
import backend.backend.DTO.DTOUsuariosPorMes;
import backend.backend.DTO.DTOUsuariosPorPais;
import backend.backend.entities.Usuario;

@Repository
public interface UsuarioAdministradorRepository extends JpaRepository<Usuario, Long> {

    @Transactional
    @Query("SELECT new backend.backend.DTO.DTOUsuario_Administrador(u.cuenta.id as idCuenta,u.nombreUsuario as nombreUsuario,u.apellidoUsuario as apellidoUsuario,u.cuenta.correo as correo,u.cuenta.rol.nombreRol as nombreRol) FROM Usuario u WHERE u.cuenta.cuentaEliminada IS NULL AND u.cuenta.cuentaVerificada IS NOT NULL ORDER BY u.cuenta.id ASC")
    List<DTOUsuario_Administrador> obtenerUsuarios();

    @Transactional
    @Query("SELECT new backend.backend.DTO.DTOUsuario_Administrador(u.cuenta.id as idCuenta,u.nombreUsuario as nombreUsuario,u.apellidoUsuario as apellidoUsuario,u.cuenta.correo as correo,u.cuenta.rol.nombreRol as nombreRol) FROM Usuario u WHERE u.cuenta.cuentaEliminada IS NULL AND u.cuenta.cuentaVerificada IS NOT NULL AND (u.cuenta.correo LIKE %?1% OR u.cuenta.rol.nombreRol LIKE %?1%) ORDER BY u.cuenta.id ASC")
    List<DTOUsuario_Administrador> filtrarUsuarios(String parametroFiltro);

    @Transactional
    @Query("SELECT new backend.backend.DTO.DTOEliminarUsuario_Administrador(u.cuenta.id as idCuenta,u.nombreUsuario as nombreUsuario,u.apellidoUsuario as apellidoUsuario,u.cuenta.correo as correo) FROM Usuario u WHERE u.cuenta.cuentaEliminada IS NULL AND u.cuenta.id=?1")
    DTOEliminarUsuario_Administrador obtenerUsuario(long idCuenta);

    @Transactional
    @Query("SELECT count(*) FROM Cuenta u WHERE u.cuentaVerificada IS NOT NULL")
    int obtenerCantidadUsuariosRegistrados();

    @Transactional
    @Query("SELECT AVG(u.calificacion.cantidadEstrellas) FROM UsuarioCalificacion u")
    double obtenerPromedioCalificaciones();

    @Transactional
    @Query("SELECT AVG(r.duracionReunionVirtual) FROM ReunionVirtual r WHERE r.fechaHoraFinVigenciaReunionVirtual IS NOT NULL")
    double obtenerPromedioReunionVirtual();

    @Transactional
    @Query("SELECT count(DISTINCT dp.participante.cuenta.id) FROM DetalleParticipante dp")
    int obtenerUsuariosQueUtilizanVideollamadas();

    @Transactional
    @Query("SELECT COUNT(c) FROM Cuenta c WHERE c.ultimaConexion >= :tiempoResta")
    int obtenerUsuariosActivos(Date tiempoResta);


    @Transactional
    @Query("SELECT c.correo FROM Cuenta c WHERE c.ultimaConexion >= :tiempoResta")
    List<String> obtenerUsuariosActivosCorreo(Date tiempoResta);


    @Transactional
    @Query("SELECT new backend.backend.DTO.DTOUsuariosPorPais(COUNT(u),p.nombrePais) " +
            "FROM Usuario u " +
            "JOIN u.pais p WHERE u.cuenta.cuentaVerificada IS NOT NULL " +
            "GROUP BY p.nombrePais " +
            "ORDER BY COUNT(u) DESC")
    List<DTOUsuariosPorPais> obtenerUsuariosPorPais();

    @Transactional
    @Query("SELECT new backend.backend.DTO.DTOUsuariosPorMes(COUNT(u), MONTH(u.cuenta.cuentaCreada), YEAR(u.cuenta.cuentaCreada)) "
            +
            "FROM Usuario u " +
            "WHERE u.cuenta.cuentaCreada IS NOT NULL AND u.cuenta.cuentaVerificada IS NOT NULL " +
            "GROUP BY MONTH(u.cuenta.cuentaCreada), YEAR(u.cuenta.cuentaCreada) " +
            "ORDER BY YEAR(u.cuenta.cuentaCreada) DESC, MONTH(u.cuenta.cuentaCreada) DESC")
    List<DTOUsuariosPorMes> obtenerUsuariosPorMes();

    @Transactional
    @Query("SELECT new backend.backend.DTO.DTODatosCalificacion(u.cuenta.id as idCuenta,u.nombreUsuario as nombreUsuario,u.apellidoUsuario as apellidoUsuario,u.cuenta.correo as correo,u.cuenta.rol.nombreRol as nombreRol) FROM Usuario u WHERE u.cuenta.id=?1 AND u.cuenta.cuentaEliminada IS NULL AND u.cuenta.cuentaVerificada IS NOT NULL ORDER BY u.cuenta.id ASC")
    DTODatosCalificacion obtenerDatosUsuarioCalificacion(long idCuenta);

    @Transactional
    @Query("SELECT new backend.backend.DTO.DTOCalificaciones(uc.idUsuarioCalificador,uc.fechaCalificacion,uc.calificacion.cantidadEstrellas) FROM Usuario u JOIN u.listaUsuarioCalificacion uc WHERE u.cuenta.id=?1 AND u.cuenta.cuentaEliminada IS NULL AND u.cuenta.cuentaVerificada IS NOT NULL")
    List<DTOCalificaciones> obtenerUsuariosCalificacion(long idCuentaCalificado);

    @Transactional
    @Query("SELECT new backend.backend.DTO.DTOCalificaciones(u.nombreUsuario as nombreUsuario,u.apellidoUsuario as apellidoUsuario,u.cuenta.correo as correo,u.cuenta.urlFoto) FROM Usuario u WHERE u.cuenta.id=?1")
    DTOCalificaciones obtenerUsuariosCalificaciones(long idCuentaCalificador);

    /* COMIENZA CONSULTAS PARA ESTADISTICAS DE ADMINISTRADOR FILTRADAS */
    @Transactional
    @Query("SELECT COUNT(*) FROM Cuenta u WHERE u.cuentaVerificada IS NOT NULL " +
            "AND (u.cuentaCreada BETWEEN :fechaDesde AND :fechaHasta)")
    int obtenerCantidadUsuariosRegistradosGranulado(@Param("fechaDesde") Date fechaDesde,
            @Param("fechaHasta") Date fechaHasta);

    @Transactional
    @Query("SELECT COALESCE(AVG(u.calificacion.cantidadEstrellas),0) FROM UsuarioCalificacion u WHERE u.fechaCalificacion BETWEEN :fechaDesde AND :fechaHasta")
    double obtenerPromedioCalificacionesGranulado(@Param("fechaDesde") Date fechaDesde,
            @Param("fechaHasta") Date fechaHasta);

    @Transactional
    @Query("SELECT COALESCE(AVG(r.duracionReunionVirtual),0) FROM ReunionVirtual r WHERE r.fechaHoraFinVigenciaReunionVirtual IS NOT NULL AND r.fechaHoraAltaReunionVirtual BETWEEN :fechaDesde AND :fechaHasta")
    double obtenerPromedioReunionVirtualGranulado(@Param("fechaDesde") Date fechaDesde,
            @Param("fechaHasta") Date fechaHasta);

    @Transactional
    @Query("SELECT count(DISTINCT dp.participante.cuenta.id) FROM DetalleParticipante dp WHERE dp.fechaHoraInicioDetalleParticipante BETWEEN :fechaDesde AND :fechaHasta ")
    int obtenerUsuariosQueUtilizanVideollamadasGranulado(@Param("fechaDesde") Date fechaDesde,
            @Param("fechaHasta") Date fechaHasta);

    @Transactional
    @Query("SELECT new backend.backend.DTO.DTOUsuariosPorPais(COUNT(u), p.nombrePais) " +
            "FROM Usuario u " +
            "JOIN u.pais p " +
            "WHERE u.cuenta.cuentaVerificada IS NOT NULL " +
            "AND u.cuenta.cuentaCreada BETWEEN :fechaDesde AND :fechaHasta " + // Agregar filtro de fecha
            "GROUP BY p.nombrePais " +
            "ORDER BY COUNT(u) DESC")
    List<DTOUsuariosPorPais> obtenerUsuariosPorPaisGranulado(@Param("fechaDesde") Date fechaDesde, @Param("fechaHasta") Date fechaHasta);
    

    @Transactional
    @Query("SELECT new backend.backend.DTO.DTOUsuariosPorMes(COUNT(u), MONTH(u.cuenta.cuentaCreada), YEAR(u.cuenta.cuentaCreada)) " +
            "FROM Usuario u " +
            "WHERE u.cuenta.cuentaCreada IS NOT NULL " +
            "AND u.cuenta.cuentaVerificada IS NOT NULL " +
            "AND u.cuenta.cuentaCreada BETWEEN :fechaDesde AND :fechaHasta " + // Agregar filtro de fecha
            "GROUP BY MONTH(u.cuenta.cuentaCreada), YEAR(u.cuenta.cuentaCreada) " +
            "ORDER BY YEAR(u.cuenta.cuentaCreada) DESC, MONTH(u.cuenta.cuentaCreada) DESC")
    List<DTOUsuariosPorMes> obtenerUsuariosPorMesGranulado(@Param("fechaDesde") Date fechaDesde, @Param("fechaHasta") Date fechaHasta);
    
    /* FINALIZA CONSULTAS PARA ESTADISTICAS DE ADMINISTRADOR FILTRADAS */
}
