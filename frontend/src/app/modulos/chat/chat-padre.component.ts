import { Component } from '@angular/core';
import { SpinnerServiceGeneral } from 'src/app/compartidos/spinner-carga-general/spinner.service';

@Component({
  selector: 'app-chat-padre',
  templateUrl: './chat-padre.component.html',
  styleUrls: ['./chat-padre.component.scss']
})
export class ChatPadreComponent {

  mostrarMsjExitoso: boolean  = false;
  
  constructor(private spinner: SpinnerServiceGeneral){}
  ngOnInit(){
    this.spinner.hideSpinner();
  }

  abrirChat(event){
    this.mostrarMsjExitoso = event
  }
}
