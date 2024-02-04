package backend.backend.DTO;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DTOPerfilOtroUsuario {
   private String nombreUsuario;
   private String apellidoUsuario;
   private int edadUsuario;
   private String descripcionUsuario;
   private String nombreIdiomaAprendiz;
   private String nombreNivelIdiomaAprendiz;
   private String nombreIdiomaNativo;
   private int rankingPosicion;
   private int puntosTotales;
   private List<String> intereses = new ArrayList<>();
   private int cantidadEstrellas;
   private String urlBandera;
   private String nombrePais;
   private String urlFoto;
}
