import { Component } from '@angular/core';
import { SpinnerServiceGeneral } from 'src/app/compartidos/spinner-carga-general/spinner.service';

@Component({
  selector: 'app-chat-padre-abierto',
  templateUrl: './chat-padre-abierto.component.html',
  styleUrls: ['./chat-padre-abierto.component.scss']
})
export class ChatPadreAbiertoComponent {
  mostrarChatExitoso: boolean  = false;
  
  constructor(private spinner: SpinnerServiceGeneral){}
  ngOnInit(){
    this.spinner.hideSpinner();
  }

  abrirChatConId(event){
    this.mostrarChatExitoso = event
  }
}
