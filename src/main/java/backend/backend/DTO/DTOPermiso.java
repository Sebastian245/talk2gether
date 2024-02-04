package backend.backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class DTOPermiso {
    long id;
    String nombrePermiso;
    String descripcion;
    Set<String> listaNombreRol = new HashSet<>();
}
