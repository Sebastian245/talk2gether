package backend.backend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "reporteMotivo")

public class ReporteMotivo extends Base {

	@Column(name = "descripcionReporteMotivo")
	private String descripcionReporteMotivo;

	@Column(name = "fechaHoraAltaReporteMotivo")
	private Date fechaHoraAltaReporteMotivo;

	@Column(name = "fechaHoraFinVigenciaReporteMotivo")
	private Date fechaHoraFinVigenciaReporteMotivo;

	@Column(name = "idCuentaInformanteMotivo")
	private Long idCuentaInformanteMotivo;

	@Column(name = "idCuentaReportada")
	private Long idCuentaReportada;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "fk_motivo")
	private Motivo motivo;

}
