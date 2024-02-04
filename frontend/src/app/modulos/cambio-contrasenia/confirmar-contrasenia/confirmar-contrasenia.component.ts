import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-confirmar-contrasenia',
  templateUrl: './confirmar-contrasenia.component.html',
  styleUrls: ['./confirmar-contrasenia.component.scss']
})
export class ConfirmarContraseniaComponent {
  constructor(private router: Router) { }

  menuPrincipal(){
    this.router.navigate(['']);
  }
}
