package backend.backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DTOPrevioCalificarUsuario {
    private long idReunion;
    private long idParticipanteDos;
    private String nombreParticipante;
    private String apellidoParticipante;

}
