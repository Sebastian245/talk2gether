package backend.backend.DTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DTODatosPersonales {
    private Long id;
    private String correo;
    private String nombreUsuario;
    private String apellidoUsuario;
    private String descripcion;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date fechaNacimiento;
    private String nombrePais;
    private String nombreIdiomaNativo;
    private String nombreIdiomaAprender;
    private String nombreNivelIdioma;
    private String urlFoto;
    private List<DTOInteres> listaIntereses = new ArrayList<>();
    public DTODatosPersonales(Long id, String correo, String nombreUsuario, String apellidoUsuario, String descripcion,
            Date fechaNacimiento, String nombrePais, String nombreIdiomaNativo, String nombreIdiomaAprender,
            String nombreNivelIdioma, String urlFoto) {
        this.id = id;
        this.correo = correo;
        this.nombreUsuario = nombreUsuario;
        this.apellidoUsuario = apellidoUsuario;
        this.descripcion = descripcion;
        this.fechaNacimiento = fechaNacimiento;
        this.nombrePais = nombrePais;
        this.nombreIdiomaNativo = nombreIdiomaNativo;
        this.nombreIdiomaAprender = nombreIdiomaAprender;
        this.nombreNivelIdioma = nombreNivelIdioma;
        this.urlFoto = urlFoto;
    }
    
    

}
