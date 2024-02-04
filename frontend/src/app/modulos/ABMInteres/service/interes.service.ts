import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class InteresService {

 constructor(private httpClient: HttpClient) { }
  url: string = environment?.apiUrl;

  id = new BehaviorSubject<number>(null);
  id$ = this.id.asObservable();

  nombreInteres = new BehaviorSubject<string>(null);
  nombreInteres$ = this.nombreInteres.asObservable();

  icono = new BehaviorSubject<string>(null);
  icono$ = this.icono.asObservable();

  traerInteres() {
    const urlEndpoint = this.url + 'intereses/listarintereses';
    return this.httpClient.get(urlEndpoint);
  }

  habilitarInteres(id:number) {
    const urlEndpoint = this.url + 'intereses/habilitarinteres/'+id;
    return this.httpClient.get(urlEndpoint);
  }
  desHabilitarInteres(id:number) {
    const urlEndpoint = this.url + 'intereses/deshabilitarinteres/'+id;
    return this.httpClient.get(urlEndpoint);
  }

  modificarInteres(id:number, nombreInteres:string, urlIcono:string ) {
    const urlEndpoint = this.url + 'intereses/actualizarinteres/'+id;
    const body = {
        "nombreInteres": nombreInteres,
        "urlInteres": urlIcono
    }
    return this.httpClient.put(urlEndpoint,body);
  }
  crearInteres(nombreIinteres:string, iconoInteres:string){
    const urlEndpoint = this.url + 'intereses/crearinteres';
    const body ={
      "nombreInteres": nombreIinteres,
      "urlInteres": iconoInteres
    }
    return this.httpClient.post(urlEndpoint,body);
  }

  guardarId(id:number){
    this.id.next(id);
  }
  getId(){
    return this.id$;
  }
  guardarInteres(nombreInteres:string){
    this.nombreInteres.next(nombreInteres);
  }
  getInteres(){
    return this.nombreInteres$;
  }
  guardarIcono(icono:string){
    this.icono.next(icono);
  }
  getIcono(){
    return this.icono$;
  }
}

