import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class DashboardAdminService {
  constructor(private httpClient: HttpClient) { }
  url: string = environment?.apiUrl;


  traerEstadisitcas() {
    const urlEndpoint = this.url + 'administrador/obtenerestadisticas';
    return this.httpClient.get(urlEndpoint);
  }
  traerEstadisticasPorFecha(fechaDesde:string, fechaHasta: string){
    const urlEndpoint = this.url + 'administrador/obtenerestadisticasGranuladas?fechaDesde='+fechaDesde+'&fechaHasta='+fechaHasta;
    return this.httpClient.get(urlEndpoint);
  }
}
