package backend.backend.utils.validation;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NumberValidator {

    public void validarEntero(Integer numero) {
        List<String> errores = new ArrayList<>();

        if (esNulo(numero)) {
            errores.add("El número no puede ser nulo");
        }

        if (!esEntero(numero)) {
            errores.add("El número debe ser un entero válido");
        }

        if (!esMayorACero(numero)) {
            errores.add("El número debe ser mayor a 0");
        }

        if (!esMenorACien(numero)) {
            errores.add("El número debe ser menor a 1000");
        }

        if (!errores.isEmpty()) {
            throw new IllegalStateException(String.join(", ", errores));
        }
    }

    private boolean esNulo(Integer numero) {
        return numero == null;
    }

    private boolean esEntero(Integer numero) {
        return String.valueOf(numero).matches("^-?\\d+$");
    }

    private boolean esMayorACero(Integer numero) {
        return numero > 0;
    }

    private boolean esMenorACien(Integer numero) {
        return numero < 1000;
    }
}
