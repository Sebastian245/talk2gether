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
@Table(name="motivo")

public class Motivo extends Base{	
	@Column(name="fechaHoraFinVigenciaMotivo")
	private Date fechaHoraFinVigenciaMotivo;
	
	@Column(name="fechaHoraAltaMotivo")
	private Date fechaHoraAltaMotivo;
	
	
	@Column(name="nombreMotivo")
	private String nombreMotivo;

	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="fk_tipoMotivo")
    private TipoMotivo tipoMotivo;
	
	
	
}
