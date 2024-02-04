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
@Table(name="estadoReporteError")

public class EstadoReporteError extends Base{	
	@Column(name="fechaHoraAltaEstadoReporteError")
	private Date fechaHoraAltaEstadoReporteError;
	
	@Column(name="fechaHoraFinVigenciaEstadoReporteError")
	private Date fechaHoraFinVigenciaEstadoReporteError;
	
	@Column(name="nombreEstadoReporteError")
	private String nombreEstadoReporteError;
	
	
	
	
}
