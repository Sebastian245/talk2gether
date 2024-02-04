import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class VerificarCuentaService {

  verificacionURL = environment.apiUrl + 'usuario/confirm?token=';
  reenviarCorreoURL = environment.apiUrl + 'usuario/reenviarMail';

  constructor(private httpClient: HttpClient) { }

  verificarCuenta(token: string){
    return this.httpClient.get(this.verificacionURL + token);
  }

  reenviarCorreo(correo: string){
    const body = {
      correo: correo
    };
    return this.httpClient.put(this.reenviarCorreoURL, body);
  }

}


