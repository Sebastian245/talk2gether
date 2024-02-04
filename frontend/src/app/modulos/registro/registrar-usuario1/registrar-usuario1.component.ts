import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { PasswordValidator } from './password-validator';
import { NuevoUsuario } from '../modelos/nuevo-usuario';

@Component({
  selector: 'app-registrar-usuario1',
  templateUrl: './registrar-usuario1.component.html',
  styleUrls: ['./registrar-usuario1.component.scss']
})
export class RegistrarUsuario1Component implements OnInit{
  registroForm: FormGroup;
  patternEmail = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|.(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
  fechaVacia: boolean = true;
  urlPrevia: string = '';

  showPassword: boolean = false;
  showRepeatPassword: boolean = false;

  @Output() enviarData: EventEmitter<any> = new EventEmitter();

  constructor(private router: Router) {}

  ngOnInit(): void {
    this.registroForm = new FormGroup({
      nombreUsuario: new FormControl('', [Validators.required, Validators.pattern(/^[A-Za-zÁÉÍÓÚáéíóúÑñÜü]+$/)]),
      apellidoUsuario: new FormControl('', [Validators.required, Validators.pattern(/^[A-Za-zÁÉÍÓÚáéíóúÑñÜü]+$/)]),
      fechaNacimiento: new FormControl('', [Validators.required]),
      correo: new FormControl('', [Validators.pattern(this.patternEmail), Validators.required]),
      contrasenia: new FormControl('', [Validators.required, PasswordValidator.strong]),
      contrasenia2: new FormControl('', [Validators.required])
    }, {
      updateOn: 'change'
    });

    const nacimiento = document.getElementById('nacimiento') as HTMLInputElement;
    let today = new Date();
    let minAge = 18;
    nacimiento.max = new Date(today.getFullYear() - minAge, today.getMonth(), today.getDate()).toISOString().split("T")[0];

    if(sessionStorage.getItem('previousUrl')=='/login'){
      this.urlPrevia = 'login';
    }

    if(sessionStorage.getItem("nombreUsuario")){
      const inputFecha = document.getElementById("nacimiento") as HTMLInputElement;
      inputFecha.value = sessionStorage.getItem("fechaNacimiento")
      this.registroForm.get('nombreUsuario').setValue(sessionStorage.getItem("nombreUsuario"));
      this.registroForm.get('apellidoUsuario').setValue(sessionStorage.getItem("apellidoUsuario"));
      this.registroForm.get('fechaNacimiento').setValue(inputFecha.value);
      this.registroForm.get('correo').setValue(sessionStorage.getItem("correo"));
    }

  }

  elegirFecha() {
    this.registroForm.get('fechaNacimiento').markAsTouched();
    const inputFecha = document.getElementById("nacimiento") as HTMLInputElement;
    inputFecha.oninput = (event) => {
      const fechaSeleccionada = inputFecha.value;
      if (fechaSeleccionada != '') {
        this.fechaVacia = false;
        this.registroForm.get('fechaNacimiento').setErrors(null);
        this.registroForm.get('fechaNacimiento').setValue(inputFecha.value);
      } else {
        this.fechaVacia = true;
        this.registroForm.get('fechaNacimiento').setErrors({ required: true });
      }
    };
  }

  togglePasswordView(): void {
    this.showPassword = !this.showPassword;
  }

  toggleRepeatPasswordView(): void {
    this.showRepeatPassword = !this.showRepeatPassword;
  }

  siguientePantalla(){
    const nuevoUsuario = new NuevoUsuario(
      this.registroForm.get('nombreUsuario').value,
      this.registroForm.get('apellidoUsuario').value,
      this.registroForm.get('fechaNacimiento').value,
      this.registroForm.get('correo').value,
      this.registroForm.get('contrasenia').value
    );
    sessionStorage.setItem("nombreUsuario", nuevoUsuario.nombreUsuario);
    sessionStorage.setItem("apellidoUsuario", nuevoUsuario.apellidoUsuario);
    sessionStorage.setItem("fechaNacimiento", nuevoUsuario.fechaNacimiento);
    sessionStorage.setItem("correo", nuevoUsuario.correo);
    this.enviarData.emit(nuevoUsuario);
  }

  volver(){
    this.router.navigate([this.urlPrevia]);
  }

}