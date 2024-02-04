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
@Table(name="puntuacion")

public class Puntuacion extends Base{	
	@Column(name="cantidadPuntos")
	private int cantidadPuntos;
	
	@Column(name="fechaPuntuacion")
	private Date fechaPuntuacion;
	

	
	
	
}
