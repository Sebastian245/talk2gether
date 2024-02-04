package backend.backend.utils.validation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

@Service
public class FechaValidator {
    private static final String FORMATO_FECHA = "yyyy-MM-dd";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(FORMATO_FECHA);

    public void validarFecha(Date fecha) {
        List<String> errores = new ArrayList<>();

        if (fecha == null) {
            errores.add("La fecha no puede ser nula");
        } else {
            if (esFechaFutura(fecha)) {
                errores.add("La fecha no puede ser futura");
            }

            if (!esMayorDe18Anios(fecha)) {
                errores.add("La persona debe tener al menos 18 años");
            }

            if (esMenorDe100Anios(fecha)) {
                errores.add("La persona no puede tener más de 100 años");
            }

            if (!cumplePatron(fecha)) {
                errores.add("La fecha solo puede contener números, guiones y el formato (año-mes-dia)");
            }
        }

        if (!errores.isEmpty()) {
            throw new IllegalStateException(String.join(", ", errores));
        }
    }

    private boolean esFechaFutura(Date fecha) {
        Date fechaActual = new Date();
        return fecha.after(fechaActual);
    }

    private boolean esMayorDe18Anios(Date fecha) {
        Date fechaActual = new Date();
        Date fechaMayor18Anios = restarAnios(fechaActual, 18);
        return fecha.before(fechaMayor18Anios);
    }

    private boolean esMenorDe100Anios(Date fecha) {
        Date fechaActual = new Date();
        Date fechaMenor100Anios = restarAnios(fechaActual, 100);
        return fecha.before(fechaMenor100Anios);
    }

    private Date restarAnios(Date fecha, int anios) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.YEAR, -anios);
        return calendar.getTime();
    }

    private boolean cumplePatron(Date fecha) {
        String fechaString = DATE_FORMAT.format(fecha);
        String patron = "^[0-9]{4}-[0-9]{2}-[0-9]{2}$";
        if (!Pattern.matches(patron, fechaString)) {
            return false;
        }
        try {
            DATE_FORMAT.setLenient(false); // Disable lenient parsing
            DATE_FORMAT.parse(fechaString); // Attempt to parse the date string
            return true; // Parsing successful, the date is valid
        } catch (ParseException e) {
            return false; // Parsing failed, the date is invalid
        }
    }
}
