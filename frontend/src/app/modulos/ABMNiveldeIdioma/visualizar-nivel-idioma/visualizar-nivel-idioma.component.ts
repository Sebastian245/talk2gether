import { Component } from '@angular/core';
import { nivelIdioma } from '../modelos/nivelIdioma';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { SpinnerServiceGeneral } from 'src/app/compartidos/spinner-carga-general/spinner.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ModalService } from '../../registro/servicios/modal.service';
import Swal from 'sweetalert2';
import { NivelIdiomaService } from '../service/nivel-idioma.service';
import { ModificarNivelIdiomaComponent } from '../modificar-nivel-idioma/modificar-nivel-idioma.component';
import { AniadirNivelIdiomaComponent } from '../aniadir-nivel-idioma/aniadir-nivel-idioma.component';

@Component({
  selector: 'app-visualizar-nivel-idioma',
  templateUrl: './visualizar-nivel-idioma.component.html',
  styleUrls: ['./visualizar-nivel-idioma.component.scss'],
})
export class VisualizarNivelIdiomaComponent {
  nivelIdiomaList: nivelIdioma[] = [];
  modalRef: NgbModalRef;
  nuevoNivelIdioma: nivelIdioma;

  constructor(
    private nivelIdiomaService: NivelIdiomaService,
    private spinnerServiceGeneral: SpinnerServiceGeneral,
    private route: Router,
    private router: ActivatedRoute,
    private modalService: ModalService
  ) {}

  ngOnInit(): void {
    this.spinnerServiceGeneral.showSpinner();
    this.nivelIdiomaService.traerNivelIdioma().subscribe({
      next: (nivelIdiomas: any[]) => {
        for (const nivelIdioma of nivelIdiomas) {
          this.nuevoNivelIdioma = {
            id: nivelIdioma['id'],
            fechaHoraAltaNivelIdioma: nivelIdioma['fechaHoraAltaNivelIdioma'],
            fechaHoraFinVigenciaNivelIdioma:
              nivelIdioma['fechaHoraFinVigenciaNivelIdioma'],
            nombreNivelIdioma: nivelIdioma['nombreNivelIdioma'],
          };
          this.nivelIdiomaList.push(this.nuevoNivelIdioma);
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
    this.nivelIdiomaList.sort((a, b) => {
      if (
        a.fechaHoraFinVigenciaNivelIdioma === null &&
        b.fechaHoraFinVigenciaNivelIdioma !== null
      ) {
        return -1;
      } else if (
        a.fechaHoraFinVigenciaNivelIdioma !== null &&
        b.fechaHoraFinVigenciaNivelIdioma === null
      ) {
        return 1;
      } else {
        return 0;
      }
    });
  }

  modificarNivelIdioma(id: number, nombreNivelIdioma: string) {
    this.nivelIdiomaService.guardarId(id);
    this.nivelIdiomaService.guardarNivelIdioma(nombreNivelIdioma);
    this.modalRef = this.modalService.open(ModificarNivelIdiomaComponent, {
      centered: true,
    });
  }

  aniadirNivelIdioma() {
    this.modalRef = this.modalService.open(AniadirNivelIdiomaComponent, {
      centered: true,
    });
  }

  desHabilitarNivelIdioma(id: number, nombreNivelIdioma: string) {
    Swal.fire({
      title:
        '¿Estás seguro que deseas deshabilitar el nivel de idioma  ' +
        nombreNivelIdioma +
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
        this.nivelIdiomaService.desHabilitarNivelIdioma(id).subscribe({
          next: (nivelIdioma) => {
            this.spinnerServiceGeneral.hideSpinner();
            this.reloadPage();
            Swal.fire({
              position: 'top-end',
              icon: 'success',
              title: 'Nivel de idioma deshabilitado exitosamente.',
              showConfirmButton: false,
              timer: 1500,
            });
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
        result.dismiss === Swal.DismissReason.cancel;
      }
    });
  }
  habilitarNivelIdioma(id: number, nombreNivelIdioma: string) {
    Swal.fire({
      title:
        '¿Estás seguro que deseas habilitar el nivel de idioma  ' +
        nombreNivelIdioma +
        '?',
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
        this.nivelIdiomaService.habilitarNivelIdioma(id).subscribe({
          next: (nivelIdioma: any[]) => {
            this.spinnerServiceGeneral.hideSpinner();
            Swal.fire({
              position: 'top-end',
              icon: 'success',
              title: 'Nivel de Idioma habilitado exitosamente.',
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

  reloadPage() {
    const currentUrl = this.route.url;
    this.route.navigateByUrl('/administrador', { skipLocationChange: true }).then(() => {
      this.route.navigateByUrl(currentUrl);
    });
  }
}
