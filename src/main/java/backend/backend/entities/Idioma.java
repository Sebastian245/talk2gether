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
@Table(name="idioma")

public class Idioma extends Base{	
	@Column(name="fechaHoraAltaIdioma")
	private Date fechaHoraAltaIdioma;
	
	@Column(name="fechaHoraFinVigenciaIdioma")
	private Date fechaHoraFinVigenciaIdioma;
	
	
	@Column(name="nombreIdioma", nullable = false,length = 100)
	private String nombreIdioma;
	
	
	
}
