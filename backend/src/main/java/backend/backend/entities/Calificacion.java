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
@Table(name="calificacion")

public class Calificacion extends Base{	
	@Column(name="cantidadEstrellas")
	private int cantidadEstrellas;
	
	@Column(name="fechaHoraAltaCalificacion")
	private Date fechaHoraAltaCalificacion;
	
	@Column(name="fechaHoraFinVigenciaCalificacion")
	private Date fechaHoraFinVigenciaCalificacion;
	
	
	
	
}
