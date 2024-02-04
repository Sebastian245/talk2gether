package backend.backend.entities;


import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cuentaeliminadamotivo")
public class CuentaEliminadaMotivo extends Base{
    
    @Column(name="fechaHoraAltaCuentaEliminadaMotivo")
    private Date fechaHoraAltaCuentaEliminadaMotivo;

    @Column(name="fechaHoraFinVigenciaCuentaEliminadaMotivo")
    private Date fechaHoraFinVigenciaCuentaEliminadaMotivo;
    
    @ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="fk_motivo")
    private Motivo motivo;
}
