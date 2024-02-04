import { Component } from '@angular/core';
import { VerificarCuentaService } from '../servicios/verificar-cuenta.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-verificar-cuenta',
  templateUrl: './verificar-cuenta.component.html',
  styleUrls: ['./verificar-cuenta.component.scss']
})
export class VerificarCuentaComponent {

  constructor(private verificarCuentaService: VerificarCuentaService) {}
  reenviarCorreo(){
    //const correo = localStorage.getItem('correo');
    //console.log(correo);
    this.verificarCuentaService.reenviarCorreo(localStorage.getItem('correo')).subscribe(
      response => {
        Swal.fire({
          position: 'center',
          icon: 'success',
          title: 'Correo enviado correctamente',
          showConfirmButton: false,
          timer: 3000
        })
      },
      error => {
        console.log(error);
      }
    );

  }
}
