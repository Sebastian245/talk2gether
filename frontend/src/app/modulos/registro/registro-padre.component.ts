import { Component } from '@angular/core';
import { NuevoUsuario } from './modelos/nuevo-usuario';
import Swal from 'sweetalert2';
import { RegistroService } from './servicios/registro.service';
import { ActivatedRoute, Router } from '@angular/router';
import { NuevoUsuario2 } from './modelos/nuevo-usuario2';
import { Usuario } from './modelos/usuario';
import { TitleCasePipe } from '@angular/common';

@Component({
  selector: 'app-registro-padre',
  templateUrl: './registro-padre.component.html',
  styleUrls: ['./registro-padre.component.scss']
})
export class RegistroPadreComponent {
  pantalla2: boolean = false;

  nuevoUsuario: NuevoUsuario;
  nuevoUsuario2: NuevoUsuario2;

  constructor(
    private registroService: RegistroService,
    private titleCase: TitleCasePipe,
    private router: Router,
    private route: ActivatedRoute) {}

  recibirDataPantalla1($event){
    this.nuevoUsuario = $event;
    this.pantalla2 = true;
  }

  recibirDataPantalla2($event){
    this.nuevoUsuario2 = $event;
    this.registrarUsuario();
  }

  volverPantalla1($event){
    this.pantalla2 = $event;
  }

  registrarUsuario(){
    const usuario = new Usuario(
      this.titleCase.transform(this.nuevoUsuario.nombreUsuario),
      this.titleCase.transform(this.nuevoUsuario.apellidoUsuario),
      this.nuevoUsuario.fechaNacimiento,
      this.nuevoUsuario.correo,
      this.nuevoUsuario.contrasenia,
      this.nuevoUsuario2.nombrePais,
      this.nuevoUsuario2.nombreIdiomaNativo,
      this.nuevoUsuario2.urlFoto,
      this.nuevoUsuario2.descripcion,
      this.nuevoUsuario2.nombreIdiomaAprendiz,
      this.nuevoUsuario2.nombreNivelIdiomaAprendiz,
      this.nuevoUsuario2.nombreIntereses
    );

    Swal.fire({
      title: 'Registrando usuario...',
      allowOutsideClick: false,
      didOpen: () => {
        Swal.showLoading();
        this.route.queryParams.subscribe(params => {
          const referidoValue = params['referido'];
          if (referidoValue) {
            this.registroService.crearReferido(usuario, referidoValue).subscribe({
              next: response => {
                Swal.hideLoading();
                this.mensajeConfirmacion();
                this.router.navigate(['verificar-cuenta']);
                sessionStorage.clear();
                localStorage.setItem('correo', this.nuevoUsuario.correo);
              },
              error: error => {
                Swal.hideLoading();
                this.mensajeError(error);
              }
            })
          } else {
            this.registroService.crear(usuario).subscribe({
              next: response => {
                Swal.hideLoading();
                this.mensajeConfirmacion();
                this.router.navigate(['verificar-cuenta']);
                sessionStorage.clear();
                localStorage.setItem('correo', this.nuevoUsuario.correo);
              },
              error: error => {
                Swal.hideLoading();
                this.mensajeError(error);
                console.log(error);
              }
            });
          }
        });
      }
    });
  }

  mensajeConfirmacion(){
    const Toast = Swal.mixin({
      toast: true,
      position: 'top-end',
      showConfirmButton: false,
      timer: 3000,
      timerProgressBar: true,
      didOpen: (toast) => {
        toast.addEventListener('mouseenter', Swal.stopTimer)
        toast.addEventListener('mouseleave', Swal.resumeTimer)
      }
    })
    Toast.fire({
      icon: 'success',
      title: 'Usuario creado correctamente'
    })
  }

  mensajeError(error){
    const Toast = Swal.mixin({
      toast: true,
      position: 'top-end',
      showConfirmButton: false,
      timer: 5000,
      timerProgressBar: true,
      didOpen: (toast) => {
        toast.addEventListener('mouseenter', Swal.stopTimer)
        toast.addEventListener('mouseleave', Swal.resumeTimer)
      }
    })
    Toast.fire({
      icon: 'error',
      title: 'Error en el registro de usuario.',
      text: error.error
    })
  }
}
