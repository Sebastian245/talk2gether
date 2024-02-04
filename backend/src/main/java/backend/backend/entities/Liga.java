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
@Table(name="liga")

public class Liga extends Base{	
	@Column(name="colorLiga")
	private String colorLiga;
	
	@Column(name="fechaHoraFinVigenciaLiga")
	private Date fechaHoraFinVigenciaLiga;
	
	@Column(name="fechaHoraAltaLiga")
	private Date fechaHoraAltaLiga;
	
	
	@Column(name="nombreLiga")
	private String nombreLiga;
	
	@Column(name="puntosDesde")
	private int puntosDesde;
	
	@Column(name="puntosHasta")
	private int puntosHasta;
	
	
	
}
