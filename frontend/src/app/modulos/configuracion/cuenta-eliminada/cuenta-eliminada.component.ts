import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-cuenta-eliminada',
  templateUrl: './cuenta-eliminada.component.html',
  styleUrls: ['./cuenta-eliminada.component.scss']
})
export class CuentaEliminadaComponent {

  constructor(
    private router: Router
    ){}

  irPaginaBienvenida(){
    this.router.navigate(['']);
  }
}
