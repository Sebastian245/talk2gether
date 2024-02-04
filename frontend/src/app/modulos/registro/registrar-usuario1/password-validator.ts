import { FormControl } from '@angular/forms';

export interface ValidationResult {
    [key: string]: boolean;
}

export class PasswordValidator {

    public static strong(control: FormControl): ValidationResult {
        let hasNumber = /\d/.test(control.value);
        let hasUpper = /[A-Z]/.test(control.value);
        let tamanio: boolean = false;

        if (control.value.length >= 8) {
            tamanio = true;
        }

        const valid = hasNumber && hasUpper && tamanio;
        if (!valid) {
            return { strong: true };
        }
        return null;
    }

}