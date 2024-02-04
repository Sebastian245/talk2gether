import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class RecuperarContraseniaService {

  url: string = environment?.apiUrl;

  constructor(private httpClient: HttpClient){}


  recuperarContrasenia(correo:string): Observable<any>{
    const urlEndpoint = this.url + 'usuario/recuperarContrasenia';
    const body = {
        "correo": correo
    }

    return this.httpClient.put(urlEndpoint,body);
  }
}

