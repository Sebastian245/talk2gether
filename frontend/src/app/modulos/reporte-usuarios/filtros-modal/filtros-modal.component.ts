import { Component, ViewEncapsulation } from '@angular/core';
import { ModalService } from '../../registro/servicios/modal.service';
import { ReporteUsuarioService } from '../service/reporte-usuario.service';
import { SpinnerServiceGeneral } from 'src/app/compartidos/spinner-carga-general/spinner.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-filtros-modal',
  templateUrl: './filtros-modal.component.html',
  styleUrls: ['./filtros-modal.component.scss'],
  encapsulation: ViewEncapsulation.None,

})
export class FiltrosModalComponent {

  listaMotivos: string[] = []
  busqueda = {}
  constructor(
    private modalService: ModalService,
    private reporteService: ReporteUsuarioService,
    private spinner: SpinnerServiceGeneral
  ) {}

  ngOnInit(): void {
    this.reporteService.traerMotivosReporteUsuario().subscribe({
      next: (r: any[]) =>{
        for (var i = 0; i < r.length; i++) {
          this.listaMotivos.push(r[i]);
        }
      },
      error: (e)=>{
        Swal.fire({
          icon: 'error',
          title: 'Oops...',
          confirmButtonColor: '#199DE8',
          text: 'Algo salió mal! Reinicie la página. Si el error persiste intente mas tarde.',
        });
      }
    })
  }
  filtrar(){
    const motivoReporte = document.getElementById("motivo") as HTMLSelectElement;
    this.busqueda = {
      motivo: motivoReporte.value,
    }
    this.dismissModal()
  }

  dismissModalCruz() {
    this.busqueda = {
      motivo: null,
    }
    this.modalService.dismissActiveModal();
  }
  dismissModal() {
    this.modalService.dismissActiveModal();
  }
}
