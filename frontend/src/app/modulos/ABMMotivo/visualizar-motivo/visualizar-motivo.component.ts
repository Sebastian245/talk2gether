import { Component } from '@angular/core';
import Swal from 'sweetalert2';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { SpinnerServiceGeneral } from 'src/app/compartidos/spinner-carga-general/spinner.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ModalService } from '../../registro/servicios/modal.service';
import { Motivo } from '../modelo/motivo';
import { MotivoService } from '../service/motivo.service';
import { AniadirMotivoComponent } from '../aniadir-motivo/aniadir-motivo.component';
import { ModificarMotivoComponent } from '../modificar-motivo/modificar-motivo.component';

@Component({
  selector: 'app-visualizar-motivo',
  templateUrl: './visualizar-motivo.component.html',
  styleUrls: ['./visualizar-motivo.component.scss'],
})
export class VisualizarMotivoComponent {
  motivoList: Motivo[] = [];
  modalRef: NgbModalRef;
  nuevoMotivo: Motivo;

  constructor(
    private motivoSerice: MotivoService,
    private spinnerServiceGeneral: SpinnerServiceGeneral,
    private route: Router,
    private router: ActivatedRoute,
    private modalService: ModalService
  ) {}

  ngOnInit(): void {
    this.spinnerServiceGeneral.showSpinner();
    this.motivoSerice.traerMotivo().subscribe({
      next: (motivos: any[]) => {
        for (const motivo of motivos) {
          this.nuevoMotivo = {
            id: motivo['id'],
            fechaHoraAltaMotivo: motivo['fechaHoraAltaMotivo'],
            fechaHoraFinVigenciaMotivo: motivo['fechaHoraFinVigenciaMotivo'],
            nombreMotivo: motivo['nombreMotivo'],
            nombreTipoMotivo: motivo['tipoMotivo']['nombreTipoMotivo'],
          };
          this.motivoList.push(this.nuevoMotivo);
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
    this.motivoList.sort((a, b) => {
      if (
        a.fechaHoraFinVigenciaMotivo === null &&
        b.fechaHoraFinVigenciaMotivo !== null
      ) {
        return -1;
      } else if (
        a.fechaHoraFinVigenciaMotivo !== null &&
        b.fechaHoraFinVigenciaMotivo === null
      ) {
        return 1;
      } else {
        return 0;
      }
    });
  }

  modificarInteres(id: number, nombreMotivo: string) {
    this.motivoSerice.guardarId(id);
    this.motivoSerice.guardarMotivo(nombreMotivo);
    this.modalRef = this.modalService.open(ModificarMotivoComponent, {
      centered: true,
    });
  }

  aniadirInteres() {
    this.modalRef = this.modalService.open(AniadirMotivoComponent, {
      centered: true,
    });
  }

  desHabilitarMotivo(id: number, nombreMotivo: string) {
    Swal.fire({
      title:
        '¿Estás seguro que deseas deshabilitar el motivo  ' +
        nombreMotivo +
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
        this.motivoSerice.desHabilitarMotivo(id).subscribe({
          next: (motivo) => {
            this.spinnerServiceGeneral.hideSpinner();
            this.reloadPage();
            Swal.fire({
              position: 'top-end',
              icon: 'success',
              title: 'Motivo deshabilitado exitosamente.',
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

  habilitarMotivo(id: number, nombreMotivo: string) {
    Swal.fire({
      title:
        '¿Estás seguro que deseas habilitar el motivo  ' + nombreMotivo + '?',
      icon: 'warning',
      showCancelButton: true,
      cancelButtonColor: '#909293',
      confirmButtonColor: '#199DE8',
      background: 'FFFFFF',
      confirmButtonText: 'Si, habilitar',
      cancelButtonText: 'Cancelar',
    }).then((result) => {
      if (result.isConfirmed) {
        this.spinnerServiceGeneral.showSpinner();
        this.motivoSerice.habilitarMotivo(id).subscribe({
          next: (interes: any[]) => {
            this.spinnerServiceGeneral.hideSpinner();
            this.reloadPage();
            Swal.fire({
              position: 'top-end',
              icon: 'success',
              title: 'Motivo habilitado exitosamente.',
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
