import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { environment } from 'src/environments/environment';
import { UsuarioPost } from '../modelo/usuarioPost';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  constructor(private httpClient: HttpClient) { }
  url: string = environment?.apiUrl;

  id = new BehaviorSubject<number>(null);
  id$ = this.id.asObservable();
  
  nombreApellido = new BehaviorSubject<string>(null);
  nombreApellido$ = this.nombreApellido.asObservable();

  correo = new BehaviorSubject<string>(null);
  correo$ = this.correo.asObservable();

  nombreRol = new BehaviorSubject<string>(null);
  nombreRol$ = this.nombreRol.asObservable();

  
  traerUsuarios() {
    const urlEndpoint = this.url + 'administrador/listarUsuarios';
    return this.httpClient.get(urlEndpoint);
  }
  
  traerRoles(){
    const urlEndpoint = this.url + 'roles/listarrolesactivos';
    return this.httpClient.get(urlEndpoint);
  }

  traerMotivos(){
    const urlEndpoint = this.url +'motivos/listarmotivosactivos?nombreTipoMotivo=plataforma'
    return this.httpClient.get(urlEndpoint);

  }

  buscador(cadena: string){
    const urlEndpoint = this.url +'administrador/filtrarusuarios?filtroParametro='+cadena
    return this.httpClient.get(urlEndpoint);

  }

  

  modificarUsuario(id:number, rol:string) {
    const urlEndpoint = this.url + 'administrador/modificarUsuario?idCuenta='+id;
    const body = {
        "nombreRol": rol
    }
    return this.httpClient.put(urlEndpoint,body);
  }

  agregarUsuario(usuario:UsuarioPost){
    const urlEndpoint = this.url +'administrador/crearUsuario'
    const body = {
      "nombreUsuario": usuario.nombreUsuario,
      "apellidoUsuario": usuario.apellidoUsuario,
      "fechaNacimiento": usuario.fechaNacimiento,
      "correo": usuario.correo,
      "contrasenia": usuario.contrasenia,
      "nombreRol":usuario.rol
    }
    return this.httpClient.post(urlEndpoint,body);
  }

  bajaUsuario(idCuenta:number,idAdministradorResponsable:number, listaMotivos:string[], descripcion:string){
    const urlEndpoint = this.url + 'administrador/eliminarCuentaUsuario';
    const body = {
      "idCuenta": idCuenta,
      "idAdministradorResponsable":idAdministradorResponsable,
      "listaMotivos": listaMotivos,
      "descripcionCuentaEliminada": descripcion
    }
    return this.httpClient.put(urlEndpoint,body);
  }

  visualizarCalificaciones(idCuenta:number){
    const urlEndpoint = this.url + 'administrador/obtenercalificaciones?idCuenta='+idCuenta;
    return this.httpClient.get(urlEndpoint);

  }
  visualizarCalificacionesFiltradas(idCuenta:number, fechaDesde:string, fechaHasta:string){
    const urlEndpoint = this.url + 'administrador/filtrarcalificaciones?idCuenta='+idCuenta+'&fechaDesde='+fechaDesde+'&fechaHasta='+fechaHasta;
    return this.httpClient.get(urlEndpoint);
  }

  guardarId(id:number){
    this.id.next(id);
  }
  getId(){
    return this.id$;
  }
  guardarNombreApellido(nombreApellido:string){
    this.nombreApellido.next(nombreApellido);
  }
  getNombreApellido(){
    return this.nombreApellido$;
  }
  guardarcorreo(correo:string){
    this.correo.next(correo);
  }
  getcorreo(){
    return this.correo$;
  }
  
  guardarNombreRol(nombreRol:string){
    this.nombreRol.next(nombreRol);
  }
  getNombreRol(){
    return this.nombreRol$;
  }



}
