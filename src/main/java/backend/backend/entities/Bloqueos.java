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
@Table(name="bloqueos")

public class Bloqueos extends Base{	
	@Column(name="fechaHoraAltaBloqueoRealizado")
	private Date fechaHoraAltaBloqueoRealizado;
	
	@Column(name="fechaHoraFinVigenciaBloqueoRealizado")
	private Date fechaHoraFinVigenciaBloqueoRealizado;
	
	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="fk_usuarioQueBloquea")
	private Cuenta usuarioQueBloquea;
	
	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="fk_usuarioBloqueado")
	private Cuenta usuarioBloqueado;
	
	
	
}
