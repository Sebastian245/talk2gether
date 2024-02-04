import {
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { SesionService } from './sesion.service';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {
  constructor(private sesionService: SesionService) {}

  intercept(
    request: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    if (this.sesionService.isLoggedIn()) {

      request = request.clone({
        setHeaders: {
          token: this.sesionService.getCurrentSesion()['token']
        }
      });
    }
    return next.handle(request);
  }
}
