import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ServicioVisualizarPerfilService } from '../services/servicio-visualizar-perfil.service';
import { NavbarService } from 'src/app/compartidos/navbar/service/navbar.service';
import { SpinnerServiceGeneral } from 'src/app/compartidos/spinner-carga-general/spinner.service';
import { UsuarioVisualizarPerfil } from '../models/usuario-visualizar';
import {
  Observable,
  catchError,
  combineLatest,
  finalize,
  map,
  of,
  switchMap,
  take,
  tap,
} from 'rxjs';
import Swal from 'sweetalert2';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { itemModal } from '../models/item-modal';
import { ModalFotoPerfilComponent } from '../modal-foto-perfil/modal-foto-perfil.component';
import { ModalService } from '../../registro/servicios/modal.service';
import { CommonModule, NgIf } from '@angular/common';
import Chart from 'chart.js/auto';

@Component({
  selector: 'app-estadisticas',
  standalone: true,
  imports: [
    CommonModule,
    NgIf
  ],
  templateUrl: './estadisticas.component.html',
  styleUrls: ['./estadisticas.component.scss']
})
export class EstadisticasComponent {
  modalRef: NgbModalRef;

  idGuest: number;
  urlFoto: string;
  nombreUsuario: string;
  apellido: string;
  isUser$: Observable<any>;
  datosUsuario: UsuarioVisualizarPerfil;
  isUserPerfil$: Observable<any>;
  items: itemModal[] = [];
  item: itemModal;
  //Valores de cambios de pantalla
  seguir: boolean ;
  idMiPerfil: number;
  user: boolean;
  estadisticas: any;
  fechaPuntos: string[];
  cantidadPuntos: number[];

  constructor(
    private router: ActivatedRoute,
    private perfilService: ServicioVisualizarPerfilService,
    private route: Router,
    private nav: NavbarService,
    private modalService: ModalService,
    private spinnerServiceGeneral: SpinnerServiceGeneral,
  ) {}

  ngOnInit(): void {
    this.spinnerServiceGeneral.showSpinner();
    this.isUserPerfil$ = combineLatest([
      this.router.params,
      this.nav.traerUsuario(),
    ]).pipe(
      tap(() => this.spinnerServiceGeneral.showSpinner()),
      switchMap(([paramId, isMyUser]) => {
        this.idGuest = paramId['id'];
        this.perfilService.guardarId(paramId['id']);
        this.idMiPerfil = isMyUser['id'];
        this.perfilService.guardaridMiPerfil(isMyUser['id']);
        if (this.idGuest == this.idMiPerfil) {
          this.user = true;
          this.isUser$ = of(true);
        } else {
          this.user = false;
          this.isUser$ = this.validarUsuariosBloqueados();
        }
        return this.perfilService.traerPerfil(paramId['id']);
      }),
      map((perfil: any) => {
        this.nombreUsuario = perfil.nombreUsuario;
        this.apellido = perfil.apellidoUsuario;
        this.datosUsuario = {
          nombre: perfil.nombreUsuario,
          apellido: perfil.apellidoUsuario,
          edad: perfil.edadUsuario,
          urlFoto: perfil.urlFoto,
        };
        this.perfilService.guardarDatos(this.datosUsuario);
        return perfil;
      }),
      catchError(error => {
        this.spinnerServiceGeneral.hideSpinner()
        Swal.fire({
          icon: 'error',
          title: 'Oops...',
          text: 'Algo salió mal! Intente nuevamente mas tarde.',
        });
        this.route.navigate(['']);
        return of(null);
      }),
      tap(() => this.perfilService.obtenerEstadisticas(this.idGuest).subscribe({
        next: data =>{
          this.estadisticas = data;
          this.fechaPuntos = Object.keys(this.estadisticas.puntuacionesPorFecha);
          this.cantidadPuntos = this.fechaPuntos.map(fecha => this.estadisticas.puntuacionesPorFecha[fecha]);
          this.spinnerServiceGeneral.hideSpinner();
          setTimeout(() => {
            this.cargarGrafico();
          });
        },
        error: error =>{
        }
      }))
    );
  }

  openDialog(urlFoto:string): void {
    const modalRef = this.modalService.open(ModalFotoPerfilComponent, {
      centered: true,
      size: 'lg'
    });
    modalRef.componentInstance.data = {
      imageUrl: urlFoto
    };
  }

  cargarGrafico(){
    const ctx = document.getElementById('myChart') as HTMLCanvasElement;
    new Chart(ctx, {
      type: 'line',
      data: {
        labels: this.fechaPuntos,
        datasets: [{
          label: '',
          data: this.cantidadPuntos,
          borderWidth: 1
        }]
      },
      options: {
        responsive: true,
        plugins: {
          legend: {
            display: false
          },
          title: {
            display: true,
            text: 'Puntos en los últimos 7 días',
          }
        },
        scales: {
          y: {
            beginAtZero: true
          }
        }
      }
    });
  }

  validarUsuariosBloqueados() {
    return combineLatest([
      this.perfilService.revisarBloqueo(this.idMiPerfil, this.idGuest),
      this.perfilService.revisarBloqueo(this.idGuest, this.idMiPerfil),
      this.perfilService.revisarSeguir(this.idMiPerfil, this.idGuest),
    ]).pipe(
      take(1),
      map(([responseRevisarBloqueo, response, responseRevisarSeguir]) => {
        if (responseRevisarBloqueo['estado'] == true) {
          this.route.navigate(['usuarioBloqueado']);
          return true;
        } else if (response['estado'] == true) {
          Swal.fire({
            title: 'Usted no puede visitar a este usuario',
            showClass: {
              popup: 'animate__animated animate__fadeInDown',
            },
            hideClass: {
              popup: 'animate__animated animate__fadeOutUp',
            },
            confirmButtonColor: '#2b6a78',
          });
          this.route.navigate(['/']);
        } else if (responseRevisarSeguir['estado'] == true) {
          this.seguir = true;
        } else{
          this.seguir = false;
        }
        return true;
      }),
      catchError(() => {
        Swal.fire({
          icon: 'error',
          title: 'Oops...',
          text: 'Algo salió mal! Intente nuevamente mas tarde.',
        });
        this.route.navigate(['/']);
        return of();
      })
    );
  }

  volver() {
    this.route.navigate(['perfilUsuario', this.idGuest]);
  }

}