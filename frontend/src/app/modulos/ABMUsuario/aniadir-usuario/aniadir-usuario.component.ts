import { Component, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { PasswordValidator } from '../../registro/registrar-usuario1/password-validator';
import { Location } from '@angular/common';
import { UsuarioService } from '../services/usuario.service';
import Swal from 'sweetalert2';
import { UsuarioPost } from '../modelo/usuarioPost';
import { SpinnerServiceGeneral } from 'src/app/compartidos/spinner-carga-general/spinner.service';

@Component({
  selector: 'app-aniadir-usuario',
  templateUrl: './aniadir-usuario.component.html',
  styleUrls: ['./aniadir-usuario.component.scss'],
})
export class AniadirUsuarioComponent {
  registroForm: FormGroup;
  patternEmail =
    /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|.(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
  fechaVacia: boolean = true;
  urlPrevia: string = '';

  showPassword: boolean = false;
  showRepeatPassword: boolean = false;

  rolesList: string[] = [];
  nuevoUsuario: UsuarioPost;
  constructor(
    private router: Router,
    private location: Location,
    private usuarioService: UsuarioService,
    private spinner: SpinnerServiceGeneral
  ) {}

  ngOnInit(): void {
    this.usuarioService.traerRoles().subscribe((response: any[]) => {
      for (const rol of response) {
        if(rol['nombreRol']!='usuario'){
          this.rolesList.push(rol['nombreRol']);
        }
      }
    });
    this.registroForm = new FormGroup(
      {
        nombreUsuario: new FormControl('', [
          Validators.required,
          Validators.pattern(/^[A-Za-z]+$/),
        ]),
        apellidoUsuario: new FormControl('', [
          Validators.required,
          Validators.pattern(/^[A-Za-z]+$/),
        ]),
        fechaNacimiento: new FormControl('', [Validators.required]),
        correo: new FormControl('', [
          Validators.pattern(this.patternEmail),
          Validators.required,
        ]),
        contrasenia: new FormControl('', [
          Validators.required,
          PasswordValidator.strong,
        ]),
        contrasenia2: new FormControl('', [Validators.required]),
        rol: new FormControl('', [Validators.required]),
      },
      {
        updateOn: 'change',
      }
    );

    const nacimiento = document.getElementById(
      'nacimiento'
    ) as HTMLInputElement;
    let today = new Date();
    let minAge = 18;
    nacimiento.max = new Date(
      today.getFullYear() - minAge,
      today.getMonth(),
      today.getDate()
    )
      .toISOString()
      .split('T')[0];
  }

  elegirFecha() {
    this.registroForm.get('fechaNacimiento').markAsTouched();
    const inputFecha = document.getElementById(
      'nacimiento'
    ) as HTMLInputElement;
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

  siguientePantalla() {
    this.spinner.showSpinner()
    this.nuevoUsuario = {
      nombreUsuario : this.registroForm.get('nombreUsuario').value,
      apellidoUsuario: this.registroForm.get('apellidoUsuario').value,
      fechaNacimiento: this.registroForm.get('fechaNacimiento').value,
      correo:this.registroForm.get('correo').value,
      contrasenia: this.registroForm.get('contrasenia').value,
      rol:this.registroForm.get('rol').value
    }
    this.usuarioService.agregarUsuario(this.nuevoUsuario).subscribe({
      next: (response)=>{
        this.spinner.hideSpinner()
        Swal.fire({
          position: 'top-end',
          icon: 'success',
          title: 'Usuario creado correctamente',
          showConfirmButton: false,
          timer: 2000,
        });
        this.router.navigate(['administrador', 'administrarUsuario']);
      },
      error: (error)=>{
        this.spinner.hideSpinner()
        if(error['error']=='El correo ingresado ya existe'){
          Swal.fire({
            icon: 'error',
            title: 'Oops...',
            confirmButtonColor: '#199DE8',
            text: 'El correo ingresado ya se encuentra ocupado',
          });
        } else{
          Swal.fire({
            icon: 'error',
            title: 'Oops...',
            confirmButtonColor: '#199DE8',
            text: 'Algo salió mal! Reinicie la página. Si el error persiste intente mas tarde.',
          });
        }
        
      }
    })
  }
  volver() {
    this.location.back();
  }
}
