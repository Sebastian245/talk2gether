import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class EliminarCuentaService {

  eliminarCuentaURL = environment.apiUrl + 'usuario/eliminarcuenta';
  listarMotivosURL = environment.apiUrl + 'motivos/listarmotivosactivos?nombreTipoMotivo=usuario';

  constructor(private httpClient: HttpClient) { }

  listarMotivosEliminacion() {
    return this.httpClient.get<any>(this.listarMotivosURL);
  }

  eliminarCuenta(eliminacion: any){
    const body = eliminacion;
    return this.httpClient.put(this.eliminarCuentaURL, body, {responseType:'text'});
  }

}
