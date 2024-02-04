import { Component } from '@angular/core';
import { BajaUsuarioComponent } from '../../ABMUsuario/baja-usuario/baja-usuario.component';
import { ModificarUsuarioComponent } from '../../ABMUsuario/modificar-usuario/modificar-usuario.component';
import Swal from 'sweetalert2';
import { FormControl, FormGroup } from '@angular/forms';
import { UsuarioService } from '../../ABMUsuario/services/usuario.service';
import { SpinnerServiceGeneral } from 'src/app/compartidos/spinner-carga-general/spinner.service';
import { Router } from '@angular/router';
import { ModalService } from '../../registro/servicios/modal.service';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Reporte } from '../model/reporte';
import { ReporteUsuarioService } from '../service/reporte-usuario.service';
import { ReporteModalComponent } from '../modal-reportes/reporte-modal/reporte-modal.component';
import { FiltrosModalComponent } from '../filtros-modal/filtros-modal.component';
@Component({
  selector: 'app-reporte-usuario',
  templateUrl: './reporte-usuario.component.html',
  styleUrls: ['./reporte-usuario.component.scss'],
})
export class ReporteUsuarioComponent {
  reporteList: Reporte[] = [];
  modalRef: NgbModalRef;
  reporte: Reporte;
  busquedaForm: FormGroup;
  busqueda = {};
  constructor(
    private reporteService: ReporteUsuarioService,
    private usuarioService: UsuarioService,
    private spinnerServiceGeneral: SpinnerServiceGeneral,
    private route: Router,
    private modalService: ModalService
  ) {}

  ngOnInit(): void {
    this.busquedaForm = new FormGroup({
      cadena: new FormControl(''),
    });
    this.traerUsuarios();
  }

  verFiltros() {
    let modalInstance: any;
    this.modalRef = this.modalService.open(FiltrosModalComponent, {
      centered: true,
    });
    modalInstance = this.modalRef.componentInstance;
    this.modalRef.dismissed.subscribe((data) => {
      this.busqueda = modalInstance.busqueda;
      this.traerUsuariosFiltradosPorMotivo(this.busqueda['motivo']);
    });
  }
  traerUsuarios(cadena?: string) {
    if (cadena) {
      this.reporteList = [];
      this.spinnerServiceGeneral.showSpinner();
      this.reporteService.buscadorUsuarioReportes(cadena).subscribe({
        next: (reportes: any[]) => {
          for (const reporte of reportes) {
            this.reporte = {
              idCuenta: reporte['idCuenta'],
              nombreUsuario: reporte['nombreUsuario'],
              apellidoUsuario: reporte['apellidoUsuario'],
              idMotivo: reporte['idMotivo'],
              nombreMotivo: reporte['nombreMotivo'],
              cantidadMotivo: reporte['cantidadMotivo'],
              correo: reporte['correo']
            };
            this.reporteList.push(this.reporte);
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
    } else {
      this.reporteList = [];
      this.spinnerServiceGeneral.showSpinner();
      this.reporteService.traerReportes().subscribe({
        next: (reportes: any[]) => {
          for (const reporte of reportes) {
            this.reporte = {
              idCuenta: reporte['idCuenta'],
              nombreUsuario: reporte['nombreUsuario'],
              apellidoUsuario: reporte['apellidoUsuario'],
              idMotivo: reporte['idMotivo'],
              nombreMotivo: reporte['nombreMotivo'],
              cantidadMotivo: reporte['cantidadMotivo'],
              correo: reporte['correo']
            };
            this.reporteList.push(this.reporte);
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
  }

  buscarUsuariosAdministrador() {
    this.traerUsuarios(this.busquedaForm.get('cadena').value);
  }

  traerUsuariosFiltradosPorMotivo(busqueda: string ) {
    if(busqueda === null){
      this.traerUsuarios();
    } else {

      this.reporteList = [];
      this.spinnerServiceGeneral.showSpinner();
      this.reporteService.filtrarPorMotivo(busqueda).subscribe({
        next: (reportes: any[]) => {
          for (const reporte of reportes) {
            this.reporte = {
              idCuenta: reporte['idCuenta'],
              nombreUsuario: reporte['nombreUsuario'],
              apellidoUsuario: reporte['apellidoUsuario'],
              idMotivo: reporte['idMotivo'],
              nombreMotivo: reporte['nombreMotivo'],
              cantidadMotivo: reporte['cantidadMotivo'],
              correo: reporte['correo']
            };
            this.reporteList.push(this.reporte);
          }
          this.spinnerServiceGeneral.hideSpinner();
        },
        error: (e) => {
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
  }

  eliminarUsuario(
    id: number,
    nombre: string,
    apellido: string,
    correo: string,
  ) {
    this.usuarioService.guardarId(id);
    const nombreApellido = nombre + ', ' + apellido;
    this.usuarioService.guardarNombreApellido(nombreApellido);
    this.usuarioService.guardarcorreo(correo);
    this.modalRef = this.modalService.open(BajaUsuarioComponent, {
      centered: true,
    });
    this.route.navigate(['administrador', 'administrarUsuario']);
  }
  visualizarReportes(idCuenta: number, idMotivo: number) {
    this.reporteService.guardaridCuenta(idCuenta);
    this.reporteService.guardaridMotivo(idMotivo);
    const modalRef = this.modalService.open(ReporteModalComponent, {
      centered: true,
    });
  }

  dirigirPerfil(id: number) {
    const url = `/perfilUsuario/` + id;
    window.open(url, '_blank');
  }

  reloadPage() {
    const currentUrl = this.route.url;
    this.route
      .navigateByUrl('/administrador', { skipLocationChange: true })
      .then(() => {
        this.route.navigateByUrl(currentUrl);
      });
  }
}
