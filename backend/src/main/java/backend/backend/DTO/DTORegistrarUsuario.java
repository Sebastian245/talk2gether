package backend.backend.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
@AllArgsConstructor
@Data
public class DTORegistrarUsuario {
    private String nombreUsuario;
    private String apellidoUsuario;
    private String fechaNacimiento;
    private String descripcion;
    private String correo;
    private String urlFoto;
    private String contrasenia;
    private String nombrePais;
    private String nombreIdiomaNativo;
    private String nombreIdiomaAprendiz;
    private String nombreNivelIdiomaAprendiz;
    private List<String> nombreIntereses;
}
