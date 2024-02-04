import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class MotivoService {

  
 constructor(private httpClient: HttpClient) { }
 url: string = environment?.apiUrl;

 id = new BehaviorSubject<number>(null);
 id$ = this.id.asObservable();

 nombreMotivo = new BehaviorSubject<string>(null);
 nombreMotivo$ = this.nombreMotivo.asObservable();

 traerMotivo() {
   const urlEndpoint = this.url + 'motivos/listarmotivos';
   return this.httpClient.get(urlEndpoint);
 }

 habilitarMotivo(id:number) {
   const urlEndpoint = this.url + 'motivos/habilitarmotivo/'+id;
   return this.httpClient.get(urlEndpoint);
 }
 desHabilitarMotivo(id:number) {
   const urlEndpoint = this.url + 'motivos/deshabilitarmotivo/'+id;
   return this.httpClient.get(urlEndpoint);
 }

 modificarMotivo(id:number, nombreMotivo:string ) {
   const urlEndpoint = this.url + 'motivos/actualizarmotivo/'+id;
   const body = {
       "nombreMotivo": nombreMotivo
   }
   return this.httpClient.put(urlEndpoint,body);
 }
 crearMotivo(nombreMotivo:string, tipoMotivo:string){
   const urlEndpoint = this.url + 'motivos/crearmotivo';
   const body ={
     "nombreMotivo":nombreMotivo,
     "nombreTipoMotivo":tipoMotivo
   }
   return this.httpClient.post(urlEndpoint,body);
 }

 guardarId(id:number){
   this.id.next(id);
 }
 getId(){
   return this.id$;
 }
 guardarMotivo(nombreMotivo:string){
   this.nombreMotivo.next(nombreMotivo);
 }
 getMotivo(){
   return this.nombreMotivo$;
 }
}
