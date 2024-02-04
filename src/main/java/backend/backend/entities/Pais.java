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
@Table(name = "pais")

public class Pais extends Base {
	@Column(name = "fechaHoraAltaPais")
	private Date fechaHoraAltaPais;

	@Column(name = "fechaHoraFinVigenciaPais")
	private Date fechaHoraFinVigenciaPais;

	@Column(name = "nombrePais",length = 50)
	private String nombrePais;

	@Column(name = "urlBandera")
	private String urlBandera;

}
