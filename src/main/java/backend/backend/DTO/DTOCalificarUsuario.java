package backend.backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DTOCalificarUsuario {
    private Long idCuentaCalificador;
    private Long idCuentaCalificado;
    private Long idReunionVirtual;
    private int cantidadEstrellas;
}
