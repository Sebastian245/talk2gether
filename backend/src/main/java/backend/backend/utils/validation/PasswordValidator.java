package backend.backend.utils.validation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
@Service
public class PasswordValidator {
    
    public void validarPassword(String password) {
        List<String> errores = new ArrayList<>();

        if(!noEsNula(password)){
            errores.add("La contraseña no puede estar vacia");
        }
        if (!tieneLongitudMinima(password)) {
            errores.add("La contraseña debe tener al menos 8 caracteres");
        }

        if (!tieneMayuscula(password)) {
            errores.add("La contraseña debe contener al menos 1 letra mayúscula");
        }

        if (!errores.isEmpty()) {
            throw new IllegalStateException(String.join(", ", errores));
        }
    }

    
    private boolean noEsNula(String password) {
        return password != null;
    }
    private boolean tieneLongitudMinima(String password) {
        return password.length() >= 8;
    }

    private boolean tieneMayuscula(String password) {
        return password.matches(".*[A-Z].*");
    }
}
