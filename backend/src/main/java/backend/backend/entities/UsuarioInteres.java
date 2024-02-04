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
@Table(name="usuarioInteres")

public class UsuarioInteres extends Base{	
	@Column(name="fechaHoraAltaUsuarioInteres")
	private Date fechaHoraAltaUsuarioInteres;
	
	@Column(name="fechaHoraFinVigenciaInteres")
	private Date fechaHoraFinVigenciaInteres;
	

	
	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="fk_interes")
	private Interes interes;
	
	
	
}
