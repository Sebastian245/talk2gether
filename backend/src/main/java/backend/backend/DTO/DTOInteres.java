package backend.backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DTOInteres {
    private Long id;
    private String name;
    private String icon;
    private boolean seleccionado=true;
    public DTOInteres(Long id, String name, String icon) {
        this.id = id;
        this.name = name;
        this.icon = icon;
    }
}
