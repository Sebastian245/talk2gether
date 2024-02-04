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
@Table(name = "usuarioCalificacion")

public class UsuarioCalificacion extends Base {
	@Column(name = "fechaCalificacion")
	private Date fechaCalificacion;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "fk_calificacion")
	private Calificacion calificacion;

	@Column(name="idUsuarioCalificador")
	private Long idUsuarioCalificador;

	@Column(name="idReunionVirtual")
	private Long idReunionVirtual;

}
