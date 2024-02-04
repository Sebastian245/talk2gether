package backend.backend.repositories;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import backend.backend.entities.DetalleParticipante;

@Repository
public interface DetalleParticipanteRepository extends JpaRepository<DetalleParticipante,Long>{


    @Transactional
    @Query("SELECT b FROM DetalleParticipante b WHERE b.participante.id = ?1 AND b.fechaHoraFinDetalleParticipante IS NULL")
    Optional<DetalleParticipante> encontrarUsuarioDetalleParticipanteNoNulo(Long idUsuario);

    
    
}
