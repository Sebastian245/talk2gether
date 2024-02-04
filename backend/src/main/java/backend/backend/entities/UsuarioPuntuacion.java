package backend.backend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;


@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name="usuarioPuntuacion")

public class UsuarioPuntuacion extends Base{	

	@Column(name="puntosTotales")
	private int puntosTotales;
	
	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="fk_liga")
	private Liga liga;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Puntuacion> listaPuntuacion = new ArrayList<Puntuacion>();
	
}
