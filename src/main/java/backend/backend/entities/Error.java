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
@Table(name="error")

public class Error extends Base{	
	@Column(name="fechaHoraFinVigenciaError")
	private Date fechaHoraFinVigenciaError;
	
	@Column(name="fechaHoraAltaError")
	private Date fechaHoraAltaError;
	
	
	@Column(name="nombreError")
	private String nombreError;
	
	
	
}
