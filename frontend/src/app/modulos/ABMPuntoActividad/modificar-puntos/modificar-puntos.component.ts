import { Component, ViewEncapsulation } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ModalService } from '../../registro/servicios/modal.service';
import { Router } from '@angular/router';
import { SpinnerServiceGeneral } from 'src/app/compartidos/spinner-carga-general/spinner.service';
import Swal from 'sweetalert2';
import { PuntosPorActividadService } from '../service/puntos-por-actividad.service';
import { combineLatest } from 'rxjs-compat/operator/combineLatest';
import { switchMap } from 'rxjs-compat/operator/switchMap';
import { of, tap } from 'rxjs';

@Component({
  selector: 'app-modificar-puntos',
  templateUrl: './modificar-puntos.component.html',
  styleUrls: ['./modificar-puntos.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class ModificarPuntosComponent {
  puntosForm: FormGroup;
  id: number;
  submitForm: boolean = false;
  nombreActividad: string;
  puntos: number;
  modalRef: NgbModalRef;
  descripcion: string;
  puntosMaximos: number;
  tipoActividad: string;
  constructor(
    private modalService: ModalService,
    private puntosService: PuntosPorActividadService,
    private router: Router,
    private spinnerServiceGeneral: SpinnerServiceGeneral
  ) {}

  ngOnInit() {
    this.puntosService
      .getnombrePuntosPorActividad()
      .subscribe({ next: (response) => (this.nombreActividad = response) });
    this.puntosService
      .getpuntosPorActividad()
      .subscribe({ next: (response) => (this.puntos = response) });
    this.puntosService
      .getId()
      .subscribe({ next: (response) => (this.id = response) });
    this.puntosService
      .getdescripcion()
      .subscribe({ next: (response) => (this.descripcion = response) });
    this.puntosService
      .getpuntosMaximos()
      .subscribe({ next: (response) => (this.puntosMaximos = response) });
    this.puntosService
      .getTipoActividad()
      .subscribe({ next: (response) => (this.tipoActividad = response) });

    this.puntosForm = new FormGroup({
      puntos: new FormControl(this.puntos, [
        Validators.required,
        this.validarNumeros,
      ]),
      actividad: new FormControl(this.nombreActividad, [
        Validators.required,
        this.validarLetras,
      ]),
      descripcion: new FormControl(this.descripcion, [
        Validators.required
      ]),
      puntosMaximos: new FormControl(this.puntosMaximos, [
        Validators.required,
        this.validarNumeros,
      ]),
    });

    this.puntosService.getId().subscribe({
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
    if (this.puntosForm.invalid) return;
    this.spinnerServiceGeneral.showSpinner();
    this.puntosService
      .modificarPuntos(
        this.id,
        this.puntosForm.get('puntos').value,
        this.puntosForm.get('actividad').value,
        this.puntosForm.get('puntosMaximos').value,
        this.puntosForm.get('descripcion').value
      )
      .subscribe({
        next: (response) => {
          this.spinnerServiceGeneral.hideSpinner();
          this.dismissModal();
          this.reloadPage();
          Swal.fire({
            position: 'top-end',
            icon: 'success',
            title: 'Puntos actualizados correctamente',
            showConfirmButton: false,
            timer: 1500,
          });
        },
        error: (error) => {
          Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: error.error.mensaje,
          });

          this.dismissModal();
          this.spinnerServiceGeneral.hideSpinner();
        },
      });
  }

  validarNumeros(control) {
    const patron = /^[0-9]+$/; // Expresión regular que solo permite números
    if (!patron.test(control.value)) {
      return { soloNumeros: true };
    }
    return null;
  }

  reloadPage() {
    const currentUrl = this.router.url;
    this.router
      .navigateByUrl('/administrador', { skipLocationChange: true })
      .then(() => {
        this.router.navigateByUrl(currentUrl);
      });
  }

  validarLetras(control) {
    const patron = /^[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ ]+$/;
    if (!patron.test(control.value)) {
      return { soloLetras: true };
    }
    return null;
  }
}
