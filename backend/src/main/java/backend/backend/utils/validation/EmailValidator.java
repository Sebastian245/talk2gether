package backend.backend.utils.validation;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;
@Service
public class EmailValidator implements Predicate<String> {
    
    public void validarEmail(String email) {
        List<String> errores = new ArrayList<>();

        if (!tieneLongitudMinima(email)) {
            errores.add("El correo no puede ser nulo");
        }

        if (!cumpleLongitudMaxima(email)) {
            errores.add("El correo excede la longitud máxima permitida");
        }

        if (!cumpleEstructura(email)) {
            errores.add("El correo no cumple con la estructura requerida");
        }

        if (!errores.isEmpty()) {
            throw new IllegalStateException(String.join(", ", errores));
        }
    }

    private boolean tieneLongitudMinima(String email) {
        return email.length() > 5;
    }

    private boolean cumpleLongitudMaxima(String email) {
        return email.length() <= 250;
    }

    private boolean cumpleEstructura(String email) {
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }
    


    @Override
    public boolean test(String arg0) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'test'");
    }
}