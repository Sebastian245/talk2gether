package backend.backend.DTO;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DTOFiltro {
    int edadMinima=0;
    int edadMaxima=0;
    List<String> intereses = new ArrayList<>();
    String nombreNivelIdioma;
    String nombrePais;
}
