package backend.backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DTOListarInteres {
    private Long id;
    private String name;
    private String icon;
    private boolean seleccionado=false;
}
