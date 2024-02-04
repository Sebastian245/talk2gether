package backend.backend.repositories;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import backend.backend.DTO.DTOPrevioCalificarUsuario;
import backend.backend.DTO.DTOReunionVirtual;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import backend.backend.DTORepository.DTOListarReunionesVirtualesRepository;
import backend.backend.entities.ReunionVirtual;

@Repository
public interface ReunionVirtualRepository extends JpaRepository<ReunionVirtual, Long> {

    
    // Realiza una consulta a base de datos para buscar el link de reunion virtual (VIGENTE)
    Optional<ReunionVirtual> findByLinkReunionVirtualAndFechaHoraFinVigenciaReunionVirtualIsNull(String linkReunionVirtual);


    
    // Realiza una consulta a base de datos para buscar las reuniones virtuales (VIGENTES)
    List<ReunionVirtual> findByFechaHoraFinVigenciaReunionVirtualIsNull();

    // Realiza una consulta a base de datos para buscar todas las reuniones virtuales e insertar en DTOListarReunionesVirtuales.
    @Transactional
    @Query("SELECT new backend.backend.DTORepository.DTOListarReunionesVirtualesRepository(r.id,dp.participante.cuenta.id,"+
    "r.linkReunionVirtual,dp.participante.nombreUsuario,dp.participante.fechaNacimiento,"+
    "dp.participante.pais.urlBandera,dp.participante.cuenta.urlFoto,l.interes.nombreInteres,"+
    "dp.participante.pais.nombrePais,dp.participante.idiomaAprendiz.nivelIdioma.nombreNivelIdioma)"+
    "FROM ReunionVirtual r JOIN r.listaDetalleParticipantes dp "+
    "JOIN dp.participante.listaIntereses l "+
    "WHERE size(r.listaDetalleParticipantes) < 2 AND r.fechaHoraFinVigenciaReunionVirtual IS NULL AND l.fechaHoraFinVigenciaInteres IS NULL AND dp.participante.idiomaNativo.idioma.nombreIdioma = ?1 AND dp.participante.idiomaAprendiz.idioma.nombreIdioma = ?2")
    List<DTOListarReunionesVirtualesRepository> listarReunionesVirtualesActivas(String idiomaAprender, String idiomaNativo);

    //Metodo para traer al otro participante de la llamada para poder calificarlo
    @Transactional
    @Query("SELECT new backend.backend.DTO.DTOPrevioCalificarUsuario(r.id,dp.participante.cuenta.id,"+
            "dp.participante.nombreUsuario,dp.participante.apellidoUsuario) "+
            "FROM ReunionVirtual r JOIN r.listaDetalleParticipantes dp "+
            "WHERE size(r.listaDetalleParticipantes)= 2 AND r.linkReunionVirtual = ?1  ")
    List<DTOPrevioCalificarUsuario> obtenerDatosParaCalificar(String url);

    @Transactional
    @Query("SELECT r.id,dp.participante.cuenta.id "+
            "FROM ReunionVirtual r JOIN r.listaDetalleParticipantes dp "+
            "WHERE r.fechaHoraFinVigenciaReunionVirtual IS NULL AND dp.participante.cuenta.id=?1 ")
    Optional<Long> obtenerIdReunionVirtualActivaPorIdCuenta(long idCuenta);

        @Transactional
    @Query("SELECT new backend.backend.DTO.DTOReunionVirtual(r.id,r.linkReunionVirtual) "+
            "FROM ReunionVirtual r JOIN r.listaDetalleParticipantes dp "+
            "WHERE size(r.listaDetalleParticipantes)= 1 AND r.fechaHoraFinVigenciaReunionVirtual IS NULL AND dp.participante.cuenta.id=?1 ")
    Optional<DTOReunionVirtual> obtenerLinkReunionVirtualActivaPorIdCuenta(long idCuenta);

}
