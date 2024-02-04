import { Component, ViewEncapsulation } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ModalService } from '../../registro/servicios/modal.service';
import { PaisService } from '../../ABMPais/service/pais.service';
import { Router } from '@angular/router';
import { SpinnerServiceGeneral } from 'src/app/compartidos/spinner-carga-general/spinner.service';
import Swal from 'sweetalert2';
import { RolService } from '../service/rol.service';

@Component({
  selector: 'app-modificar-rol',
  templateUrl: './modificar-rol.component.html',
  styleUrls: ['./modificar-rol.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class ModificarRolComponent {
  rolForm: FormGroup;
  id: number;
  submitForm: boolean = false;
  nombreRol: string;
  constructor(
    private modalService: ModalService,
    private rolService: RolService,
    private router: Router,
    private spinnerServiceGeneral: SpinnerServiceGeneral
  ) {}

  ngOnInit() {
    this.rolService.getNombre().subscribe({next: (response)=> this.nombreRol = response})

    this.rolForm = new FormGroup({
      rol: new FormControl(this.nombreRol, [Validators.required, this.validarLetras]),
    });

    this.rolService.getId().subscribe({
      next: (response) => {
        this.id = response;
      },
    });
  }

  dismissModal() {
    this.modalService.dismissActiveModal();
  }

  enviarDatos() {
    this.submitForm = true;
    if (this.rolForm.invalid) return;
    this.spinnerServiceGeneral.showSpinner();
    this.rolService
      .modificarRol(
        this.id,
        this.rolForm.get('rol').value
      )
      .subscribe({
        next: (response) => {
          this.spinnerServiceGeneral.hideSpinner();
          this.dismissModal();
          this.reloadPage();
          Swal.fire({
            position: 'top-end',
            icon: 'success',
            title: 'Rol actualizado correctamente',
            showConfirmButton: false,
            timer: 1500,
          });
        },
        error: (error) => {
          if (error.error.mensaje === 'Ya existe un rol con el mismo nombre.') {
            Swal.fire({
              icon: 'error',
              title: 'Oops...',
              text: error.error.mensaje,
            });
          } else {
            Swal.fire({
              icon: 'error',
              title: 'Oops...',
              text: 'Algo salió mal! Intente nuevamente mas tarde.',
            });
          }
          this.dismissModal();
          this.spinnerServiceGeneral.hideSpinner();
        },
      });
  }

  validarLetras(control) {
    const patron = /^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$/; 
    if (!patron.test(control.value)) {
      return { soloLetras: true };
    }
    return null;
  }

  reloadPage() {
    const currentUrl = this.router.url;
    this.router.navigateByUrl('/administrador', { skipLocationChange: true }).then(() => {
      this.router.navigateByUrl(currentUrl);
    });
  }
}
