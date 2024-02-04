import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PaisService {

  constructor(private httpClient: HttpClient) { }
  url: string = environment?.apiUrl;

  id = new BehaviorSubject<number>(null);
  id$ = this.id.asObservable();
  
  nombre = new BehaviorSubject<string>(null);
  nombre$ = this.nombre.asObservable();

  urlPais = new BehaviorSubject<string>(null);
  urlPais$ = this.urlPais.asObservable();
  
  traerPais() {
    const urlEndpoint = this.url + 'paises/listarpaises';
    return this.httpClient.get(urlEndpoint);
  }
  habilitarPais(id:number) {
    const urlEndpoint = this.url + 'paises/habilitarpais/'+id;
    return this.httpClient.get(urlEndpoint);
  }
  desHabilitarPais(id:number) {
    const urlEndpoint = this.url + 'paises/deshabilitarpais/'+id;
    return this.httpClient.get(urlEndpoint);
  }

  modificarPais(id:number, nombrePais:string, urlBandera:string) {
    const urlEndpoint = this.url + 'paises/actualizarpais/'+id;
    const body = {
        "nombrePais": nombrePais,
        "urlBandera": urlBandera
    }
    return this.httpClient.put(urlEndpoint,body);
  }
  agregarPais(nombrePais:string, urlPais:string){
    const urlEndpoint = this.url + 'paises/crearpais';
    const body ={
      "nombrePais":nombrePais,
      "urlBandera":urlPais
    }
    return this.httpClient.post(urlEndpoint,body);
  }
  guardarId(id:number){
    this.id.next(id);
  }
  getId(){
    return this.id$;
  }

  guardarNombre(nombre:string){
    this.nombre.next(nombre);
  }
  getNombre(){
    return this.nombre$;
  }
  
  guardarUrlPais(UrlPais:string){
    this.urlPais.next(UrlPais);
  }
  getUrlPais(){
    return this.urlPais$;
  }

}
