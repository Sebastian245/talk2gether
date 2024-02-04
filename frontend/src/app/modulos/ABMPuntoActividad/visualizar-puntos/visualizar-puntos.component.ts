import { Component } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { SpinnerServiceGeneral } from 'src/app/compartidos/spinner-carga-general/spinner.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ModalService } from '../../registro/servicios/modal.service';
import Swal from 'sweetalert2';
import { Puntos } from '../modelo/puntosPorActividad';
import { PuntosPorActividadService } from '../service/puntos-por-actividad.service';
import { ModificarPuntosComponent } from '../modificar-puntos/modificar-puntos.component';

@Component({
  selector: 'app-visualizar-puntos',
  templateUrl: './visualizar-puntos.component.html',
  styleUrls: ['./visualizar-puntos.component.scss']
})
export class VisualizarPuntosComponent {
  puntosList: Puntos[] = [];
  modalRef: NgbModalRef;
  nuevoPuntos: Puntos;

  constructor(
    private puntosPorActividadService: PuntosPorActividadService,
    private spinnerServiceGeneral: SpinnerServiceGeneral,
    private route: Router,
    private router: ActivatedRoute,
    private modalService: ModalService
  ) {}

  ngOnInit(): void {
    this.spinnerServiceGeneral.showSpinner();
    this.puntosPorActividadService.traerPuntosPorActividad().subscribe({
      next: (puntos: any[]) => {
        for (const punto of puntos) {
          this.nuevoPuntos = {
            id: punto['id'],
            fechaHoraAltaPuntosPorActividad: punto['fechaHoraAltaPuntosPorActividad'],
            fechaHoraFinVigenciaPuntosPorActividad: punto['fechaHoraFinVigenciaPuntosPorActividad'],
            nombrePuntosPorActividad: punto['nombrePuntosPorActividad'],
            puntosPorActividad: punto['puntosPorActividad'],
            tipoPuntosPorActividad: punto['tipoPuntosPorActividad'],
            descripcionPuntosPorActividad: punto['descripcionPuntosPorActividad'],
            puntosMaximos: punto['puntosMaximos']
          };
          this.puntosList.push(this.nuevoPuntos);
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
    this.puntosList.sort((a, b) => {
      if (
        a.fechaHoraFinVigenciaPuntosPorActividad === null &&
        b.fechaHoraFinVigenciaPuntosPorActividad !== null
      ) {
        return -1;
      } else if (
        a.fechaHoraFinVigenciaPuntosPorActividad !== null &&
        b.fechaHoraFinVigenciaPuntosPorActividad === null
      ) {
        return 1;
      } else {
        return 0;
      }
    });
  }


  modificarPuntosPorActividad(id:number, nombrePuntosPorActividad: string, puntosPorActividad:number, descripcion:string, puntosMaximos: number, tipoActividad:string){
    this.puntosPorActividadService.guardarId(id);
    this.puntosPorActividadService.guardarnombrePuntosPorActividad(nombrePuntosPorActividad);
    this.puntosPorActividadService.guardarpuntosPorActividad(puntosPorActividad);
    this.puntosPorActividadService.guardardescripcion(descripcion);
    this.puntosPorActividadService.guardarpuntosMaximos(puntosMaximos);
    this.puntosPorActividadService.guardarTipoActividad(tipoActividad);


    this.modalRef = this.modalService.open(ModificarPuntosComponent, {
      centered: true,
    });
  }


  reloadPage() {
    const currentUrl = this.route.url;
    this.route.navigateByUrl('/administrador', { skipLocationChange: true }).then(() => {
      this.route.navigateByUrl(currentUrl);
    });
  }
}
