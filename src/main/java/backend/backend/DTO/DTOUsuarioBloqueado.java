package backend.backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DTOUsuarioBloqueado {

    private Long idCuenta;
    private String nombre;
    private String apellido;
    private String urlFoto;

}
