package backend.backend.DTO;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DTOMotivosCuentaEliminada {
    
    private Long idCuenta;
    private Long idAdministradorResponsable=-1l;
    private List<String> listaMotivos = new ArrayList<>();
    private String descripcionCuentaEliminada;
    private String contrasenia=null;
    public DTOMotivosCuentaEliminada(Long idCuenta, Long idAdministradorResponsable, List<String> listaMotivos,
            String descripcionCuentaEliminada) {
        this.idCuenta = idCuenta;
        this.idAdministradorResponsable = idAdministradorResponsable;
        this.listaMotivos = listaMotivos;
        this.descripcionCuentaEliminada = descripcionCuentaEliminada;
    }
    
}
