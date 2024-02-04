package backend.backend.DTO;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DTODatosCalificacion {
    private Long idCuenta;
    private String nombreUsuario;
    private String apellidoUsuario;
    private String correo;
    private String nombreRol;
    public DTODatosCalificacion(Long idCuenta, String nombreUsuario, String apellidoUsuario, String correo,
            String nombreRol) {
        this.idCuenta = idCuenta;
        this.nombreUsuario = nombreUsuario;
        this.apellidoUsuario = apellidoUsuario;
        this.correo = correo;
        this.nombreRol = nombreRol;
    }
    private List<DTOCalificaciones> calificaciones= new ArrayList<>();
}
