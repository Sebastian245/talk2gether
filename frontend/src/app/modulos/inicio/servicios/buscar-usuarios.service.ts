import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { UsuarioBusqueda } from '../modelos/usuario-busqueda';
import { UsuarioCalificar } from '../modelos/usuario-calificar';

@Injectable({
  providedIn: 'root'
})
export class BuscarUsuariosService {
  linkReunion = new BehaviorSubject<string>(null);
  linkReunion$ = this.linkReunion.asObservable();

  otroUsuario = new BehaviorSubject<UsuarioCalificar>(null);
  otroUsuario$ = this.otroUsuario.asObservable();

  busquedaURL = environment.apiUrl + 'reunionvirtual/listarsalasactivas';
  busquedaFiltradaURL = environment.apiUrl + 'reunionvirtual/listarsalasactivasfiltradas';
  busquedaTextoURL= environment.apiUrl + 'usuario/buscarUsuario?parametroDeBusqueda='
  unirseASalaURL = environment.apiUrl + 'reunionvirtual/unirseasala';
  idiomaURL = environment.apiUrl + 'cuenta/obteneridiomaaprendiz';
  crearSalaURL = environment.apiUrl + 'reunionvirtual/crearsala';
  finalizarVideollamadaURL = environment.apiUrl + 'reunionvirtual/finalizarvideollamada';
  finalizarVideollamadaIDURL = environment.apiUrl + 'reunionvirtual/finalizarvideollamadaPorRefrescar'

  constructor(private httpClient: HttpClient) { }

  buscarUsuarios(idCuenta: number): Observable<any>{
    return this.httpClient.get<UsuarioBusqueda[]>(this.busquedaURL + '?idCuenta=' + idCuenta);
  }

  buscarUsuariosFiltrados(idCuenta: number, busqueda): Observable<any>{
    return this.httpClient.get<UsuarioBusqueda[]>(
      this.busquedaFiltradaURL +
      '?idCuenta=' + idCuenta +
      '&edadMinima=' + (busqueda.edadDesde ?? '') +
      '&edadMaxima=' + (busqueda.edadHasta ?? '') +
      '&intereses=' + (busqueda.intereses ?? '') +
      '&nombrePais=' + (busqueda.pais ?? '') +
      '&nombreNivelIdioma=' + (busqueda.nivelMinimo ?? ''));
  }

  buscarUsuariosBuscador(parametroBusqueda: string): Observable<any>{
    return this.httpClient.get<UsuarioBusqueda[]>(this.busquedaTextoURL + parametroBusqueda);
  }

  unirseAVideollamada(idCuenta: number, linkReunionVirtual: string){
    const body = {}
    return this.httpClient.put(
      this.unirseASalaURL + '?linkReunion=' + linkReunionVirtual +
      '&idSegundoParticipante=' + idCuenta, body);
  }

  crearVideollamada(idCuenta: number){
    const body = {}
    return this.httpClient.post(this.crearSalaURL + '?idCuenta=' + idCuenta, body);
  }

  finalizarVideollamada(linkReunionVirtual: string){
    const body = {}
    return this.httpClient.put(
    this.finalizarVideollamadaURL + '?linkReunion=' + linkReunionVirtual, body);
    }

  finalizarVideollamadaID(idCuenta: number){
    const body = {}
    return this.httpClient.put(
    this.finalizarVideollamadaIDURL + '?idCuenta=' + idCuenta, body);
  }

  guardarLink(idReunion:string){
    this.linkReunion.next(idReunion);
  }

  getLink(){
    return this.linkReunion$;
  }

  guardarOtroUsuario(usuario:UsuarioCalificar){
    this.otroUsuario.next(usuario);
  }

  getOtroUsuario(){
    return this.otroUsuario$;
  }


  idiomaAprendiz(idCuenta: number){
    return this.httpClient.get(this.idiomaURL + '?idCuenta=' + idCuenta);
  }
  reunionOtroParticipante(idCuenta: number , url:string){
    return this.httpClient.get(environment.apiUrl+"reunionvirtual/obtenerUsuarioACalificar?idCuenta="+idCuenta+"&url="+url);
  }

  consultarDatos(idCuenta: number){
    return this.httpClient.get(environment.apiUrl + "cuenta/obtenerusuariochat?idCuenta="+ idCuenta);
  }

}