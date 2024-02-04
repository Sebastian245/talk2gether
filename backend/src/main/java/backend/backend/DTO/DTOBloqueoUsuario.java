package backend.backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DTOBloqueoUsuario {
    private Long idUsuarioQueBloquea;
    private Long idUsuarioBloqueado;
}
