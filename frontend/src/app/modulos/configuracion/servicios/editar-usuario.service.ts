import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class EditarUsuarioService {
  datosUsuario = new BehaviorSubject<string>(null);
  datosUsuario$ = this.datosUsuario.asObservable();

  idCuenta = new BehaviorSubject<number>(null);
  idCuenta$ = this.datosUsuario.asObservable();

  datosPersonalesURL = environment.apiUrl + 'cuenta/obtenerdatospersonales';
  editarUsuarioURL = environment.apiUrl + 'usuario/modificarUsuario';

  constructor(private httpClient: HttpClient) { }

  obtenerDatosPersonales(idCuenta: number) {
    return this.httpClient.get(this.datosPersonalesURL + '?idCuenta=' + idCuenta);
  }

  editarUsuario(usuario: any, idCuenta: number): Observable<any> {
    const body = usuario;
    return this.httpClient.put(this.editarUsuarioURL + '?idCuenta=' + idCuenta , body, {responseType:'text'});
  }

  guardarInformacion(datosUsuario: any){
    this.datosUsuario.next(datosUsuario);
  }

  getInformacion(){
    return this.datosUsuario$;
  }

  guardarId(idCuenta: number){
    this.idCuenta.next(idCuenta);
  }

  getIdCuenta(){
    return this.idCuenta;
  }

}
