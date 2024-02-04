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
@Table(name="logro")

public class Logro extends Base{	
	
	@Column(name="fechaHoraAltaLogro")
	private Date fechaHoraAltaLogro;
	
	@Column(name="fechaHoraFinVigenciaLogro")
	private Date fechaHoraFinVigenciaLogro;
	
	
	@Column(name="nombreLogro",length = 100)
	private String nombreLogro;
	
}
