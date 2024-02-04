package backend.backend.DTO;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DTOCalificaciones {
    private Long idCuenta;
    private String nombre;
    private String apellido;
    private String correo;
    private String urlFoto;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date fechaCalificacion;
    private int calificacion;
    public DTOCalificaciones(Long idCuenta, Date fechaCalificacion, int calificacion) {
        this.idCuenta = idCuenta;
        this.fechaCalificacion = fechaCalificacion;
        this.calificacion = calificacion;
    }
    public DTOCalificaciones(String nombre, String apellido, String correo, String urlFoto) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.urlFoto = urlFoto;
    }


    
}
