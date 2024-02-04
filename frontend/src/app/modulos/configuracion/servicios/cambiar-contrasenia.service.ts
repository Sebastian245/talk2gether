import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CambiarContraseniaService {

  cambiarContraseniaURL = environment.apiUrl + 'usuario/cambiarcontrasenia';

  constructor(private httpClient: HttpClient) { }

  cambiarContrasenia(cambiarContrasenia: any){
    const body =  cambiarContrasenia;
    return this.httpClient.put(this.cambiarContraseniaURL, body);
  }

}
