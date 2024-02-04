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
@Table(name="seguidores")

public class Seguidores extends Base{	
	@Column(name="fechaHoraAltaSeguidor")
	private Date fechaHoraAltaSeguidor;
	
	@Column(name="fechaHoraFinVigenciaSeguidor")
	private Date fechaHoraFinVigenciaSeguidor;
	
	@Column(name="idCuenta")
	private Long idCuenta;
	
	
	
}
