package backend.backend.DTO;

import java.util.Map;
import java.util.TreeMap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DTOVisualizarEstadisticasAprendiz {
    private int promedioCalificaciones; 
    private int cantidadPuntos;
    private double tiempoVideollamada;
    private int cantidadInteraccionesPaisesDif;
    private int cantidadInteraccionesUsuariosDif;
    private Map<String, Integer> puntuacionesPorFecha = new TreeMap<>();


}

