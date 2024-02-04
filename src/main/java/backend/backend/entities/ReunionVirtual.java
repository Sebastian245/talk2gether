package backend.backend.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name="reunionVirtual")

public class ReunionVirtual extends Base{	
	
	@Column(name="duracionReunionVirtual")
	private double duracionReunionVirtual;
	
	@Column(name="fechaHoraAltaReunionVirtual")
	private Date fechaHoraAltaReunionVirtual;

	@Column(name="fechaHoraFinVigenciaReunionVirtual")
	private Date fechaHoraFinVigenciaReunionVirtual;
	
	@Column(name="linkReunionVirtual")
	private String linkReunionVirtual;
	
	@OneToMany(cascade=CascadeType.ALL, orphanRemoval=true)
	private List<DetalleParticipante> listaDetalleParticipantes = new ArrayList<DetalleParticipante>();
	
	
	
}
