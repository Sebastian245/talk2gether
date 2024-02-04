package backend.backend.utils.functions;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class Functions {    
    public static int calcularEdad(Date fechaNacimiento) {
		LocalDate fechaNacimientoLocal = fechaNacimiento.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate fechaActual = LocalDate.now();
		Period periodo = Period.between(fechaNacimientoLocal, fechaActual);
		return periodo.getYears();
	}
	public static int promedioCalificacion(List<Long> listaUsuarioCalificacion) {
		double suma = 0;
		double promedio = 0;
		int cantidadCalificaciones = listaUsuarioCalificacion.size();
		if (cantidadCalificaciones != 0) {
			for (Long usuarioCalificacion : listaUsuarioCalificacion) {
				suma += usuarioCalificacion;
			}
			promedio = (suma / cantidadCalificaciones);

			BigDecimal decimalValue = BigDecimal.valueOf(promedio);
			int intValue = decimalValue.setScale(0, RoundingMode.HALF_UP).intValue();
			return (intValue);
		} else
			return 0;
	}
	public static String HOST="localhost:4200/";
	public static double cortarADosDecimales(double numero) {
		BigDecimal bd = BigDecimal.valueOf(numero);
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
}
