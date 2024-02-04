import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { SesionService } from 'src/app/interceptors/sesion.service';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class NavbarService {
  cantidadChats = new BehaviorSubject<number>(null);
  cantidadChats$ = this.cantidadChats.asObservable();

  idLogueado = new BehaviorSubject<string>(null);
  idLogueado$ = this.idLogueado.asObservable();

  token : string;
  url: string = environment?.apiUrl;
  constructor(private httpClient: HttpClient, private sesionService: SesionService) { 
  }

  traerUsuario(){
    const urlEndpoint = this.url + 'actual-usuario';
    
    return this.httpClient.get(urlEndpoint);
  }
  guardarId(cantidadChats:number){
    this.cantidadChats.next(cantidadChats);
  }
  getId(){
    return this.cantidadChats$;
  }
  guardarIdLogueado(idLogueado:string){
    this.idLogueado.next(idLogueado);
  }
  getIdLogueado(){
    return this.idLogueado$;
  }

}


