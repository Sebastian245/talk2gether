import { Component, OnInit } from '@angular/core';
import { PaisService } from '../service/pais.service';
import { SpinnerServiceGeneral } from 'src/app/compartidos/spinner-carga-general/spinner.service';
import { Pais } from '../modelo/pais';
import { ActivatedRoute, NavigationExtras, Router } from '@angular/router';
import Swal from 'sweetalert2';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ModalService } from '../../registro/servicios/modal.service';
import { ModificarPaisComponent } from '../modificar-pais/modificar-pais.component';
import { AniadirPaisComponent } from '../aniadir-pais/aniadir-pais.component';

@Component({
  selector: 'app-visualizar-pais',
  templateUrl: './visualizar-pais.component.html',
  styleUrls: ['./visualizar-pais.component.scss'],
})
export class visualizarPais implements OnInit {
  paisList: Pais[] = [];
  modalRef: NgbModalRef;
  nuevoPais: Pais;

  constructor(
    private paisService: PaisService,
    private spinnerServiceGeneral: SpinnerServiceGeneral,
    private route: Router,
    private router: ActivatedRoute,
    private modalService: ModalService
  ) {}

  ngOnInit(): void {
    this.spinnerServiceGeneral.showSpinner();
    this.paisService.traerPais().subscribe({
      next: (paises: any[]) => {
        for (const pais of paises) {
          this.nuevoPais = {
            id: pais['id'],
            fechaHoraAltaPais: pais['fechaHoraAltaPais'],
            fechaHoraFinVigenciaPais: pais['fechaHoraFinVigenciaPais'],
            nombrePais: pais['nombrePais'],
            urlBandera: pais['urlBandera'],
          };
          this.paisList.push(this.nuevoPais);
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
    this.paisList.sort((a, b) => {
      if (
        a.fechaHoraFinVigenciaPais === null &&
        b.fechaHoraFinVigenciaPais !== null
      ) {
        return -1;
      } else if (
        a.fechaHoraFinVigenciaPais !== null &&
        b.fechaHoraFinVigenciaPais === null
      ) {
        return 1;
      } else {
        return 0;
      }
    });
  }

  desHabilitarPais(id: number, nombrePais: string) {
    Swal.fire({
      title:
        '¿Estás seguro que deseas deshabilitar el país  ' + nombrePais + '?',
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
        this.paisService.desHabilitarPais(id).subscribe({
          next: (paises) => {
            this.spinnerServiceGeneral.hideSpinner();
            this.reloadPage();
            Swal.fire({
              position: 'top-end',
              icon: 'success',
              title: 'País deshabilitado exitosamente.',
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
  habilitarPais(id: number, nombrePais: string) {
    Swal.fire({
      title: '¿Estás seguro que deseas habilitar el país  ' + nombrePais + '?',
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
        this.paisService.habilitarPais(id).subscribe({
          next: (paises: any[]) => {
            this.spinnerServiceGeneral.hideSpinner();
            Swal.fire({
              position: 'top-end',
              icon: 'success',
              title: 'País habilitado exitosamente.',
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

  modificarPais(id: number, nombre: string, url: string) {
    this.paisService.guardarId(id);
    this.paisService.guardarNombre(nombre);
    this.paisService.guardarUrlPais(url);
    this.modalRef = this.modalService.open(ModificarPaisComponent, {
      centered: true,
    });
  }

  aniadirIdioma() {
    this.modalRef = this.modalService.open(AniadirPaisComponent, {
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
