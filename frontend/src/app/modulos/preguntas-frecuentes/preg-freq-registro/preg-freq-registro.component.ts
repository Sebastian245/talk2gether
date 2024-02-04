import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-preg-freq-registro',
  templateUrl: './preg-freq-registro.component.html',
  styleUrls: ['./preg-freq-registro.component.scss']
})
export class PregFreqRegistroComponent {
  constructor(private router:Router){

  }

  redirigirRegistro(){
    const url = `/registro`;
    window.open(url, '_blank');
  }
}
