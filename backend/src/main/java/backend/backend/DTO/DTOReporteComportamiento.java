package backend.backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DTOReporteComportamiento {
    private Long idCuenta;
    private String nombreUsuario;
    private String apellidoUsuario;
    private String correo;
    private Long idMotivo;
    private String nombreMotivo;
    private Long cantidadMotivo;

}