import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { COMETCHAT_CONSTANTS } from 'src/CONSTS';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class ChatService {
  urlApi: string = environment?.apiUrl;
  urlApiChat: string =
    'https://' +
    COMETCHAT_CONSTANTS.APP_ID +
    '.api-' +
    COMETCHAT_CONSTANTS.REGION +
    '.cometchat.io/v3/users';
  onLoginError: boolean = false;
  errorMsg: string = '';

  constructor(
    private httpClient: HttpClient
  ) {}

  registroChat(uid:string, name:string, avatar:string) {
    const options = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        apiKey: COMETCHAT_CONSTANTS.API_KEY,
      }),
    };
    const body = {
      "uid": uid,
      "name": name,
      "avatar":avatar
    };
    return this.httpClient.post(this.urlApiChat, body, options);
  }

  editarUsuario(uid:string, name:string, avatar:string) {
    const options = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        apiKey: COMETCHAT_CONSTANTS.API_KEY,
      }),
    };
    const body = {
      "name": name,
      "avatar":avatar
    };
    return this.httpClient.put(this.urlApiChat + '/' +uid, body, options);
  }

  consultarDatos(uid:string){
    const url = this.urlApi+"cuenta/obtenerusuariochat?idCuenta="+uid
    return this.httpClient.get(url);

  }

  eliminarUsuario(uid: number) {
    const id = uid.toString()
    const options = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        apiKey: COMETCHAT_CONSTANTS.API_KEY,
      }),
      body: {
        "permanent": true
      }
    };
    const url = this.urlApiChat +"/"+ id;
    return this.httpClient.delete(url, options)
  }

  consultarUsuario(id:string){
    const options = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        apiKey: COMETCHAT_CONSTANTS.API_KEY,
      }),
    };
    const urlConsultarUsuario = this.urlApiChat+"/"+id
    return this.httpClient.get(urlConsultarUsuario,options);
  }

  consultarListaUsuario(){
    const options = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        apiKey: COMETCHAT_CONSTANTS.API_KEY,
      }),
    };
    return this.httpClient.get(this.urlApiChat,options);
  }
  consultarMensajes(uid:string){
    const options = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        apiKey: COMETCHAT_CONSTANTS.API_KEY,
      }),
    };
    const urlConsultarUsuario = this.urlApiChat+"/"+uid+"/messages?unread=true"
    return this.httpClient.get(urlConsultarUsuario,options);
  }

  consultarBloqueados(uid:string){
    const options = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        apiKey: COMETCHAT_CONSTANTS.API_KEY,
      }),
    };
    const urlConsultarUsuario = this.urlApiChat+"/"+uid+"/blockedusers"
    return this.httpClient.get(urlConsultarUsuario,options);
  }

  traerChats(){
    const urlApiChat: string =
    'https://' +
    COMETCHAT_CONSTANTS.APP_ID +
    '.api-' +
    COMETCHAT_CONSTANTS.REGION +
    '.cometchat.io/v3/messages'
    const options = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        apiKey: COMETCHAT_CONSTANTS.API_KEY,
      }),
    };
    return this.httpClient.get(urlApiChat,options);
  }
}

