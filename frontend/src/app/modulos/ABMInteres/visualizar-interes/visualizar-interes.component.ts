import { Component, ViewEncapsulation } from '@angular/core';
import Swal from 'sweetalert2';
import { SpinnerServiceGeneral } from 'src/app/compartidos/spinner-carga-general/spinner.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ModalService } from '../../registro/servicios/modal.service';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Interes } from '../modelo/interes';
import { InteresService } from '../service/interes.service';
import { ModificarInteresComponent } from '../modificar-interes/modificar-interes.component';
import { AniadirInteresComponent } from '../aniadir-interes/aniadir-interes.component';

@Component({
  selector: 'app-visualizar-interes',
  templateUrl: './visualizar-interes.component.html',
  styleUrls: ['./visualizar-interes.component.scss'],
})
export class VisualizarInteresComponent {
  interesList: Interes[] = [];
  modalRef: NgbModalRef;
  nuevoInteres: Interes;
  icono: string;
  constructor(
    private interesService: InteresService,
    private spinnerServiceGeneral: SpinnerServiceGeneral,
    private route: Router,
    private router: ActivatedRoute,
    private modalService: ModalService
  ) {}

  ngOnInit(): void {
    this.spinnerServiceGeneral.showSpinner();
    this.interesService.traerInteres().subscribe({
      next: (intereses: any[]) => {
        for (const interes of intereses) {
          this.nuevoInteres = {
            id: interes['id'],
            fechaHoraAltaInteres: interes['fechaHoraAltaInteres'],
            fechaHoraFinVigenciaInteres: interes['fechaHoraFinVigenciaInteres'],
            nombreInteres: interes['nombreInteres'],
            urlInteres: interes['urlInteres']
          };
          this.interesList.push(this.nuevoInteres);
          this.ordenarLista();
        }
        this.spinnerServiceGeneral.hideSpinner();
      },
      error: (error) => {
        Swal.fire({
          icon: 'error',
          title: 'Oops...',
          confirmButtonColor: '#199DE8',
          text: 'Algo salió mal! Reinicie la página. Si el error persiste intente mas tarde.',
        });
        this.spinnerServiceGeneral.hideSpinner();
      },
    });
  }

  ordenarLista() {
    this.interesList.sort((a, b) => {
      if (
        a.fechaHoraFinVigenciaInteres === null &&
        b.fechaHoraFinVigenciaInteres !== null
      ) {
        return -1;
      } else if (
        a.fechaHoraFinVigenciaInteres !== null &&
        b.fechaHoraFinVigenciaInteres === null
      ) {
        return 1;
      } else {
        return 0;
      }
    });
  }

  modificarInteres(id: number, nombreInteres: string, icono:string) {
    this.interesService.guardarId(id);
    this.interesService.guardarInteres(nombreInteres);
    this.interesService.guardarIcono(icono)
    this.modalRef = this.modalService.open(ModificarInteresComponent, {
      centered: true,
    });
  }

  aniadirInteres() {
    this.modalRef = this.modalService.open(AniadirInteresComponent, {
      centered: true,
    });
  }

  desHabilitarInteres(id: number, nombreInteres: string) {
    Swal.fire({
      title:
        '¿Estás seguro que deseas deshabilitar el interés  ' +
        nombreInteres +
        '?',
      icon: 'warning',
      showCancelButton: true,
      cancelButtonColor: '#909293',
      confirmButtonColor: '#199DE8',
      background: 'FFFFFF',
      confirmButtonText: 'Si, deshabilitar',
      cancelButtonText: 'Cancelar',
    }).then((result) => {
      if (result.isConfirmed) {
        this.spinnerServiceGeneral.showSpinner();
        this.interesService.desHabilitarInteres(id).subscribe({
          next: (interes) => {
            this.spinnerServiceGeneral.hideSpinner();
            this.reloadPage();
            Swal.fire({
              position: 'top-end',
              icon: 'success',
              title: 'Interés deshabilitado exitosamente.',
              showConfirmButton: false,
              timer: 1500,
            });
          },
          error: (error) => {
            this.spinnerServiceGeneral.hideSpinner();
            Swal.fire({
              icon: 'error',
              title: 'Oops...',
              confirmButtonColor: '#199DE8',
              text: 'Algo salió mal! Reinicie la página. Si el error persiste intente mas tarde.',
            });
          },
        });
      } else {
        result.dismiss === Swal.DismissReason.cancel;
      }
    });
  }

  habilitarInteres(id: number, nombreInteres: string) {
    Swal.fire({
      title:
        '¿Estás seguro que deseas habilitar el interés  ' + nombreInteres + '?',
      icon: 'warning',
      showCancelButton: true,
      cancelButtonColor: '#909293',
      confirmButtonColor: '#199DE8',
      background: 'FFFFFF',
      confirmButtonText: 'Si, habilitar',
      cancelButtonText: 'Cancelar',
    }).then((result) => {
      if(result.isConfirmed){
        this.spinnerServiceGeneral.showSpinner();
        this.interesService.habilitarInteres(id).subscribe({
          next: (interes: any[]) => {
            this.spinnerServiceGeneral.hideSpinner();
            this.reloadPage();
            Swal.fire({
              position: 'top-end',
              icon: 'success',
              title: 'Interés habilitado exitosamente.',
              showConfirmButton: false,
              timer: 1500,
            });
          },
          error: (error) => {
            this.spinnerServiceGeneral.hideSpinner();
            Swal.fire({
              icon: 'error',
              title: 'Oops...',
              confirmButtonColor: '#199DE8',
              text: 'Algo salió mal! Reinicie la página. Si el error persiste intente mas tarde.',
            });
          },
        });
      } else {
        result.dismiss === Swal.DismissReason.cancel;
      }
     
    });
  }
  reloadPage() {
    const currentUrl = this.route.url;
    this.route.navigateByUrl('/administrador', { skipLocationChange: true }).then(() => {
      this.route.navigateByUrl(currentUrl);
    });
  }
}
