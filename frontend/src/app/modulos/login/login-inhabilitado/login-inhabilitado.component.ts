import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from '../servicios/login.service';

@Component({
  selector: 'app-login-inhabilitado',
  templateUrl: './login-inhabilitado.component.html',
  styleUrls: ['./login-inhabilitado.component.scss']
})
export class LoginInhabilitadoComponent implements OnInit {
  constructor(private router: Router, private loginService : LoginService) { }
  
  motivosReporte: string[]
  ngOnInit(): void {
      this.loginService.getMotivo().subscribe(
        {
          next :(response) => (
            this.motivosReporte = response
          ),
          error:(error) =>(
            this.motivosReporte = []
          ),
        }
      )
  }
  menuPrincipal(){
    this.router.navigate(['']);
  }
}
