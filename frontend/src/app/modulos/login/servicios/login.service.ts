import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Usuario } from '../modelos/Usuario';
import { SesionService } from 'src/app/interceptors/sesion.service';

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  url: string = environment?.apiUrl;
  motivosReporte = new BehaviorSubject<string[]>(null);
  motivosReporte$ = this.motivosReporte.asObservable();

  rol = new BehaviorSubject<string>(null);
  rol$ = this.rol.asObservable();
  
  constructor(
    private httpClient: HttpClient,
    private sesionService: SesionService
  ) {}

  login(correo: string, contrasenia: string) {
    const options = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
    };
    const urlEndpoint = this.url + 'iniciarsesion';
    const body: Usuario = {
      correo,
      contrasenia,
    };
    return this.httpClient.post(urlEndpoint, body, options).pipe(
      tap((response) => {
        const token = response;
        this.sesionService.startLocalSession(token);
      })
    );
  }

  guardarMotivo(motivos: string[]) {
    this.motivosReporte.next(motivos);
  }
  getMotivo(): Observable<string[]>{
    return  this.motivosReporte$; 
  }

  logout() {
    this.sesionService.clearLocalSession();
  }

  traerRutasPermitidas() {
    const urlEndpoint = this.url + 'permisos/listarPermisosParaUnRol';
    return this.httpClient.get(urlEndpoint);
  }
  guardarRol(rol: string) {
    this.rol.next(rol);
  }
  getRol(): Observable<string>{
    return this.rol$; 
  }
}
