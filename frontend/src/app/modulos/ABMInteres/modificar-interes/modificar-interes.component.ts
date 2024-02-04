import { Component, ViewEncapsulation } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ModalService } from '../../registro/servicios/modal.service';
import { Router } from '@angular/router';
import { SpinnerServiceGeneral } from 'src/app/compartidos/spinner-carga-general/spinner.service';
import Swal from 'sweetalert2';
import { InteresService } from '../service/interes.service';
import { Observable, combineLatest, of, switchMap, tap } from 'rxjs';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { InfoInteresComponent } from '../info-interes/info-interes.component';

@Component({
  selector: 'app-modificar-interes',
  templateUrl: './modificar-interes.component.html',
  styleUrls: ['./modificar-interes.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class ModificarInteresComponent {
  modalRef: NgbModalRef;
  interesForm: FormGroup;
  id: number;
  submitForm: boolean = false;
  nombreInteres: string;
  icono: string;
  isInteres$ : Observable<any>
  constructor(
    private modalService: ModalService,
    private interesService: InteresService,
    private route: Router,
    private spinnerServiceGeneral: SpinnerServiceGeneral
  ) {}

  ngOnInit() {
    this.spinnerServiceGeneral.showSpinner();

    this.isInteres$ = combineLatest([
      this.interesService.getInteres(),
      this.interesService.getIcono(),
      this.interesService.getId()
    ]).pipe(
      tap(() => {
        this.spinnerServiceGeneral.hideSpinner();
      }),
      switchMap(([interes, icono,id]) => {
        this.nombreInteres = interes
        this.icono = icono
        this.id = id
        this.interesForm = new FormGroup({
          interes: new FormControl(this.nombreInteres, [
            Validators.required,
            this.validarLetras,
          ]),
          urlIcono: new FormControl(this.icono, Validators.required),
        });
        return of(true);
      })
    );




  }

  dismissModal() {
    this.modalService.dismissActiveModal();
  }
  abrirInfo(){
    this.modalRef = this.modalService.open(InfoInteresComponent, {
      centered: true,
    });
  }
  enviarDatos() {
    this.submitForm = true;
    if (this.interesForm.invalid) return;
    this.spinnerServiceGeneral.showSpinner();
    this.interesService
      .modificarInteres(
        this.id,
        this.interesForm.get('interes').value,
        this.interesForm.get('urlIcono').value
      )
      .subscribe({
        next: (response) => {
          this.spinnerServiceGeneral.hideSpinner();
          this.dismissModal();
          this.reloadPage();
          Swal.fire({
            position: 'top-end',
            icon: 'success',
            title: 'Interes actualizado correctamente',
            showConfirmButton: false,
            timer: 1500,
          });
        },
        error: (error) => {
          if (error.error.mensaje === 'Ya existe un interés con el mismo nombre.') {
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
    const patron = /^[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ ]+$/;
    if (!patron.test(control.value)) {
      return { soloLetras: true };
    }
    return null;
  }

  reloadPage() {
    const currentUrl = this.route.url;
    this.route.navigateByUrl('/administrador', { skipLocationChange: true }).then(() => {
      this.route.navigateByUrl(currentUrl);
    });
  }
}
