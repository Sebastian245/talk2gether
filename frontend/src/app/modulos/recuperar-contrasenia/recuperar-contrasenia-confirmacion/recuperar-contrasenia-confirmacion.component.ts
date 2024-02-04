import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-recuperar-contrasenia-confirmacion',
  templateUrl: './recuperar-contrasenia-confirmacion.component.html',
  styleUrls: ['./recuperar-contrasenia-confirmacion.component.scss']
})
export class RecuperarContraseniaConfirmacionComponent {
  
  @Input() receptorEmail: string;
  @Output() enviarEmail:  EventEmitter<string> = new EventEmitter();;

  reenviarEmail(){
    this.enviarEmail.emit(this.receptorEmail);
  }
  
}
