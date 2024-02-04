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
@Table(name="nivelIdioma")

public class NivelIdioma extends Base{	
	@Column(name="fechaHoraAltaNivelIdioma")
	private Date fechaHoraAltaNivelIdioma;
	
	@Column(name="fechaHoraFinVigenciaNivelIdioma")
	private Date fechaHoraFinVigenciaNivelIdioma;
	
	
	@Column(name="nombreNivelIdioma", nullable = false,length = 100)
	private String nombreNivelIdioma;
	
	
	
}
