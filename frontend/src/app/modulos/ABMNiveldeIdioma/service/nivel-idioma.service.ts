import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class NivelIdiomaService {
  constructor(private httpClient: HttpClient) { }
  url: string = environment?.apiUrl;

  id = new BehaviorSubject<number>(null);
  id$ = this.id.asObservable();

  nombreNivelIdioma = new BehaviorSubject<string>(null);
  nombreNivelIdioma$ = this.nombreNivelIdioma.asObservable();

  traerNivelIdioma() {
    const urlEndpoint = this.url + 'nivelesidioma/listarnivelesidioma';
    return this.httpClient.get(urlEndpoint);
  }
  habilitarNivelIdioma(id:number) {
    const urlEndpoint = this.url + 'nivelesidioma/habilitarnivelidioma/'+id;
    return this.httpClient.get(urlEndpoint);
  }
  desHabilitarNivelIdioma(id:number) {
    const urlEndpoint = this.url + 'nivelesidioma/deshabilitarnivelidioma/'+id;
    return this.httpClient.get(urlEndpoint);
  }

  modificarNivelIdioma(id:number, nombreNivelIdioma:string ) {
    const urlEndpoint = this.url + 'nivelesidioma/actualizarnivelidioma/'+id;
    const body = {
        "nombreNivelIdioma": nombreNivelIdioma
    }
    return this.httpClient.put(urlEndpoint,body);
  }

  agregarNivelIdioma(nombreNivelIdioma:string){
    const urlEndpoint = this.url + 'nivelesidioma/crearnivelidioma';
    const body ={
      "nombreNivelIdioma":nombreNivelIdioma
    }
    return this.httpClient.post(urlEndpoint,body);
  }
  guardarId(id:number){
    this.id.next(id);
  }
  getId(){
    return this.id$;
  }
  guardarNivelIdioma(nombreNivelIdioma:string){
    this.nombreNivelIdioma.next(nombreNivelIdioma);
  }
  getNivelIdioma(){
    return this.nombreNivelIdioma$;
  }
}
