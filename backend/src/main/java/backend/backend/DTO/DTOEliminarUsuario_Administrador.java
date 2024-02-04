package backend.backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DTOEliminarUsuario_Administrador {
    private long idCuenta;
    private String nombreUsuario;
    private String apellidoUsuario;
    private String correo;
}