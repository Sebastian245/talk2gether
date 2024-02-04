package backend.backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
@AllArgsConstructor
@Data
public class DTOAdministrarUsuario_Administrador {
    private String nombreUsuario;
    private String apellidoUsuario;
    private String fechaNacimiento;
    private String correo;
    private String contrasenia;
    private String nombreRol;
}
