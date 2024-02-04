import { Component, ViewEncapsulation } from '@angular/core';
import { detalleReporte } from '../../model/detalleReporte';
import { ModalService } from 'src/app/modulos/registro/servicios/modal.service';
import { ReporteUsuarioService } from '../../service/reporte-usuario.service';
import { SpinnerServiceGeneral } from 'src/app/compartidos/spinner-carga-general/spinner.service';
import {
  Observable,
  catchError,
  combineLatest,
  map,
  of,
  switchMap,
  tap,
} from 'rxjs';

@Component({
  selector: 'app-reporte-modal',
  templateUrl: './reporte-modal.component.html',
  styleUrls: ['./reporte-modal.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class ReporteModalComponent {
  detalleReporteList: detalleReporte[] = [];
  detalleReporte: detalleReporte;
  constructor(
    private modalService: ModalService,
    private reporteService: ReporteUsuarioService,
    private spinner: SpinnerServiceGeneral
  ) {}
  reporte$: Observable<any>;

  ngOnInit(): void {
    this.reporte$ = combineLatest([
      this.reporteService.getidCuenta(),
      this.reporteService.getidMotivo(),
    ]).pipe(
      tap(() => {
        this.spinner.hideSpinner();
      }),
      switchMap(([idCuenta, idMotivo]) => {
        return this.reporteService.traerDetalleReporte(idCuenta, idMotivo).pipe(
          map((r: detalleReporte[]) => {
            this.detalleReporteList = [];
            for (const detalleReporte of r) {
              this.detalleReporte = {
                correo: detalleReporte['correo'],
                descripcionReporteMotivo:
                  detalleReporte['descripcionReporteMotivo'],
                fechaHoraAltaReporteMotivo: this.formatDate(
                  detalleReporte['fechaHoraAltaReporteMotivo']
                ),
                idCuentaInformanteMotivo:
                  detalleReporte['idCuentaInformanteMotivo'],
              };
              this.detalleReporteList.push(this.detalleReporte);
            }
            return true;
          }),
          catchError((error) => {
            console.error(error);
            return of(false);
          })
        );
      })
    );
  }
  dirigirPerfil(id: number) {
    console.log(id)
    const url = `/perfilUsuario/` + id;
    window.open(url, '_blank');

  }
  formatDate(dateString: string): string {
    const parsedDate = new Date(dateString);

    const day = parsedDate.getDate().toString().padStart(2, '0');
    const month = (parsedDate.getMonth() + 1).toString().padStart(2, '0');
    const year = parsedDate.getFullYear();
    const hours = parsedDate.getHours().toString().padStart(2, '0');
    const minutes = parsedDate.getMinutes().toString().padStart(2, '0');
    const seconds = parsedDate.getSeconds().toString().padStart(2, '0');

    return `${day}/${month}/${year} ${hours}:${minutes}:${seconds}`;
  }
  dismissModal() {
    this.modalService.dismissActiveModal();
  }
}
