package backend.backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DTOUsuario_Administrador {
    private Long idCuenta;
    private String nombreUsuario;
    private String apellidoUsuario;
    private String correo;
    private String nombreRol;
}
