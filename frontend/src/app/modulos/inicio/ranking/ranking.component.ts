import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { RankingUsuariosService } from '../servicios/ranking-usuarios.service';
import { NavbarService } from 'src/app/compartidos/navbar/service/navbar.service';
import { SpinnerServiceGeneral } from 'src/app/compartidos/spinner-carga-general/spinner.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-ranking',
  templateUrl: './ranking.component.html',
  styleUrls: ['./ranking.component.scss']
})
export class RankingComponent {

  constructor(
    private router: Router,
    private navbarService: NavbarService,
    private rankingService: RankingUsuariosService,
    private spinner: SpinnerServiceGeneral
    ) {}

  idUsuario: number;
  rankingCargado = false;
  usuariosRanking = [];

  ngOnInit(){
    this.spinner.showSpinner();
    this.navbarService.traerUsuario().subscribe(
      {
        next: response => {
          this.idUsuario = response['id']
          this.rankingService.buscarUsuarios(this.idUsuario).subscribe({
            next: response =>{
              this.spinner.hideSpinner();
              this.rankingCargado = true;
              this.usuariosRanking = response;
            },
            error: error =>{

            }
          })
        },
        error: error => {
          this.spinner.hideSpinner();
          localStorage.clear();
          sessionStorage.clear();
          localStorage.setItem('isLoggedIn', 'false');
          Swal.fire(
            'La sesión fue cerrada',
            'Inicie nuevamente para continuar en la plataforma',
            'question'
          )
          this.router.navigate([''])
        }
      }
    );
  }

  volver(){
    this.router.navigate(['inicio']);
  }
}