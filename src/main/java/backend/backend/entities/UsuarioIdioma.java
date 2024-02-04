package backend.backend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name="usuarioIdioma")

public class UsuarioIdioma extends Base{	
	
	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="fk_idioma")
	private Idioma idioma;
	
	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="fk_nivelIdioma")
	private NivelIdioma nivelIdioma;
	
	
	
}
