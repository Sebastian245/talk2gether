import { Component, ViewEncapsulation } from '@angular/core';
import { Router } from '@angular/router';
import { SpinnerServiceGeneral } from 'src/app/compartidos/spinner-carga-general/spinner.service';

@Component({
  selector: 'app-principal-chat',
  templateUrl: './principal-chat.component.html',
  styleUrls: ['./principal-chat.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class PrincipalChatComponent {
   constructor(private sppiner:SpinnerServiceGeneral){}
   ngOnInit(){

     this.sppiner.hideSpinner();
    }

}
