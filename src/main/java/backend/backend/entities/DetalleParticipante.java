package backend.backend.entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name="detalleParticipante")

public class DetalleParticipante extends Base{	
	@Column(name="fechaHoraFinDetalleParticipante")
	private Date fechaHoraFinDetalleParticipante;
	
	@Column(name="fechaHoraInicioDetalleParticipante")
	private Date fechaHoraInicioDetalleParticipante;
	
	
	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="fk_participante")
	private Usuario participante;
	
	
	
}
