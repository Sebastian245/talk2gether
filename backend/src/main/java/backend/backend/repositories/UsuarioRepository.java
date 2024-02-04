package backend.backend.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import backend.backend.DTO.DTOTablaRanking;
import backend.backend.DTORepository.DTOListarReunionesVirtualesRepository;
import backend.backend.DTORepository.DTOPerfilOtroUsuarioRepository;
import backend.backend.entities.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long>{  
    
    //Utilizado para calcular la tabla de ranking
    @Transactional
    @Query("SELECT u.cuenta.id as id, u.usuarioPuntuacion.puntosTotales as puntosTotales FROM Usuario u")
    List<DTOPerfilOtroUsuarioRepository> listaUsuarios();

    @Transactional
    @Query("SELECT new backend.backend.DTO.DTOTablaRanking(u.cuenta.id as id,CONCAT (u.nombreUsuario,' ',u.apellidoUsuario) as nombreUsuario,u.usuarioPuntuacion.puntosTotales as puntosTotales, u.cuenta.urlFoto as urlFoto) FROM Usuario u ORDER BY u.usuarioPuntuacion.puntosTotales DESC")
    List<DTOTablaRanking> listaUsuariosOrdenadosPorPuntosLimitados(Pageable pageable);

    @Transactional
    @Query("SELECT new backend.backend.DTO.DTOTablaRanking(u.cuenta.id as id,CONCAT (u.nombreUsuario,' ',u.apellidoUsuario) as nombreUsuario,u.usuarioPuntuacion.puntosTotales as puntosTotales, u.cuenta.urlFoto as urlFoto) FROM Usuario u WHERE u.cuenta.id=?1 ORDER BY u.usuarioPuntuacion.puntosTotales DESC")
    DTOTablaRanking posicionUsuarioTablaRaking(Long idCuenta);

    @Transactional
    @Query("SELECT new backend.backend.DTO.DTOTablaRanking(u.cuenta.id as id,u.nombreUsuario as nombreUsuario,u.usuarioPuntuacion.puntosTotales as puntosTotales, u.cuenta.urlFoto as urlFoto) FROM Usuario u ORDER BY u.usuarioPuntuacion.puntosTotales DESC")
    List<DTOTablaRanking> listaUsuariosTablaRanking();

    @Transactional
    @Query("SELECT new backend.backend.DTORepository.DTOListarReunionesVirtualesRepository(u.cuenta.id," +
            "u.nombreUsuario,u.fechaNacimiento," +
            "u.pais.urlBandera,u.cuenta.urlFoto," +
            "u.pais.nombrePais,u.idiomaAprendiz.nivelIdioma.nombreNivelIdioma) " +
            "FROM Usuario u " +
            "WHERE u.cuenta.cuentaEliminada IS NULL AND u.cuenta.cuentaVerificada IS NOT NULL AND (" +
            "(CONCAT(u.nombreUsuario, ' ', u.apellidoUsuario) LIKE %?1%) OR (CONCAT(u.apellidoUsuario, ' ', u.nombreUsuario) LIKE %?1%))")
    List<DTOListarReunionesVirtualesRepository> buscarUsuario(String parametroDeBusqueda);

    //Obtener idioma a aprender del usuario
    @Transactional
    @Query("SELECT u.idiomaAprendiz.idioma.nombreIdioma FROM Usuario u WHERE u.id=?1")
    String obtenerIdiomaAprenderUsuario(Long idUsuario);

    @Transactional
    @Query("SELECT u.idiomaNativo.idioma.nombreIdioma FROM Usuario u WHERE u.id=?1")
    String obtenerIdiomaNativoUsuario(Long idUsuario);

}
   
