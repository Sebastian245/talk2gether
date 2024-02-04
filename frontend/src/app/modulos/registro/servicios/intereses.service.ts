import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Interes } from '../registrar-usuario2/models/Interes';

@Injectable({
  providedIn: 'root'
})
export class InteresesService {

  //localhost:8080/intereses/listarinteresesactivos
  obtenerInteresesURL = environment.apiUrl + 'intereses/listarinteresesactivos';

  constructor(private httpClient: HttpClient) { }

  obtenerIntereses(){
    return this.httpClient.get<Interes[]>(this.obtenerInteresesURL);
  }

}
