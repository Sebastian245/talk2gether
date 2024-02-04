package backend.backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DTOListarTipoMotivos {
    private long idTipoMotivo;
    private String nombreTipoMotivo;
}