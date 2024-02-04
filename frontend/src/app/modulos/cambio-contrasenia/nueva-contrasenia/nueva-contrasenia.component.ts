import { Component, EventEmitter, Input, OnInit, Output } from "@angular/core";
import { FormBuilder, FormGroup } from "@angular/forms";
import { CustomRegisterPasswordValidationsService } from "./custom-register-password-validations.service";
import { NavigationEnd, Router } from "@angular/router";
import { CambioContraseniaService } from "../servicios/cambio-contrasenia.service";
import Swal from "sweetalert2";


@Component({
  selector: 'app-nueva-contrasenia',
  templateUrl: './nueva-contrasenia.component.html',
  styleUrls: ['./nueva-contrasenia.component.scss']
})
export class NuevaContraseniaComponent implements OnInit {

  submitForm: boolean = false;
  errorService: boolean;
  

  contraseniaForm: FormGroup;
  showPassword: boolean = false;
  passwordType: string = 'password';
  showRepeatPassword: boolean = false;
  repeatPasswordType: string = 'password';
  tokenRecuperado: string;
  currentUrl:string;
  constructor(private fb: FormBuilder,
              private validation: CustomRegisterPasswordValidationsService,
              private router: Router,
              private cambioService :CambioContraseniaService){
  }

  ngOnInit(): void {
    const intermedia = this.router.url;
    this.tokenRecuperado = intermedia.split('?')[1];
    this.contraseniaForm = this.fb.group(
      {
        password1: [
          '',
          [
            this.validation.minLengthValidation,
            this.validation.oneUpperCaseValidation,
            this.validation.oneNumberValidation
          ],
        ],
        password2: [''],
      },
      { updateOn: 'change' }
    );
  }

  enviarContrasenia(){
    this.submitForm = true;
    if (this.contraseniaForm.invalid) return; 
    if ((this.contraseniaForm.get('password1').value) !== (this.contraseniaForm.get('password2').value)) {
      return;
    }
    this.cambioContrasenia(this.contraseniaForm.get('password1').value, this.tokenRecuperado);
  }

  togglePasswordView(fromRepeat: boolean = false) {
    if (!fromRepeat) {
      this.showPassword = !this.showPassword;
      if (this.showPassword) {
        this.passwordType = 'text';
      } else {
        this.passwordType = 'password';
      }
    } 
    else {
      this.showRepeatPassword = !this.showRepeatPassword;
      if (this.showRepeatPassword) {
        this.repeatPasswordType = 'text';
      } else {
        this.repeatPasswordType = 'password';
      }
    }
  }
  cambioContrasenia(pass:string, tokenRecuperado:string){

    this.cambioService.recuperarContrasenia(pass,tokenRecuperado)
    .subscribe(
      {
        next: response => {
          this.router.navigate(['confirmacion'])
        },
        error: error => {
          Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: '¡Algo anda mal! Intente nuevamente mas tarde.',
            confirmButtonColor: '#2b6a78',
          })
        }  
      }

    )
  }


}
