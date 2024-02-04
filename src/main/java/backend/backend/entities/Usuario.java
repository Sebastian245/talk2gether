package backend.backend.entities;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "usuario")

public class Usuario extends Base {

	@Column(name = "nombreUsuario", nullable = false, length = 50)
	private String nombreUsuario;

	@Column(name = "apellidoUsuario", nullable = false, length = 50)
	private String apellidoUsuario;

	@Column(name = "fechaNacimiento")
	private Date fechaNacimiento;

	@Column(name = "descripcionUsuario", length = 200)
	private String descripcionUsuario;

	@OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
	private List<UsuarioLogro> listaUsuarioLogro = new ArrayList<UsuarioLogro>();

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_usuarioPuntuacion")
	private UsuarioPuntuacion usuarioPuntuacion;

	@OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
	private List<UsuarioInteres> listaIntereses = new ArrayList<UsuarioInteres>();

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "fk_idiomaAprendiz")
	private UsuarioIdioma idiomaAprendiz;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "fk_idiomaNativo")
	private UsuarioIdioma idiomaNativo;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<UsuarioCalificacion> listaUsuarioCalificacion = new ArrayList<UsuarioCalificacion>();

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "fk_pais", nullable = true)
	private Pais pais;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_cuenta")
	private Cuenta cuenta;

	public int calcularEdad() {
		LocalDate fechaNacimientoLocal = fechaNacimiento.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate fechaActual = LocalDate.now();
		Period periodo = Period.between(fechaNacimientoLocal, fechaActual);
		return periodo.getYears();
	}

	public List<String> interesesToString() {
		List<String> listaInteresString = new ArrayList<>();
		for (UsuarioInteres interes : listaIntereses) {
			if(interes.getFechaHoraFinVigenciaInteres()==null){
			listaInteresString.add(interes.getInteres().getNombreInteres());
			}
		}
		return listaInteresString;
	}

	public int promedioCalificacion() {
		double suma = 0;
		double promedio = 0;
		int cantidadCalificaciones = listaUsuarioCalificacion.size();
		if (cantidadCalificaciones != 0) {
			for (UsuarioCalificacion usuarioCalificacion : listaUsuarioCalificacion) {
				suma += usuarioCalificacion.getCalificacion().getCantidadEstrellas();
			}
			promedio = (suma / cantidadCalificaciones);

			BigDecimal decimalValue = BigDecimal.valueOf(promedio);
			int intValue = decimalValue.setScale(0, RoundingMode.HALF_UP).intValue();
			return (intValue);
		} else
			return 0;
	}

	public int calculoPuntosTotales() {
		return usuarioPuntuacion.getPuntosTotales();
	}
}
