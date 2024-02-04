package backend.backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DTOTablaRanking {
    private int posicion=0;
    private Long id;
    private String nombreUsuario;
    private int puntosTotales;
    private String urlFoto;
    public DTOTablaRanking(Long id, String nombreUsuario, int puntosTotales, String urlFoto) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.puntosTotales = puntosTotales;
        this.urlFoto = urlFoto;
    }
}
