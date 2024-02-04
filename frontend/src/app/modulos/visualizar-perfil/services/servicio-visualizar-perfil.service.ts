import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { SesionService } from 'src/app/interceptors/sesion.service';
import { environment } from 'src/environments/environment';
import { UsuarioVisualizarPerfil } from '../models/usuario-visualizar';
import { itemModal } from '../models/item-modal';
import { COMETCHAT_CONSTANTS } from 'src/CONSTS';

@Injectable({
  providedIn: 'root',
})
export class ServicioVisualizarPerfilService {
  url: string = environment?.apiUrl;
  urlEstadisticas = environment?.apiUrl + 'cuenta/visualizarestadisticasaprendiz';
  urlLogros = environment?.apiUrl + 'gamificacion/obtenerlogros';

  idMiPerfil = new BehaviorSubject<number>(null);
  idMiPerfil$ = this.idMiPerfil.asObservable();

  id = new BehaviorSubject<number>(null);
  id$ = this.id.asObservable();

  datos = new BehaviorSubject<UsuarioVisualizarPerfil>(null);
  datos$= this.datos.asObservable();

  seguidores = new BehaviorSubject<itemModal[]>(null);
  seguidores$= this.seguidores.asObservable();

  seguidos = new BehaviorSubject<itemModal[]>(null);
  seguidos$= this.seguidos.asObservable();

  constructor(
    private httpClient: HttpClient
  ) {}

  traerPerfil(idPerfil: number) {
    const urlEndpoint = this.url + 'usuario/visualizarotroperfil?idCuenta=' + idPerfil;
    return this.httpClient.get(urlEndpoint);
  }

  bloquearUsuarioChat(uidUsuarioQueBloquea:number, uidUsuarioQueBloqueo:number){
    console.log(uidUsuarioQueBloquea,uidUsuarioQueBloqueo)
    const options = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        apiKey: "3647b5fc3ed4cdcc9cc67d33c8e7a77b4576873b",
      }),
    };
    const uidBloquear: string[] = [];
    uidBloquear.push(uidUsuarioQueBloqueo.toString());
    console.log(uidBloquear)
    const urlEndpoint = "https://245472a631aa053e.api-us.cometchat.io/v3/users/"+uidUsuarioQueBloquea+"/blockedusers"
    const body = {
      "blockedUids": uidBloquear
    }
    return this.httpClient.post(urlEndpoint, body,options);
  }

  traerSeguidosSeguidores(idPerfil: number) {
    const urlEndpoint = this.url + 'cuenta/obtenercantidadSeguidosSeguidores?idCuenta=' + idPerfil;
    return this.httpClient.get(urlEndpoint);
  }

  desBloquearUsuarioChat(uidUsuarioQueBloquea:number, uidUsuarioQueBloqueo:number){
    const uidDesBloquear: string[] = [];
    uidDesBloquear.push(uidUsuarioQueBloqueo.toString());
    const body_user = {
      "blockedUids": uidDesBloquear
    }
    const options = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        apiKey: COMETCHAT_CONSTANTS.API_KEY,
      }),
      body: body_user
    };
    const urlEndpoint = "https://245472a631aa053e.api-us.cometchat.io/v3/users/"+uidUsuarioQueBloquea+"/blockedusers"

    return this.httpClient.delete(urlEndpoint, options);
  }

  bloquearUsuario(idUsuarioQueBloquea:number, idUsuarioBloqueado:number){
    const urlEndpoint = this.url + 'cuenta/bloquear';
    const body = {
      "idUsuarioQueBloquea": idUsuarioQueBloquea,
      "idUsuarioBloqueado": idUsuarioBloqueado
    }
    return this.httpClient.post(urlEndpoint, body);
  }

  desBloqueo(idUsuarioQueDesbloquea:number, idUsuarioDesbloqueado:number){
    const urlEndpoint = this.url + 'cuenta/desbloquear';
    const body = {
      "idUsuarioQueDesbloquea": idUsuarioQueDesbloquea,
      "idUsuarioDesbloqueado": idUsuarioDesbloqueado
    }

    return this.httpClient.post(urlEndpoint, body);
  }

  revisarBloqueo(idUsuarioQueBloquea:number, idUsuarioBloqueado:number){
      const urlEndpoint = this.url + 'cuenta/verificarbloqueo';
      const body = {
        "idUsuarioQueBloquea": idUsuarioQueBloquea,
        "idUsuarioBloqueado": idUsuarioBloqueado
      }
      return this.httpClient.post(urlEndpoint, body);
  }

  seguirUsuario(idMiPerfil, idGuest){
      const urlEndpoint = this.url + 'cuenta/seguirusuario';
      const body = {
        "idUsuarioSeguidor": idMiPerfil,
        "idUsuarioSeguido": idGuest
      }
      return this.httpClient.post(urlEndpoint, body);
  }

  dejarSeguirUsuario(idMiPerfil, idGuest){
    const urlEndpoint = this.url + 'cuenta/dejardeseguirusuario';
    const body = {
      "idUsuarioSeguidor": idMiPerfil,
      "idUsuarioSeguido": idGuest
    }
    return this.httpClient.post(urlEndpoint, body);
  }

  revisarSeguir(idMiPerfil, idGuest){
    const urlEndpoint = this.url + 'cuenta/verificarseguimiento';
    const body = {
      "idUsuarioSeguidor": idMiPerfil,
      "idUsuarioSeguido": idGuest
    }
    return this.httpClient.post(urlEndpoint, body);
  }

  traerSeguidores(id:number){
    const urlEndpoint = this.url + 'cuenta/listarseguidores?idCuenta='+id;
    return this.httpClient.get(urlEndpoint);
  }

  traerSeguidos(id:number){
    const urlEndpoint = this.url + 'cuenta/listarseguidos?idCuenta='+id;
    return this.httpClient.get(urlEndpoint);
  }

  guardarDatos(datosUsuario:UsuarioVisualizarPerfil){
    this.datos.next(datosUsuario);
  }

  getDatos(){
    return this.datos$;
  }

  guardarSeguidores(items:itemModal[]){
    this.seguidores.next(items);
  }

  getSeguidores(){
    return this.seguidores$;
  }

  guardarSeguidos(items:itemModal[]){
    this.seguidos.next(items);
  }

  getSeguidos(){
    return this.seguidos$;
  }

  guardaridMiPerfil(id:number){
    this.idMiPerfil.next(id);
  }
  getidMiPerfil(){
    return this.idMiPerfil$;
  }

  guardarId(id:number){
    this.id.next(id);
  }

  getId(){
    return this.id$;
  }

  obtenerEstadisticas(idCuenta: number){
    return this.httpClient.get(this.urlEstadisticas + '?idCuenta=' + idCuenta);
  }

  obtenerLogros(idCuenta: number){
    return this.httpClient.get<any>(this.urlLogros + '?idCuenta=' + idCuenta);
  }

}
