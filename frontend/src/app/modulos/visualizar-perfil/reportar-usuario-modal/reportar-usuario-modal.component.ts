import { Component, ViewEncapsulation } from '@angular/core';
import { ModalService } from '../../registro/servicios/modal.service';
import { SpinnerServiceGeneral } from 'src/app/compartidos/spinner-carga-general/spinner.service';
import { ReportarUsuarioService } from '../services/reportar-usuario.service';
import { ServicioVisualizarPerfilService } from '../services/servicio-visualizar-perfil.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-reportar-usuario-modal',
  templateUrl: './reportar-usuario-modal.component.html',
  styleUrls: ['./reportar-usuario-modal.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class ReportarUsuarioModalComponent {

  descripcionContent = '';
  motivoList = [];
  motivosSeleccionados: string[] = [];
  idReportante: number;
  idReportado: number;

  constructor(
    private modalService: ModalService,
    private spinner: SpinnerServiceGeneral,
    private reportarUsuarioService: ReportarUsuarioService,
    private perfilService: ServicioVisualizarPerfilService
    ) { }

  ngOnInit(){
    this.reportarUsuarioService.listarMotivosReporte().subscribe({
      next: response => {
        this.motivoList = response.listaNombreMotivo;
      },
      error: error => {
      }
    });

    this.perfilService.getId().subscribe({
      next: subscribe =>{
        this.idReportado = +subscribe;
      },
      error: error =>{

      }
    });

    this.perfilService.getidMiPerfil().subscribe({
      next: subscribe =>{
        this.idReportante = +subscribe;
      },
      error: error =>{
      }
    });
  }

  reportar(){
    const reporte = {
      "idCuentaInformanteMotivo": this.idReportante,
      "idCuentaReportado": this.idReportado,
      "descripcionReporteMotivo": this.descripcionContent,
      "nombreMotivo": this.motivosSeleccionados
    }
    this.spinner.showSpinner();
    this.reportarUsuarioService.reportarUsuario(reporte).subscribe({
      next: subscribe =>{
        this.spinner.hideSpinner();
        Swal.fire({
          position: 'center',
          icon: 'success',
          title: 'Reporte enviado correctamente',
          showConfirmButton: false,
          timer: 1500
        });
        this.dismissModal();
      },
      error: error =>{
        Swal.fire({
          icon: 'error',
          title: 'Oops...',
          text: 'Algo salió mal! Intente nuevamente mas tarde.',
        });
      }
    })
  }

  dismissModal() {
    this.modalService.dismissActiveModal();
  }

}
