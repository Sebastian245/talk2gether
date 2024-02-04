import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, CanActivateFn, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { SesionService } from '../interceptors/sesion.service';
import Swal from 'sweetalert2';

@Injectable({providedIn:'root'})
export class AuthGuard implements CanActivate{
  
  iniciado: boolean;

  constructor(private router:Router,private sesion: SesionService){

  }
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {
    this.iniciado = this.sesion.getCurrentSesion();
    if(this.iniciado!=null){
      this.router.navigate(['/']);
      Swal.fire({
        title: 'Usted se encuentra logueado. No se puede ingresar a la pantalla solicitada',
        showClass: {
          popup: 'animate__animated animate__fadeInDown',
        },
        hideClass: {
          popup: 'animate__animated animate__fadeOutUp',
        },
        confirmButtonColor: '#2b6a78',
      })
      return false;
    } else {
      return true;
    }
  }
  
}



