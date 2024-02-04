import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PermisosService {

  constructor(private httpClient: HttpClient) { }
  url: string = environment?.apiUrl;

  id = new BehaviorSubject<number>(null);
  id$ = this.id.asObservable();
  
  ruta = new BehaviorSubject<string>(null);
  ruta$ = this.ruta.asObservable();


  nombreRolList = new BehaviorSubject<string[]>(null);
  nombreRolList$ = this.nombreRolList.asObservable();

  traerPermisos() {
    const urlEndpoint = this.url + 'permisos/listarpermisos';
    return this.httpClient.get(urlEndpoint);
  }
  
  
  editarPermiso(nombreRuta:string, rolList:string[]) {
    const urlEndpoint = this.url + 'permisos/editarpermiso';
    const body = {
      "nombrePermiso": nombreRuta,
      "listaNombreRol": rolList
    }
    return this.httpClient.put(urlEndpoint, body);
  }

  guardarId(id:number){
    this.id.next(id);
  }
  getId(){
    return this.id$;
  }

  guardarRuta(ruta:string){
    this.ruta.next(ruta);
  }
  getRuta(){
    return this.ruta$;
  }
  guardarNombreRolList(nombreRolList:string[]){
    this.nombreRolList.next(nombreRolList);
  }
  getNombreRolList(){
    return this.nombreRolList$;
  }
}

