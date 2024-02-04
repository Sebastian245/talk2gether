package backend.backend.DTORepository;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DTOListarReunionesVirtualesRepository {
    private long id;
    private long idCuenta;
    private String linkReunionVirtual;
    private String nombreUsuario;
    private Date fechaNacimiento;
    private String urlBandera;
    private String urlFoto;
    private String nombreInteres;
    private String nombrePais;
    private String nombreNivelIdioma;
    public DTOListarReunionesVirtualesRepository(long id,long idCuenta, String linkReunionVirtual, String nombreUsuario,
            Date fechaNacimiento, String urlBandera, String urlFoto, String nombreInteres) {
        this.id = id;
        this.idCuenta=idCuenta;
        this.linkReunionVirtual = linkReunionVirtual;
        this.nombreUsuario = nombreUsuario;
        this.fechaNacimiento = fechaNacimiento;
        this.urlBandera = urlBandera;
        this.urlFoto = urlFoto;
        this.nombreInteres = nombreInteres;
    }
    public DTOListarReunionesVirtualesRepository(long idCuenta, String nombreUsuario, Date fechaNacimiento,
            String urlBandera, String urlFoto, String nombrePais, String nombreNivelIdioma) {
        this.idCuenta = idCuenta;
        this.nombreUsuario = nombreUsuario;
        this.fechaNacimiento = fechaNacimiento;
        this.urlBandera = urlBandera;
        this.urlFoto = urlFoto;
        this.nombrePais = nombrePais;
        this.nombreNivelIdioma = nombreNivelIdioma;
    }
    
    
    

    

}
