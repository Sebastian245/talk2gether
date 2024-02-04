package backend.backend.DTO;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DTOListarReunionesVirtuales {
    private long idReunionVirtual;
    private long idCuenta;
    private String linkReunionVirtual;
    private String nombreUsuario;
    private List<String> intereses = new ArrayList<>();
    private int edad;
    private String urlBandera;
    private String urlFoto;
    private int cantidadEstrellas;
    private String nombrePais;
    private String nombreNivelIdioma;
}
