package backend.backend.DTORepository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DTOListarSeguidosRepository {
    private Long id;
    private String nombreUsuario;
    private String apellidoUsuario;
    private String urlFoto;
}
