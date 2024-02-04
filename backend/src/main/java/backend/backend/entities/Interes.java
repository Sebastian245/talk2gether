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
@Table(name = "interes")

public class Interes extends Base {
	@Column(name = "fechaHoraAltaInteres")
	private Date fechaHoraAltaInteres;

	@Column(name = "fechaHoraFinVigenciaInteres")
	private Date fechaHoraFinVigenciaInteres;

	@Column(name = "nombreInteres", length = 50)
	private String nombreInteres;

	@Column(name = "urlInteres")
	private String urlInteres;
}
