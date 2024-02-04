import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UsuariosBloqueadosService {

  usuariosBloqueadosURL = environment.apiUrl + 'cuenta/usuariosbloqueados';

  constructor(private httpClient: HttpClient) { }

  listaUsuariosBloqueados(idCuenta: number) {
    return this.httpClient.get<any>(this.usuariosBloqueadosURL + '?idCuenta=' + idCuenta);
  }

}
