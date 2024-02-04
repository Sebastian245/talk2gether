import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class IdiomaService {
  constructor(private httpClient: HttpClient) { }
  url: string = environment?.apiUrl;

  id = new BehaviorSubject<number>(null);
  id$ = this.id.asObservable();

  nombreIdioma = new BehaviorSubject<string>(null);
  nombreIdioma$ = this.nombreIdioma.asObservable();

  traerIdioma() {
    const urlEndpoint = this.url + 'idiomas/listaridiomas';
    return this.httpClient.get(urlEndpoint);
  }
  habilitarIdioma(id:number) {
    const urlEndpoint = this.url + 'idiomas/habilitaridioma/'+id;
    return this.httpClient.get(urlEndpoint);
  }
  desHabilitarIdioma(id:number) {
    const urlEndpoint = this.url + 'idiomas/deshabilitaridioma/'+id;
    return this.httpClient.get(urlEndpoint);
  }

  modificarIdioma(id:number, nombreIdioma:string ) {
    const urlEndpoint = this.url + 'idiomas/actualizaridioma/'+id;
    const body = {
        "nombreIdioma": nombreIdioma
    }
    return this.httpClient.put(urlEndpoint,body);
  }
  agregarIdioma(nombreIdioma:string){
    const urlEndpoint = this.url + 'idiomas/crearidioma';
    const body ={
      "nombreIdioma":nombreIdioma
    }
    return this.httpClient.post(urlEndpoint,body);
  }

  guardarId(id:number){
    this.id.next(id);
  }
  getId(){
    return this.id$;
  }
  guardarIdioma(nombreIdioma:string){
    this.nombreIdioma.next(nombreIdioma);
  }
  getIdioma(){
    return this.nombreIdioma$;
  }

 
}
