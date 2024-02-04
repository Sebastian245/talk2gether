import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CambioContraseniaService {

  urlEnviroment: string = environment?.apiUrl;
  currentUrl: string;
  constructor(private httpClient: HttpClient){}

  
  recuperarContrasenia(contra:String, tokenRecuperado:string): Observable<any>{
    const urlEndpoint = this.urlEnviroment + 'usuario/recuperar?'+tokenRecuperado;
    const body = {
        "contrasenia": contra
    }
    return this.httpClient.put(urlEndpoint,body);
  }
}
