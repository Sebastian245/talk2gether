package backend.backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DTOCrearPuntosPorActividad {
    private String puntosPorActividad;
    private String nombrePuntosPorActividad;
    private String valorMaximo;
    private String descripcion;

}
