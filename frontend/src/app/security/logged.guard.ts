import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { SesionService } from '../interceptors/sesion.service';
import Swal from 'sweetalert2';

@Injectable({providedIn:'root'})
export class loggedGuard implements CanActivate{
  iniciado: boolean;

  constructor(
    private router: Router,
    private sesion: SesionService
    ){}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {
    this.iniciado = this.sesion.getCurrentSesion();
    if(this.iniciado==null){
      Swal.fire({
        title: 'Por favor inicie sesión para ingresar a la página solicitada.',
        showClass: {
          popup: 'animate__animated animate__fadeInDown',
        },
        hideClass: {
          popup: 'animate__animated animate__fadeOutUp',
        },
        confirmButtonColor: '#2b6a78',
      })
      this.router.navigate(['/']);
      return false;
    } else {
      return true;
    }
  }
};
