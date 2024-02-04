package backend.backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DTOVerificacion {
    private String mensaje;
    private boolean estado = false;
    private int numeroError=-1;
    public DTOVerificacion(String mensaje, boolean estado) {
        this.mensaje = mensaje;
        this.estado = estado;
    }
}
