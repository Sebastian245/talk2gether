import { Component, OnInit } from '@angular/core';
import { catchError, combineLatest, map, of, switchMap, take, tap } from 'rxjs';
import { NavbarService } from 'src/app/compartidos/navbar/service/navbar.service';
import { ServicioVisualizarPerfilService } from '../services/servicio-visualizar-perfil.service';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { SpinnerServiceGeneral } from 'src/app/compartidos/spinner-carga-general/spinner.service';

@Component({
  selector: 'app-bloqueado',
  templateUrl: './bloqueado.component.html',
  styleUrls: ['./bloqueado.component.scss'],
})
export class BloqueadoComponent implements OnInit {
  nombreUsuario: string;
  urlFoto: string;
  apellido: string;
  edad: string;
  id: number;
  idMiPerfil: number;
  idGuest: number;
  constructor(
    private visualizarService: ServicioVisualizarPerfilService,
    private route: Router,
    private spinner: SpinnerServiceGeneral
  ) {}
  ngOnInit(): void {
    this.spinner.hideSpinner();
    combineLatest([
      this.visualizarService.getDatos(),
      this.visualizarService.getId(),
      this.visualizarService.getidMiPerfil(),
    ])
      .pipe(
        take(1),
        map(([datos, id, idMiPerfil]) => {
          console.log('qweqweqwe', id)
          this.urlFoto = datos['urlFoto'];
          this.nombreUsuario = datos['nombre'];
          this.apellido = datos['apellido'];
          this.edad = datos['edad'];
          this.id = id;
          this.idMiPerfil = idMiPerfil;
        })
      )
      .subscribe();
    console.log(this.id);
    if (this.id == null) {
      Swal.fire({
        icon: 'error',
        title: 'Oops...',
        text: 'No ingreso correctamente a la pantalla.',
      });
      this.route.navigate(['/']);
    }
  }

  desbloquear() {
    Swal.fire({
      title:
        '¿Estás seguro que deseas desbloquear a ' +
        this.nombreUsuario +
        ' ' +
        this.apellido +
        ' ?',
      icon: 'warning',
      showCancelButton: true,
      cancelButtonColor: '#909293',
      confirmButtonColor: '#199DE8',
      background: 'FFFFFF',
      cancelButtonText: 'Cancelar',
      confirmButtonText: 'Si, desbloquear',
    }).then((result) => {
      this.spinner.showSpinner();
      if (result.isConfirmed) {
        combineLatest([
          this.visualizarService.desBloqueo(this.idMiPerfil, this.id),
          this.visualizarService.getId(),
        ])
          .pipe(
            take(1),
            switchMap(([desbloqueo, id]) => {
              
              this.idGuest = id;
              return this.visualizarService.desBloquearUsuarioChat(
                this.idMiPerfil,
                id
              );
            }),
            catchError(() => {
              Swal.fire({
                icon: 'error',
                title: 'Oops...',
                text: 'Algo salió mal! Intente nuevamente mas tarde.',
              });
              return of(false);
            })
          )
          .subscribe(() => {
            this.spinner.hideSpinner();
            this.visualizarService.guardarId(null)
            this.route.navigate(['perfilUsuario/' + this.id]);
          });

  
      } else {
        this.spinner.hideSpinner();
        this.route.navigate(['perfilUsuario/' + this.id]);
      }
    });
  }
  redireccionInicio() {
    this.route.navigate(['/inicio']);
  }
}
