package backend.backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DTOCambiarContrasenia {
    private Long id;
    private String contraseniaNueva;
    private String contraseniaAntigua;
    
}
