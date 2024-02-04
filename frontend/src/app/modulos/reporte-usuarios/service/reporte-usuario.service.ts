import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class ReporteUsuarioService {
  constructor(private httpClient: HttpClient) {}
  url: string = environment?.apiUrl;
  idCuenta = new BehaviorSubject<number>(null);
  idCuenta$ = this.idCuenta.asObservable();

  idMotivo = new BehaviorSubject<number>(null);
  idMotivo$ = this.idMotivo.asObservable();

  traerReportes() {
    const urlEndpoint = this.url + 'motivos/listarreportecomportamiento';
    return this.httpClient.get(urlEndpoint);
  }

  traerDetalleReporte(idCuenta: number, idMotivo: number) {
    const urlEndpoint =
      this.url +
      'motivos/detallemotivoreporte?idCuentaReportada=+' +
      idCuenta +
      '&idMotivo=' +
      idMotivo;
    return this.httpClient.get(urlEndpoint);
  }

  traerMotivosReporteUsuario() {
    const urlEndpoint =
      this.url + 'motivos/listarmotivosactivosConID?nombreTipoMotivo=plataforma';
    return this.httpClient.get(urlEndpoint);
  }

  buscadorUsuarioReportes(cadena: string) {
    const urlEndpoint =
      this.url + 'motivos/filtrarReportePorNombre?nombreUsuario=' + cadena;
    return this.httpClient.get(urlEndpoint);
  }

  filtrarPorMotivo(cadena: string) {
    const urlEndpoint = this.url + 'motivos/filtrarReportePorMotivo?idMotivo='+cadena;
    return this.httpClient.get(urlEndpoint);
  }

  guardaridCuenta(idCuenta: number) {
    this.idCuenta.next(idCuenta);
  }
  getidCuenta(): Observable<number> {
    return this.idCuenta$;
  }

  guardaridMotivo(idMotivo: number) {
    this.idMotivo.next(idMotivo);
  }
  getidMotivo(): Observable<number> {
    return this.idMotivo$;
  }
}
