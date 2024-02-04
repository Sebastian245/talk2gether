package backend.backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DTOLogrosAprendiz {
    private String nombreLogro;
    private String descripcionLogro;
    private int valorActual;
    private int valorMaximo;
    private int porcentajeLogro;
    private int puntosQueOtorga;
    private boolean elLogroSeConsiguio=false;
     
}

