import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Usuario } from '../modelos/usuario';
import { Interes } from '../registrar-usuario2/models/Interes';

@Injectable({
  providedIn: 'root'
})
export class RegistroService {

  registroURL = environment.apiUrl + 'usuario/registrarse';
  obtenerInteresesURL = environment.apiUrl + 'intereses/listarinteresesactivos';

  constructor(private httpClient: HttpClient) { }

  crear(usuario: Usuario): Observable<any> {
    const options = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    const body: Usuario = usuario;
    return this.httpClient.post(this.registroURL, body, {responseType:'text'});
  }

  crearReferido(usuario: Usuario, referidoValue: string): Observable<any> {
    const options = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    const body: Usuario = usuario;
    return this.httpClient.post(this.registroURL + '?idCuenta=' + referidoValue, body, {responseType:'text'});
  }

  opcionesPais(){
    return this.httpClient.get(environment.apiUrl +'paises/listarpaisesactivos');
  }

  opcionesIdiomas() {
    return this.httpClient.get(environment.apiUrl +'idiomas/listaridiomasactivos');
  }

  opcionesNivelIdiomas() {
    return this.httpClient.get(environment.apiUrl + 'nivelesidioma/listarnivelesidiomaactivos');
  }

  obtenerIntereses(){
    return this.httpClient.get(this.obtenerInteresesURL);
  }

}
