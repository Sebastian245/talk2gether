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
@Table(name="seguidos")

public class Seguidos extends Base{	
	@Column(name="fechaHoraFinVigenciaSeguido")
	private Date fechaHoraFinVigenciaSeguido;
	
	@Column(name="fechaHoraAltaSeguido")
	private Date fechaHoraAltaSeguido;
	
	@Column(name="idCuenta")
	private Long idCuenta;
	
	
	
}
