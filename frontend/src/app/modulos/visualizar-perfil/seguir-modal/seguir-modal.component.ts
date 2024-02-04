import { Component, ViewEncapsulation } from '@angular/core';
import { ModalService } from '../../registro/servicios/modal.service';
import { Router } from '@angular/router';
import { itemModal } from '../models/item-modal';
import { ServicioVisualizarPerfilService } from '../services/servicio-visualizar-perfil.service';
import { Subscription, catchError, filter, of, switchMap, take, tap } from 'rxjs';
import Swal from 'sweetalert2';
import { SpinnerServiceGeneral } from 'src/app/compartidos/spinner-carga-general/spinner.service';

@Component({
  selector: 'app-seguir-modal',
  templateUrl: './seguir-modal.component.html',
  styleUrls: ['./seguir-modal.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class SeguirModalComponent {
  cargando: boolean = true;
  id: number;
  item: itemModal;
  items: itemModal[] = [];
  seguidos$: Subscription;
  seguidores$: Subscription;
  estaEnSeguidores: boolean = true;
  estaEnSeguidos: boolean = false;
  usuariosSeguidores: number;
  usuariosSeguidos: number;
  flag: boolean = false;
  constructor(
    private router: Router,
    private modalService: ModalService,
    private visualizarService: ServicioVisualizarPerfilService,
    private route: Router,
    private spinner: SpinnerServiceGeneral
  ) {}

  dismissModal() {
    this.modalService.dismissActiveModal();
  }

  ngOnInit() {
    this.spinner.showSpinner();
    this.visualizarService
    .getId()
    .pipe(
      filter((response) => response != null),
      switchMap((response) => {
        this.id = response;
        return this.visualizarService.traerSeguidosSeguidores(this.id);
      }),
      catchError((error) => {
        this.spinner.hideSpinner();
        Swal.fire({
          icon: 'error',
          title: 'Oops...',
          text: 'Algo salió mal! Intente nuevamente mas tarde.',
        });
        return of(null);
      })
    )
    .subscribe({
      next: (r) => {
        this.spinner.hideSpinner();
        this.flag = true;
        this.usuariosSeguidores = r['cantidadSeguidores'];
        this.usuariosSeguidos = r['cantidadSeguidos'];
        this.mostrarSeguidores();
      }
    });
  }

  visitarPerfil(id) {
    this.router.navigate(['perfilUsuario', id]);
    this.dismissModal();
  }

  mostrarSeguidos() {
    if (this.seguidores$) this.seguidores$.unsubscribe();
    this.estaEnSeguidos = true;
    this.estaEnSeguidores = false;
    this.cargando = true;
    this.items = [];
    this.seguidos$ = this.visualizarService.traerSeguidos(this.id).subscribe({
      next: (response: any[]) => {
        for (let item of response) {
          this.item = {
            nombreUsuario: response['nombre'],
            apellidoUsuario: response['apellido'],
            urlFoto: response['image'],
            id: response['id'],
          };
          this.items.push(item);
        }
        console.log(this.items);
        this.cargando = false;
      },
      error: (error) => {
        Swal.fire({
          icon: 'error',
          title: 'Oops...',
          text: 'Algo salió mal! Intente nuevamente mas tarde.',
        });
        this.route.navigate(['']);
        this.cargando = false;
      },
    });
  }

  mostrarSeguidores() {
    if (this.seguidos$) this.seguidos$.unsubscribe();
    this.estaEnSeguidores = true;
    this.estaEnSeguidos = false;
    this.cargando = true;
    this.items = [];
    this.seguidores$ = this.visualizarService.traerSeguidores(this.id).subscribe({
      next: (response: any[]) => {
        for (let item of response) {
          this.item = {
            nombreUsuario: response['nombre'],
            apellidoUsuario: response['apellido'],
            urlFoto: response['image'],
            id: response['id'],
          };
          this.items.push(item);
        }
        this.visualizarService.guardarSeguidores(this.items);
        this.cargando = false;
      },
      error: (error) => {
        Swal.fire({
          icon: 'error',
          title: 'Oops...',
          text: 'Algo salió mal! Intente nuevamente mas tarde.',
        });
        this.cargando = false;
        this.route.navigate(['perfilUsuario/', this.id]);
      },
    });
  }
}