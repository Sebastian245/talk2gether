import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-configuracion-padre',
  templateUrl: './configuracion-padre.component.html',
  styleUrls: ['./configuracion-padre.component.scss']
})
export class ConfiguracionPadreComponent {

  pantallaEditarUsuario: boolean = false;

  constructor(
    private router: Router
    ) {}

    pasarPantallaEditarUsuario(){
      this.pantallaEditarUsuario = true;
    }

    volverPantalla(){
      this.pantallaEditarUsuario = false;
      this.reloadPage();
    }

    reloadPage() {
      setTimeout(() => {
        window.location.reload();
      }, 2000);
    }



}
