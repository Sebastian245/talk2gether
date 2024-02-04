import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-preg-freq-introduccion',
  templateUrl: './preg-freq-introduccion.component.html',
  styleUrls: ['./preg-freq-introduccion.component.scss']
})
export class PregFreqIntroduccionComponent {
  constructor(private router:Router){

  }

  redirigirRegistro(){
    this.router.navigate(['registro'])
  }
}
