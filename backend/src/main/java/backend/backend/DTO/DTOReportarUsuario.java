package backend.backend.DTO;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DTOReportarUsuario {
    private Long idCuentaInformanteMotivo;
    private Long idCuentaReportado;
    private String descripcionReporteMotivo;
    private List<String> nombreMotivo = new ArrayList<>();
}
