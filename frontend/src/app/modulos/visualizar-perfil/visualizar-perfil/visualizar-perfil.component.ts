import { CommonModule, Location, NgIf } from '@angular/common';
import { Component, Input } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import {
  NgbCarouselModule,
  NgbModal,
  NgbModalRef,
} from '@ng-bootstrap/ng-bootstrap';
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
import { NavbarService } from 'src/app/compartidos/navbar/service/navbar.service';
import { SpinnerServiceGeneral } from 'src/app/compartidos/spinner-carga-general/spinner.service';
import Swal from 'sweetalert2';
import { ModalService } from '../../registro/servicios/modal.service';
import { itemModal } from '../models/item-modal';
import { UsuarioVisualizarPerfil } from '../models/usuario-visualizar';
import { SeguirModalComponent } from '../seguir-modal/seguir-modal.component';
import { ServicioVisualizarPerfilService } from '../services/servicio-visualizar-perfil.service';
import { ModalFotoPerfilComponent } from '../modal-foto-perfil/modal-foto-perfil.component';
import { ReportarUsuarioModalComponent } from '../reportar-usuario-modal/reportar-usuario-modal.component';

@Component({
  selector: 'app-visualizar-perfil',
  standalone: true,
  imports: [
    NgbCarouselModule,
    NgIf,
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
  ],
  templateUrl: './visualizar-perfil.component.html',
  styleUrls: ['./visualizar-perfil.component.scss'],
})
export class VisualizarPerfilComponent {
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
  seguir: boolean;
  idMiPerfil: number;
  user: boolean;

  constructor(
    private router: ActivatedRoute,
    private perfilService: ServicioVisualizarPerfilService,
    private route: Router,
    private nav: NavbarService,
    private modalService: ModalService,
    private spinnerServiceGeneral: SpinnerServiceGeneral
  ) { }

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
        return this.perfilService.traerPerfil(paramId['id']).pipe(
          take(1),
          tap((response) => {
            this.nombreUsuario = response['nombreUsuario'];
            this.apellido = response['apellidoUsuario'];
            this.datosUsuario = {
              nombre: response['nombreUsuario'],
              apellido: response['apellidoUsuario'],
              edad: response['edadUsuario'],
              urlFoto: response['urlFoto'],
            };
            this.perfilService.guardarDatos(this.datosUsuario);
            if (this.idGuest == this.idMiPerfil) {
              this.user = true;
              this.isUser$ = of(true);
            } else {
              this.user = false;
              this.isUser$ = this.validarUsuariosBloqueados();
            }
          }),
          catchError((error) => {
            Swal.fire({
              title:
                'No se pudieron obtener los datos del usuario intente mas tarde.',
              showClass: {
                popup: 'animate__animated animate__fadeInDown',
              },
              hideClass: {
                popup: 'animate__animated animate__fadeOutUp',
              },
              confirmButtonColor: '#2b6a78',
            });
            this.route.navigate(['']);
            return of(false);
          })
        );
      }),
      catchError((error) => {
        this.spinnerServiceGeneral.hideSpinner();
        localStorage.clear();
        sessionStorage.clear();
        localStorage.setItem('isLoggedIn', 'false');
        Swal.fire(
          'La sesión fue cerrada',
          'Inicie nuevamente para continuar en la plataforma',
          'question'
        );
        this.route.navigate(['']);
        return of(null);
      }),
      tap(() => this.spinnerServiceGeneral.hideSpinner())
    );
  }
  openDialog(urlFoto: string): void {
    const modalRef = this.modalService.open(ModalFotoPerfilComponent, {
      centered: true,
      size: 'lg',
    });

    modalRef.componentInstance.data = {
      imageUrl: urlFoto,
    };
  }
  bloquear() {
    Swal.fire({
      title:
        '¿Estás seguro que deseas bloquear a ' +
        this.nombreUsuario +
        ' ' +
        this.apellido +
        ' ?',
      icon: 'warning',
      showCancelButton: true,
      cancelButtonColor: '#909293',
      confirmButtonColor: '#199DE8',
      background: 'FFFFFF',
      confirmButtonText: 'Si, bloquear',
      cancelButtonText: 'Cancelar',
    }).then((result) => {
      if (result.isConfirmed) {
        this.spinnerServiceGeneral.showSpinner();
        this.perfilService
          .bloquearUsuario(this.idMiPerfil, this.idGuest)
          .pipe(
            switchMap((response) => {
              return this.perfilService
                .bloquearUsuarioChat(this.idMiPerfil, this.idGuest)
                .pipe(
                  tap((response) => {
                    this.spinnerServiceGeneral.hideSpinner();
                    this.route.navigate(['usuarioBloqueado']);
                  }),
                  catchError((error) => {
                    this.spinnerServiceGeneral.hideSpinner();
                    this.route.navigate(['usuarioBloqueado']);
                    return of(false);
                  })
                );
            }),
            catchError((error) => {
              Swal.fire({
                icon: 'error',
                title: 'Oops...',
                text: 'Algo salió mal! Intente nuevamente mas tarde.',
              });
              return of(false);
            })
          )
          .subscribe();
      }
      finalize(() => this.spinnerServiceGeneral.hideSpinner());
    });
  }

  reportar() {
    let modalInstance: any;
    this.modalRef = this.modalService.open(ReportarUsuarioModalComponent, {
      centered: true,
    });
    modalInstance = this.modalRef.componentInstance;
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
          return of(true);
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
        } else {
          this.seguir = false;
        }
        return of(false);
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

  seguirUsuario() {
    this.spinnerServiceGeneral.showSpinner();
    this.perfilService.seguirUsuario(this.idMiPerfil, this.idGuest).subscribe({
      next: (response: any[]) => {
        this.seguir = !this.seguir;
        this.spinnerServiceGeneral.hideSpinner();
      },
      error: (error) => {
        Swal.fire({
          icon: 'error',
          title: 'Oops...',
          text: 'Algo salió mal! Intente nuevamente mas tarde.',
        });
        this.spinnerServiceGeneral.hideSpinner();
        this.route.navigate(['']);
      },
    });
  }

  dejarSeguirUsuario() {
    this.spinnerServiceGeneral.showSpinner();
    this.perfilService
      .dejarSeguirUsuario(this.idMiPerfil, this.idGuest)
      .subscribe({
        next: (response: any[]) => {
          this.seguir = !this.seguir;
          this.spinnerServiceGeneral.hideSpinner();
        },
        error: (error) => {
          Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: 'Algo salió mal! Intente nuevamente mas tarde.',
          });
          this.route.navigate(['']);
          this.spinnerServiceGeneral.hideSpinner();
        },
      });
  }

  dirigirLogros() {
    this.route.navigate(['perfilUsuario/logros', this.idGuest]);
  }

  dirigirEstadisticas() {
    this.route.navigate(['perfilUsuario/estadisticas', this.idGuest]);
  }

  dirigirMensaje() {
    this.route.navigate(['chat', this.idGuest]);
  }

  volver() {
    this.route.navigate(['inicio']);
  }

  verSeguidores() {
    this.modalRef = this.modalService.open(SeguirModalComponent, {
      centered: true,
    });
  }
}
