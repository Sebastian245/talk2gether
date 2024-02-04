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
@Table(name="reporteError")

public class ReporteError extends Base{	

	@Column(name="descripcionReporteError")
	private String descripcionReporteError;
	
	@Column(name="fechaHoraAltaReporteError")
	private Date fechaHoraAltaReporteError;
	
	@Column(name="fechaHoraFinVigenciaReporteError")
	private Date fechaHoraFinVigenciaReporteError;
	
	@Column(name="idCuentaAdministradorError")
	private int idCuentaAdministradorError;
	
	@Column(name="idCuentaInformanteError")
	private int idCuentaInformanteError;
	

	
	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="fk_error")
	private Error error;
	
	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="fk_estadoReporteError")
	private EstadoReporteError estadoReporteError;
	
	
	
}
