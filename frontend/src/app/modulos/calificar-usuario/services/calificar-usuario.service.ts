import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CalificarUsuarioService {

  constructor(private httpClient: HttpClient) { }
  url: string = environment?.apiUrl;


  calificarUsuario(idCuentaCalificador:number,idCuentaCalificado:number,idReunionVirtual:number,cantidadEstrellas:number) {
    const urlEndpoint = this.url + 'usuario/calificarusuario';
    const body ={
      "idCuentaCalificador": idCuentaCalificador,
      "idCuentaCalificado": idCuentaCalificado,
      "idReunionVirtual": idReunionVirtual,
      "cantidadEstrellas": cantidadEstrellas
  }
    return this.httpClient.put(urlEndpoint,body);
  }
}
