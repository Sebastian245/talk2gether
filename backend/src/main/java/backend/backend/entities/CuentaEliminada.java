package backend.backend.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cuentaeliminada")
public class CuentaEliminada extends Base {

    @Column(name = "fechaHoraAltaCuentaEliminada")
    private Date fechaHoraAltaCuentaEliminada;
    
    @Column(name = "idAdministradorReponsable")
    private Long idAdministradorReponsable;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CuentaEliminadaMotivo> listaCuentaEliminadaMotivo = new ArrayList<CuentaEliminadaMotivo>();

    @Column(name = "descripcionCuentaEliminada")
    private String descripcionCuentaEliminada;    
}
