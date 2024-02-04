package backend.backend.DTO;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DTOCuentaEliminadaMotivo {
    private boolean estaEliminado = true;
    private List<String> cuentaEliminadaMotivos = new ArrayList<>();
    private int numeroError=-1;

}
