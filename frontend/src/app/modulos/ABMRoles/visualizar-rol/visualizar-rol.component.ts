import { Component } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { SpinnerServiceGeneral } from 'src/app/compartidos/spinner-carga-general/spinner.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ModalService } from '../../registro/servicios/modal.service';
import Swal from 'sweetalert2';
import { RolService } from '../service/rol.service';
import { Rol } from '../modelo/rol';
import { ModificarRolComponent } from '../modificar-rol/modificar-rol.component';
import { AniadirRolComponent } from '../aniadir-rol/aniadir-rol.component';

@Component({
  selector: 'app-visualizar-rol',
  templateUrl: './visualizar-rol.component.html',
  styleUrls: ['./visualizar-rol.component.scss']
})
export class VisualizarRolComponent {
  rolList: Rol[] = [];
  modalRef: NgbModalRef;
  nuevoRol: Rol;

  constructor(
    private rolService: RolService,
    private spinnerServiceGeneral: SpinnerServiceGeneral,
    private route: Router,
    private modalService: ModalService
  ) {}

  ngOnInit(): void {
    this.spinnerServiceGeneral.showSpinner();
    this.rolService.traerRoles().subscribe({
      next: (roles: Rol[]) => {
        for (const rol of roles) {
          this.nuevoRol = {
            id: rol['id'],
            fechaHoraFinVigenciaRol: rol['fechaHoraFinVigenciaRol'],
            fechaHoraAltaRol: rol['fechaHoraAltaRol'],
            nombreRol: rol['nombreRol'],
          };
          this.rolList.push(this.nuevoRol);
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
    this.rolList.sort((a, b) => {
      if (
        a.fechaHoraFinVigenciaRol === null &&
        b.fechaHoraFinVigenciaRol !== null
      ) {
        return -1;
      } else if (
        a.fechaHoraFinVigenciaRol !== null &&
        b.fechaHoraFinVigenciaRol === null
      ) {
        return 1;
      } else {
        return 0;
      }
    });
  }

  desHabilitarRol(id: number, nombreRol: string) {
    Swal.fire({
      title:
        '¿Estás seguro que deseas deshabilitar el rol  -' + nombreRol + '-?',
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
        this.rolService.desHabilitarRol(id).subscribe({
          next: (roles) => {
            this.spinnerServiceGeneral.hideSpinner();
            this.reloadPage();
            Swal.fire({
              position: 'top-end',
              icon: 'success',
              title: 'Rol deshabilitado exitosamente.',
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
  habilitarRol(id: number, nombreRol: string) {
    Swal.fire({
      title: '¿Estás seguro que deseas habilitar el rol - ' + nombreRol + '- ?',
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
        this.rolService.habilitarRol(id).subscribe({
          next: (roles: any[]) => {
            this.spinnerServiceGeneral.hideSpinner();
            Swal.fire({
              position: 'top-end',
              icon: 'success',
              title: 'Rol habilitado exitosamente.',
              showConfirmButton: false,
              timer: 1500,
            });
            this.reloadPage();
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

  modificarRol(id: number, nombre: string) {
    this.rolService.guardarId(id);
    this.rolService.guardarNombre(nombre);
    this.modalRef = this.modalService.open(ModificarRolComponent, {
      centered: true,
    });
  }

  aniadirRol() {
    this.modalRef = this.modalService.open(AniadirRolComponent, {
      centered: true,
    });
  }
  modificarPermisos(){
    this.route.navigate(['administrador', 'administrarPermisos'])
  }

  reloadPage() {
    const currentUrl = this.route.url;
    this.route.navigateByUrl('/administrador', { skipLocationChange: true }).then(() => {
      this.route.navigateByUrl(currentUrl);
    });
  }
}
