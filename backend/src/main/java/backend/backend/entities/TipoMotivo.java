package backend.backend.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="tipomotivo")
public class TipoMotivo extends Base{

    @Column(name="fechaHoraAltaTipoMotivo")
    private Date fechaHoraAltaTipoMotivo;

    @Column(name="fechaHoraFinVigenciaTipoMotivo")
    private Date fechaHoraFinVigenciaTipoMotivo;

    @Column(name="nombreTipoMotivo")
    private String nombreTipoMotivo;
}
