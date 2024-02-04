package backend.backend.utils.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

@Service
public class TextValidator {
    
    public void validarTexto(String texto) {
        List<String> errores = new ArrayList<>();

        if (esNulo(texto)) {
            errores.add("El texto no puede ser nulo");
        }

        if (!tieneLongitudValida(texto)) {
            errores.add("El texto debe tener una longitud mayor a 0 y no mayor a 50");
        }

        if (!cumplePatron(texto)) {
            errores.add("El texto no puede contener números ni símbolos");
        }

        if (!errores.isEmpty()) {
            throw new IllegalStateException(String.join(", ", errores));
        }
    }

    private boolean esNulo(String texto) {
        return texto == null;
    }

    private boolean tieneLongitudValida(String texto) {
        return texto.length() > 0 && texto.length() <= 50;
    }

    private boolean cumplePatron(String texto) {
        String patron = "^[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ\\s]+$";
        return Pattern.matches(patron, texto);
    }
}

