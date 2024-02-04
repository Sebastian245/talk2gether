import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-sin-funcionar',
  templateUrl: './sin-funcionar.component.html',
  styleUrls: ['./sin-funcionar.component.scss']
})
export class SinFuncionarComponent {

  constructor(private router: Router) { }

  menuPrincipal(){
    this.router.navigate(['inicio']);
  }
}
