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
@Table(name="puntosPorActividad")

public class PuntosPorActividad extends Base{	
	@Column(name="fechaHoraAltaPuntosPorActividad")
	private Date fechaHoraAltaPuntosPorActividad;
	
	@Column(name="fechaHoraFinVigenciaPuntosPorActividad")
	private Date fechaHoraFinVigenciaPuntosPorActividad;
	
	@Column(name="nombrePuntosPorActividad")
	private String nombrePuntosPorActividad;
	
	@Column(name="puntosPorActividad")
	private int puntosPorActividad;
	
	@Column(name="descripcionPuntosPorActividad")
	private String descripcionPuntosPorActividad;
	
	@Column(name="puntosMaximos")
	private int puntosMaximos;

	@Column(name="tipoPuntosPorActividad")
	private String tipoPuntosPorActividad;
	
}
