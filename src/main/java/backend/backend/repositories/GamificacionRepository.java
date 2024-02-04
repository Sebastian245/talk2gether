package backend.backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import backend.backend.entities.Usuario;

@Repository
public interface GamificacionRepository extends JpaRepository<Usuario,Long> {


    @Query("SELECT u.cuenta.cantidadReferidos FROM Usuario u WHERE u.cuenta.id=?1")
    int obtenerLogroComunicador(Long idCuenta);
    
    @Query("SELECT count(*) FROM Usuario u JOIN u.cuenta.listaSeguidores ls WHERE u.cuenta.id=?1 AND ls.fechaHoraFinVigenciaSeguidor IS NULL")
    int obtenerLogroElPopular(Long idCuenta);
    
    @Query("SELECT (u.usuarioPuntuacion.puntosTotales) FROM Usuario u WHERE u.cuenta.id=?1")
    int obtenerLogroMenteMultilingue(Long idCuenta);
    
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
    int obtenerLogroElFilosofo(Long idCuenta);


    @Query("SELECT count(*) FROM Usuario u JOIN u.listaUsuarioCalificacion luc WHERE u.cuenta.id=?1 AND luc.calificacion.cantidadEstrellas=5")
    int obtenerLogroAprendizEjemplar(Long idCuenta);
    
   
    
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
    int obtenerLogroElViajero(Long idCuenta);


}
