import { Component, ViewEncapsulation } from '@angular/core';
import { ModalService } from '../../registro/servicios/modal.service';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import Swal from 'sweetalert2';
import { SpinnerServiceGeneral } from 'src/app/compartidos/spinner-carga-general/spinner.service';
import { UsuariosBloqueadosService } from '../servicios/usuarios-bloqueados.service';
import { NavbarService } from 'src/app/compartidos/navbar/service/navbar.service';

@Component({
  selector: 'app-usuarios-bloqueados-modal',
  templateUrl: './usuarios-bloqueados-modal.component.html',
  styleUrls: ['./usuarios-bloqueados-modal.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class UsuariosBloqueadosModalComponent {
  cargando: boolean = true;
  idUsuario: number;
  bloqueados$: Subscription;
  listaBloqueados = [];
  flag: boolean = false;

  constructor(
    private router: Router,
    private modalService: ModalService,
    private navbarService: NavbarService,
    private usuariosBloqueadosService: UsuariosBloqueadosService,
    private route: Router,
    private spinner: SpinnerServiceGeneral
  ) {}

  ngOnInit() {
    this.spinner.showSpinner();
    this.navbarService.traerUsuario().subscribe({
      next: response => {
        this.idUsuario = response['id'];
        this.mostrarBloqueados();
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
    });
  }

  visitarPerfil(id) {
    this.router.navigate(['perfilUsuario', id]);
    this.dismissModal();
  }

  mostrarBloqueados() {
    this.cargando = true;
    this.bloqueados$ = this.usuariosBloqueadosService.listaUsuariosBloqueados(this.idUsuario).subscribe({
      next: (response: any[]) => {
        this.listaBloqueados = response;
        this.cargando = false;
        this.flag = true;
        this.spinner.hideSpinner();
      },
      error: (error) => {
        Swal.fire({
          icon: 'error',
          title: 'Oops...',
          text: 'Algo salió mal! Intente nuevamente mas tarde.',
        });
        this.route.navigate(['']);
        this.cargando = false;
      },
    });
  }

  dismissModal() {
    this.modalService.dismissActiveModal();
  }

}
