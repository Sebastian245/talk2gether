import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { ChatReport } from '../models/chatReport';
import { HttpBackend, HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class ReporteUsuarioChatService {
  id = new BehaviorSubject<number>(null);
  id$ = this.id.asObservable();

  id2 = new BehaviorSubject<number>(null);
  id2$ = this.id2.asObservable();

  chatArmados = new BehaviorSubject<ChatReport[]>(null);
  chatArmados$ = this.chatArmados.asObservable();

  mostrar = new BehaviorSubject<boolean>(null);
  mostrar$ = this.mostrar.asObservable();

  urlFirebaseBackUp: string = environment?.firebaseApi;

  private httpClient: HttpClient;

  constructor(handler: HttpBackend) {
    this.httpClient = new HttpClient(handler);
  }
  
  traerJsonBackUp() {
    return this.httpClient.get(
      this.urlFirebaseBackUp +
        'back-up-chats%2F20230730183238023_back-upchats.json?alt=media&token=b5c8d28f-9ba9-45b4-b965-8286a27b9b42'
    );
  }



  guardarChats(chats: ChatReport[]) {
    this.chatArmados.next(chats);
  }

  eliminarChats(chatsAEliminar: ChatReport[]): void {
    const chatsActuales = this.chatArmados.getValue();
    const chatsRestantes = chatsActuales.filter(
      (chat) => !chatsAEliminar.includes(chat)
    );
    this.chatArmados.next(chatsRestantes);
  }
  getChats() {
    return this.chatArmados$;
  }

  guardarId(id: number) {
    this.id.next(id);
  }
  getId() {
    return this.id$;
  }
  guardarId2(id2: number) {
    this.id2.next(id2);
  }
  getId2() {
    return this.id2$;
  }
}
