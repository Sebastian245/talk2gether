package backend.backend.authentication.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface ConfirmationTokenRepository
                extends JpaRepository<ConfirmationToken, Long> {

        Optional<ConfirmationToken> findByToken(String token);
        

        @Transactional
        @Modifying
        @Query("UPDATE ConfirmationToken c " +
                        "SET c.confirmedAt = ?2 " +
                        "WHERE c.token = ?1")
        int updateConfirmedAt(String token,
                        LocalDateTime confirmedAt);

        @Transactional
        @Query("SELECT c FROM ConfirmationToken c WHERE c.cuenta.id = :idCuenta")
        Optional<ConfirmationToken> findByIdCuenta(@Param("idCuenta") Long idCuenta);

}
