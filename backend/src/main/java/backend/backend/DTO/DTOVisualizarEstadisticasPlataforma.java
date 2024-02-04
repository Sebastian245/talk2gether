package backend.backend.DTO;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DTOVisualizarEstadisticasPlataforma {
    private int usuarioRegistradosTotal;
    private int usuariosActivos;
    private List<DTOUsuariosPorMes> usuariosRegistradosPorMes = new ArrayList<>();
    private List<DTOUsuariosPorPais> usuariosRegistradosPorPais = new ArrayList<>();
    private double porcentajeUsuariosQueUtilizanVideollamadas;
    private double promedioCalificacion;
    private int tiempoPromedioUsuariosQueUtilizanVideollamada;
}
