package backend.backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DTOUsuariosPorPais {
    private Long cantidadUsuarios;
    private String nombrePais;
}
