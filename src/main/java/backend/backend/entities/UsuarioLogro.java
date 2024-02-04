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
@Table(name="usuarioLogro")

public class UsuarioLogro extends Base{	
	@Column(name="fechaLogroConseguido")
	private Date fechaLogroConseguido;
	
	
	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="fk_Logro")
	private Logro Logro;
	
	
	
}
