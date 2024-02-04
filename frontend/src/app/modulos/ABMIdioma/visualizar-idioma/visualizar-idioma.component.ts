import { Component } from '@angular/core';
import { SpinnerServiceGeneral } from 'src/app/compartidos/spinner-carga-general/spinner.service';
import { IdiomaService } from '../service/idioma.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ModalService } from '../../registro/servicios/modal.service';
import { Idioma } from '../modelos/idioma';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import Swal from 'sweetalert2';
import { ModificarIdiomaComponent } from '../modificar-idioma/modificar-idioma.component';
import { AniadirIdiomaComponent } from '../aniadir-idioma/aniadir-idioma.component';

@Component({
  selector: 'app-visualizar-idioma',
  templateUrl: './visualizar-idioma.component.html',
  styleUrls: ['./visualizar-idioma.component.scss']
})
export class VisualizarIdiomaComponent {
  idiomaList: Idioma[] = [];
  modalRef: NgbModalRef;
  nuevoIdioma: Idioma;
  
  constructor(
    private idiomaService: IdiomaService,
    private spinnerServiceGeneral: SpinnerServiceGeneral,
    private route: Router,
    private router: ActivatedRoute,
    private modalService: ModalService
  ) {}

  ngOnInit(): void {
    this.spinnerServiceGeneral.showSpinner();
    this.idiomaService.traerIdioma().subscribe({
      next: (idiomas: any[]) => {
        for (const idioma of idiomas) {
          this.nuevoIdioma = {
            id: idioma['id'],
            fechaHoraAltaIdioma: idioma['fechaHoraAltaIdioma'],
            fechaHoraFinVigenciaIdioma: idioma['fechaHoraFinVigenciaIdioma'],
            nombreIdioma: idioma['nombreIdioma'],
          };
          this.idiomaList.push(this.nuevoIdioma);
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
    this.idiomaList.sort((a, b) => {
      if (
        a.fechaHoraFinVigenciaIdioma === null &&
        b.fechaHoraFinVigenciaIdioma !== null
      ) {
        return -1;
      } else if (
        a.fechaHoraFinVigenciaIdioma !== null &&
        b.fechaHoraFinVigenciaIdioma === null
      ) {
        return 1;
      } else {
        return 0;
      }
    });
  }
  


  modificarIdioma(id: number, nombreIdioma:string){
    this.idiomaService.guardarId(id);
    this.idiomaService.guardarIdioma(nombreIdioma);
    this.modalRef = this.modalService.open(ModificarIdiomaComponent, {
      centered: true,
    });
  }

  aniadirIdioma(){
    this.modalRef = this.modalService.open(AniadirIdiomaComponent, {
      centered: true,
    });
  }

  desHabilitarIdioma(id: number, nombreIdioma: string) {
    Swal.fire({
      title:
        '¿Estás seguro que deseas deshabilitar el idioma  '+nombreIdioma +'?',
      icon: 'warning',
      showCancelButton: true,
      cancelButtonColor: '#909293',
      confirmButtonColor: '#199DE8',
      background: 'FFFFFF',
      confirmButtonText: 'Si, deshabilitar',
      cancelButtonText: 'Cancelar',
    }).then((result) => {
      if (result.isConfirmed){
      this.spinnerServiceGeneral.showSpinner();
      this.idiomaService.desHabilitarIdioma(id).subscribe({
        next: (idioma) => {
          this.spinnerServiceGeneral.hideSpinner();
          this.reloadPage();
          Swal.fire({
            position: 'top-end',
            icon: 'success',
            title: 'Idioma deshabilitado exitosamente.',
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
      result.dismiss === Swal.DismissReason.cancel
    }
    })
  }
  habilitarIdioma(id: number, nombreIdioma:string) {
    
    Swal.fire({
      title:
        '¿Estás seguro que deseas habilitar el idioma  '+nombreIdioma +'?',
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
        this.idiomaService.habilitarIdioma(id).subscribe({
          next: (idioma: any[]) => {
            this.spinnerServiceGeneral.hideSpinner();
            this.reloadPage();
            Swal.fire({
              position: 'top-end',
              icon: 'success',
              title: 'Idioma habilitado exitosamente.',
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
      }else {
        result.dismiss === Swal.DismissReason.cancel
      }
      })
    }
  

  reloadPage() {
    const currentUrl = this.route.url;
    this.route.navigateByUrl('/administrador', { skipLocationChange: true }).then(() => {
      this.route.navigateByUrl(currentUrl);
    });
  }
}  
