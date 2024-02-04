import { Component, Input } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ChatService } from '../../chat/services/chat-service.service';
import { ReporteUsuarioChatService } from '../services/reporte-usuario-chat.service';
import { ChatReport } from '../models/chatReport';
import { Observable, combineLatest, of, switchMap } from 'rxjs';

@Component({
  selector: 'app-reporte-chats-visualizar',
  templateUrl: './reporte-chats-visualizar.component.html',
  styleUrls: ['./reporte-chats-visualizar.component.scss'],
})
export class ReporteChatsVisualizarComponent {
  id1: number;
  id2: number;
  chats: ChatReport[] = [];
  mostrar: boolean;
  isChat$: Observable<any>;
  @Input() receptorChat: ChatReport[];
  constructor(
    private route: Router,
    private router: ActivatedRoute,
    private serviceChat: ChatService,
    private reporteChat: ReporteUsuarioChatService
  ) {}

  ngOnInit() {
    this.isChat$ = combineLatest([
      this.reporteChat.getId(),
      this.reporteChat.getId2(),
      this.reporteChat.getChats()
    ]).pipe(
      switchMap(([id1, id2]) => {
        this.id1 = id1;
        this.id2 = id2;
        this.chats =this.receptorChat
        const chatsFiltrados = this.chats.filter(
          (chat) =>
            (chat.entities.receiver.entity.uid === this.id1.toString() &&
            chat.entities.sender.entity.uid === this.id2.toString()) ||
            (chat.entities.receiver.entity.uid === this.id2.toString() &&
            chat.entities.sender.entity.uid === this.id1.toString()
            )
        );
        //aca tendria que traer las fechas y hacer una validacion por si no lo escribe
        //Y volver a filtrar los chats
        this.chats = chatsFiltrados;
        const isChat = chatsFiltrados.length > 0;
        return of(isChat);
      }
    )
    )

  }

  formatTime(timestamp: number): string {
    const timestampMilisegundos = timestamp * 1000;
  
    // Creamos el objeto Date con el timestamp en milisegundos
    const fecha = new Date(timestampMilisegundos);
  
    // Obtenemos los componentes de la fecha
    const dia = fecha.getDate();
    const mes = fecha.getMonth() + 1; // Nota: los meses en Date son indexados desde 0 (enero) a 11 (diciembre)
    const anio = fecha.getFullYear();
  
    // Componemos el string de la fecha en el formato deseado (ejemplo: dd/mm/yyyy)
    const fechaFormateada = `${dia.toString().padStart(2, '0')}/${mes.toString().padStart(2, '0')}/${anio}`;
  
    return fechaFormateada;
  }

}
