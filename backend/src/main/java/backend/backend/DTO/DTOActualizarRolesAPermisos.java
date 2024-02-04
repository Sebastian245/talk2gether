package backend.backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DTOActualizarRolesAPermisos {

    String nombrePermiso;

    List<String> listaNombreRol = new ArrayList<>();

}
