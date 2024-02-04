import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  NavigationEnd,
  Router,
  RouterStateSnapshot,
  UrlTree,
} from '@angular/router';
import { Observable, of, switchMap } from 'rxjs';
import { SesionService } from '../interceptors/sesion.service';
import Swal from 'sweetalert2';
import { LoginService } from '../modulos/login/servicios/login.service';

@Injectable({ providedIn: 'root' })
export class blockedRoutes implements CanActivate {
  iniciado: boolean;
  currentUrl: string;
  routes: string[];
  respuesta: string;

  constructor(
    private router: Router,
    private sesion: SesionService,
    private routesServices: LoginService
  ) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean> {
    const iniciado = this.sesion.getCurrentSesion();
    if (iniciado != null) {
      return this.routesServices.traerRutasPermitidas().pipe(
        switchMap((rutasPermitidas) => {
          const currentUrl = state.url.split('?')[0];
          const parts = currentUrl.split('/');
          let extractedPart='';
          if (parts[1] === 'administrador') {
            extractedPart = parts.join('/');
          } else if (parts[1] === 'perfilUsuario') {
            extractedPart = '/'+parts[1]; 
          } else if (parts[1] === 'chat') {
            extractedPart = '/'+parts[1];
          } else if (parts[1]+'/'+parts[2] === 'perfilUsuario/logros') {
            extractedPart = '/'+parts[1]+'/'+parts[2];
          } else if (parts[1]+'/'+parts[2] === 'perfilUsuario/estaditicas') {
            extractedPart = '/'+parts[1];   
          } else {
            extractedPart = '/'+parts[1]
          }
          const rutasPermitidasArray = Object.values(rutasPermitidas);
          if (!rutasPermitidasArray.includes(extractedPart)) {
            Swal.fire({
              title: 'No puede ingresar a la pagina solicitada',
              icon: 'warning',
              confirmButtonColor: '#2b6a78',
            });
            this.router.navigateByUrl('');
            return of(false);
          } else {
            return of(true);
          }
        })
      );
    } else {
      this.router.navigate(['/']);
      return of(false);
    }
  }
}
