import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ReportarUsuarioService {

  listarMotivosReporteURL: string = environment?.apiUrl + 'motivos/listarmotivosactivos?nombreTipoMotivo=plataforma';
  reportarUsuarioURL: string = environment?.apiUrl + 'cuenta/reportarusuario';

  constructor(
    private httpClient: HttpClient
  ) {}

  listarMotivosReporte() {
    return this.httpClient.get<any>(this.listarMotivosReporteURL);
  }

  reportarUsuario(reporte: any){
    const body = reporte;
    return this.httpClient.post(this.reportarUsuarioURL, body, {responseType:'text'});
  }

}
