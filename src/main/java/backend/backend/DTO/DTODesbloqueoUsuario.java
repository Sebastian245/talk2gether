package backend.backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DTODesbloqueoUsuario {
    private Long idUsuarioQueDesbloquea;
    private Long idUsuarioDesbloqueado;
}
