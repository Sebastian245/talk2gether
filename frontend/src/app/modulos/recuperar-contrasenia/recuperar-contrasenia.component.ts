import { Component } from '@angular/core';
import { RecuperarContraseniaService } from './servicios/recuperar-contrasenia.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-recuperar-contrasenia',
  templateUrl: './recuperar-contrasenia.component.html',
  styleUrls: ['./recuperar-contrasenia.component.scss']
})
export class RecuperarContraseniaComponent {
  submitError: boolean = false;
  mensajeError: string = '';
  mostrarMsjExitoso: boolean  = false;
  emailGuardado: string;
  constructor(private recuperar:RecuperarContraseniaService ) { }
  
  enviarMail(event){
    this.emailGuardado = event;
    this.recuperar.recuperarContrasenia(this.emailGuardado)
    .subscribe(
      {
        next: response => {
          this.mostrarMsjExitoso = true;
        },
        error: error => {
          console.log(error.error.mensaje)
          this.mensajeError = error.error.mensaje;
          this.submitError = true;
          this.mostrarMsjExitoso = false;
        }
      }

    )
  }

  reenviarMail(event){
    this.recuperar.recuperarContrasenia(event)
    .subscribe(
      response => {
        Swal.fire({
          position: 'center',
          icon: 'success',
          title: 'Correo reenviado correctamente',
          showConfirmButton: false,
          timer: 1500
        })
      },
      error => {
        console.log(error);
        Swal.fire({
          icon: 'error',
          title: 'Oops...',
          text: 'Algo no funciona correctamente. Intente mas tarde.',
          confirmButtonColor: '#2b6a78',
        })
      }
    )
  }
}