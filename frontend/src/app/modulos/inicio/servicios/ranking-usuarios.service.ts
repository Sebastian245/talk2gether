import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class RankingUsuariosService {


  rankingURL = environment.apiUrl + 'gamificacion/tablaranking';

  constructor(private httpClient: HttpClient) { }

  buscarUsuarios(idCuenta: number){
    return this.httpClient.get<any>(this.rankingURL + '?idCuenta=' + idCuenta +
    '&cantidadFilas=25');
  }
}