import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class RolService {

  constructor(private httpClient: HttpClient) { }
  url: string = environment?.apiUrl;

  id = new BehaviorSubject<number>(null);
  id$ = this.id.asObservable();
  
  nombre = new BehaviorSubject<string>(null);
  nombre$ = this.nombre.asObservable();

  
  traerRoles() {
    const urlEndpoint = this.url + 'roles/listarroles';
    return this.httpClient.get(urlEndpoint);
  }
  habilitarRol(id:number) {
    const urlEndpoint = this.url + 'roles/habilitarrol/'+id;
    return this.httpClient.get(urlEndpoint);
  }
  desHabilitarRol(id:number) {
    const urlEndpoint = this.url + 'roles/deshabilitarrol/'+id;
    return this.httpClient.get(urlEndpoint);
  }

  modificarRol(id:number, nombreRol:string) {
    const urlEndpoint = this.url + 'roles/actualizarrol/'+id;
    const body = {
        "nombreRol": nombreRol,
    }
    return this.httpClient.put(urlEndpoint,body);
  }
  agregarRol(nombreRol:string){
    const urlEndpoint = this.url + 'roles/crearrol';
    const body ={
      "nombreRol":nombreRol,
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
  

}
