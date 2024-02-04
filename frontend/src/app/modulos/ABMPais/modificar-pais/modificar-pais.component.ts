import { Component, ViewEncapsulation } from '@angular/core';
import { ModalService } from '../../registro/servicios/modal.service';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { PaisService } from '../service/pais.service';
import { Router } from '@angular/router';

import { SpinnerServiceGeneral } from 'src/app/compartidos/spinner-carga-general/spinner.service';
import Swal from 'sweetalert2';
import { InfoPaisComponent } from '../info-pais/info-pais.component';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-modificar-pais',
  templateUrl: './modificar-pais.component.html',
  styleUrls: ['./modificar-pais.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class ModificarPaisComponent {
  paisForm: FormGroup;
  id: number;
  submitForm: boolean = false;
  nombrePais: string;
  urlPais: string;
  modalRef: NgbModalRef;

  constructor(
    private modalService: ModalService,
    private paisService: PaisService,
    private router: Router,
    private spinnerServiceGeneral: SpinnerServiceGeneral
  ) {}

  ngOnInit() {
    this.paisService.getNombre().subscribe({next: (response)=> this.nombrePais = response})
    this.paisService.getUrlPais().subscribe({next: (response)=> this.urlPais = response})

    this.paisForm = new FormGroup({
      pais: new FormControl(this.nombrePais, [Validators.required, this.validarLetras]),
      urlPais: new FormControl(this.urlPais, [Validators.required, this.validarLetras]),
    });

    this.paisService.getId().subscribe({
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
    if (this.paisForm.invalid) return;
    this.spinnerServiceGeneral.showSpinner();
    this.paisService
      .modificarPais(
        this.id,
        this.paisForm.get('pais').value,
        this.paisForm.get('urlPais').value
      )
      .subscribe({
        next: (response) => {
          this.spinnerServiceGeneral.hideSpinner();
          this.dismissModal();
          this.reloadPage();
          Swal.fire({
            position: 'top-end',
            icon: 'success',
            title: 'País actualizado correctamente',
            showConfirmButton: false,
            timer: 1500,
          });
        },
        error: (error) => {
          if (error.error.mensaje === 'Ya existe un país con el mismo nombre.') {
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
  abrirInfo(){
    this.modalRef = this.modalService.open(InfoPaisComponent, {
      centered: true,
    });
  }
}
