import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ModalService } from '../../registro/servicios/modal.service';
import { NavbarService } from 'src/app/compartidos/navbar/service/navbar.service';
import { SpinnerServiceGeneral } from 'src/app/compartidos/spinner-carga-general/spinner.service';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { PasswordValidator } from '../../registro/registrar-usuario1/password-validator';
import { CambiarContraseniaService } from '../servicios/cambiar-contrasenia.service';
import { SesionService } from 'src/app/interceptors/sesion.service';

@Component({
  selector: 'app-cambiar-contrasenia-modal',
  templateUrl: './cambiar-contrasenia-modal.component.html',
  styleUrls: ['./cambiar-contrasenia-modal.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class CambiarContraseniaModalComponent implements OnInit {
  idUsuario: number;
  cambioContraseniaForm: FormGroup;
  datosCambiarContrasenia = {};

  showOldPassword: boolean = false;
  showNewPassword: boolean = false;
  showRepeatedNewPassword: boolean = false;

  constructor(
    private modalService: ModalService,
    private navbarService: NavbarService,
    private spinner: SpinnerServiceGeneral,
    private cambiarContraseniaService: CambiarContraseniaService,
    private sesionService: SesionService,
    private router: Router
  ) { }

  ngOnInit() {
    this.cambioContraseniaForm = new FormGroup({
      oldPassword: new FormControl('', [Validators.required]),
      newPassword: new FormControl('', [Validators.required, PasswordValidator.strong]),
      repeatedNewPassword: new FormControl('', [Validators.required])
    }, {
      updateOn: 'change'
    });

    this.navbarService.traerUsuario().subscribe({
      next: response => {
        this.idUsuario = response['id'];
      },
      error: error => {
        localStorage.clear();
        sessionStorage.clear();
        localStorage.setItem('isLoggedIn', 'false');
        Swal.fire(
          'La sesión fue cerrada',
          'Inicie nuevamente para continuar en la plataforma',
          'question'
        )
        this.router.navigate([''])
      }
    });
  }

  cambiarContrasenia() {
    this.datosCambiarContrasenia = {
      "id": this.idUsuario,
      "contraseniaNueva": this.cambioContraseniaForm.get('newPassword').value,
      "contraseniaAntigua": this.cambioContraseniaForm.get('oldPassword').value
    }

    console.log(this.datosCambiarContrasenia);

    this.dismissModal();
    this.spinner.showSpinner();
    this.cambiarContraseniaService.cambiarContrasenia(this.datosCambiarContrasenia).subscribe({
      next: response => {
        this.spinner.hideSpinner();
        Swal.fire({
          position: 'top-end',
          icon: 'success',
          title: 'Contraseña modificada correctamente',
          showConfirmButton: false,
          timer: 1500
        });
        this.sesionService.clearLocalSession();
        this.router.navigate(['login']);

      },
      error: error => {
        this.spinner.hideSpinner();
        Swal.fire({
          icon: 'error',
          title: 'No fue posible modificar su contraseña',
          text: error.error,
        });
      }
    })

  }

  dismissModal() {
    this.modalService.dismissActiveModal();
  }

  toggleOldPasswordView(): void {
    this.showOldPassword = !this.showOldPassword;
  }

  toggleNewPasswordView(): void {
    this.showNewPassword = !this.showNewPassword;
  }

  toggleRepeatedNewPasswordView(): void {
    this.showRepeatedNewPassword = !this.showRepeatedNewPassword;
  }

}
