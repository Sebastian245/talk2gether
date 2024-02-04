package backend.backend.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DTOCrearRol {
    private String nombreRol;
    private List<String> listaRolPermiso;
}
