package backend.backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DTOUsuarioChat {
    private Long id;
    private String nombreUsuario;
    private String apellidoUsuario;
    private String urlFoto;
}
