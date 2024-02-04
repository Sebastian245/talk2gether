package backend.backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DTOUsuariosPorMes {
    private Long cantidadUsuarios;
    private int numeroMes;
    private int anio;
    private String nombreMes;
    public DTOUsuariosPorMes(Long cantidadUsuarios, int numeroMes, int anio) {
        this.cantidadUsuarios = cantidadUsuarios;
        this.numeroMes = numeroMes;
        this.anio = anio;
    }
}
