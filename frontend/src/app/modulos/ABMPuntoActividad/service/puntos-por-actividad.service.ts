import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class PuntosPorActividadService {
  constructor(private httpClient: HttpClient) {}
  url: string = environment?.apiUrl;

  id = new BehaviorSubject<number>(null);
  id$ = this.id.asObservable();

  nombrePuntosPorActividad = new BehaviorSubject<string>(null);
  nombrePuntosPorActividad$ = this.nombrePuntosPorActividad.asObservable();

  puntosPorActividad = new BehaviorSubject<number>(null);
  puntosPorActividad$ = this.puntosPorActividad.asObservable();

  descripcion = new BehaviorSubject<string>(null);
  descripcion$ = this.descripcion.asObservable();

  puntosMaximos = new BehaviorSubject<number>(null);
  puntosMaximos$ = this.puntosMaximos.asObservable();

  tipoActividad = new BehaviorSubject<string>(null);
  tipoActividad$ = this.tipoActividad.asObservable();

  traerPuntosPorActividad() {
    const urlEndpoint =
      this.url + 'puntosporactividad/listarpuntosporactividadactivos';
    return this.httpClient.get(urlEndpoint);
  }

  modificarPuntos(id: number, puntos: number, nombrePuntosPorActividad: string, valorMaximo:number, descripcion: string) {
    const urlEndpoint =
      this.url + 'puntosporactividad/actualizarpuntosporactividad/' + id;
    const body = {
      puntosPorActividad: puntos,
      nombrePuntosPorActividad: nombrePuntosPorActividad,
      valorMaximo: valorMaximo,
      descripcion: descripcion,
    };
    return this.httpClient.put(urlEndpoint, body);
  }

  guardarId(id: number) {
    this.id.next(id);
  }
  getId() {
    return this.id$;
  }
  guardarnombrePuntosPorActividad(nombrePuntosPorActividad: string) {
    this.nombrePuntosPorActividad.next(nombrePuntosPorActividad);
  }
  getnombrePuntosPorActividad() {
    return this.nombrePuntosPorActividad$;
  }
  guardarpuntosPorActividad(puntosPorActividad: number) {
    this.puntosPorActividad.next(puntosPorActividad);
  }
  getpuntosPorActividad() {
    return this.puntosPorActividad$;
  }

  guardardescripcion(descripcion: string) {
    this.descripcion.next(descripcion);
  }
  getdescripcion() {
    return this.descripcion$;
  }

  guardarpuntosMaximos(puntosMaximos: number) {
    this.puntosMaximos.next(puntosMaximos);
  }
  getpuntosMaximos() {
    return this.puntosMaximos$;
  }

  guardarTipoActividad(tipoActividad: string) {
    this.tipoActividad.next(tipoActividad);
  }
  getTipoActividad() {
    return this.tipoActividad$;
  }


}
