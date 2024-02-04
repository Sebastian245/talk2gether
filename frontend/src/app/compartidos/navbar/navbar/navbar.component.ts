import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { SesionService } from 'src/app/interceptors/sesion.service';
import { LoginService } from 'src/app/modulos/login/servicios/login.service';
import { NavbarService } from '../service/navbar.service';
import { PopupsInicioService } from 'src/app/modulos/inicio/servicios/popups-inicio.service';
import Swal from 'sweetalert2';
import { SpinnerServiceGeneral } from '../../spinner-carga-general/spinner.service';
import { ChatService } from 'src/app/modulos/chat/services/chat-service.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
})
export class NavbarComponent implements OnInit {
  currentUrl: string;
  localSesion: string;
  urlFoto: string;
  id: string;
  numeroChats: number;
  rol: string;
  username: string;
  linkReferido: string;
  rolNav: string;

  constructor(
    private router: Router,
    private cdr: ChangeDetectorRef,
    private loginservice: LoginService,
    private sesion: SesionService,
    private navService: NavbarService,
    private popUpInicio: PopupsInicioService,
    private spinnerServiceGeneral: SpinnerServiceGeneral,
    private chatService: ChatService
  ) { }

  ngOnInit() {
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        this.currentUrl = event.url.split('?')[0];
        this.traerRol();
        this.fotoPerfil();
      }
    });
  }

  traerRol() {
    this.loginservice.getRol().subscribe({
      next: (response) => {
        this.rolNav = response;
      },
    });
  }
  fotoPerfil() {
    this.localSesion = this.sesion.getCurrentSesion();

    if (this.localSesion != null) {
      this.navService.traerUsuario().subscribe({
        next: (response) => {
          this.rol = response['rol']['nombreRol'];
          this.urlFoto = response['urlFoto'];
          this.id = response['id'];
          this.username = response['username'];
          this.navService.guardarIdLogueado(this.id);
          this.chatService.consultarMensajes(this.id).subscribe({
            next: (response: any[]) => {
              this.numeroChats = response['data'].length;
            },
            error: (error) => {
              console.log('.');
            },
          });
        },
        error: (error) => {
          localStorage.clear();
          sessionStorage.clear();
          localStorage.setItem('isLoggedIn', 'false');
          Swal.fire(
            'La sesión fue cerrada',
            'Inicie nuevamente para continuar en la plataforma',
            'question'
          );
          this.router.navigate(['']);
        },
      });
    }
  }

  redireccionMenuPrincipal() {
    if (this.sesion.isLoggedIn()) {
      this.router.navigate(['inicio']);
    } else {
      this.router.navigate(['']);
    }
  }
  redireccionLogin() {
    document.location.href = 'login';
  }

  popUpInformacion() {
    const url = `/ayuda/introduccion`;
    window.open(url, '_blank');
  }

  referirAmigos() {
    this.spinnerServiceGeneral.showSpinner();
    this.popUpInicio.obtenerLinkReferido(parseInt(this.id)).subscribe({
      next: (response) => {
        this.linkReferido = response.mensaje;
        this.spinnerServiceGeneral.hideSpinner();
        this.popUpInicio.referirAmigos(this.linkReferido);
      },
      error: (error) => { },
    });
  }

  redireccionRanking() {
    this.router.navigate(['ranking']);
  }

  redireccionChat() {
    this.router.navigate(['chat']);
  }

  redireccionRegistrate() {
    this.router.navigate(['registro']);
    sessionStorage.setItem('previousUrl', this.currentUrl);
  }

  redireccionMiPerfil() {
    this.localSesion = this.sesion.getCurrentSesion();
    if (this.localSesion != null) {
      this.spinnerServiceGeneral.showSpinner();
      this.navService.traerUsuario().subscribe({
        next: (response) => {
          this.id = response['id'];
          this.router.navigate(['perfilUsuario', this.id]);
          this.spinnerServiceGeneral.hideSpinner();
        },
        error: (error) => {
          this.spinnerServiceGeneral.hideSpinner();
          localStorage.clear();
          sessionStorage.clear();
          localStorage.setItem('isLoggedIn', 'false');
          Swal.fire(
            'La sesión fue cerrada',
            'Inicie nuevamente para continuar en la plataforma',
            'question'
          );
          this.router.navigate(['']);
        },
      });
    }
  }

  redireccionConfiguracion() {
    this.router.navigate(['configuracion']);
  }

  redireccionCerrarSesion() {
    this.loginservice.logout();
    document.location.href = 'login';
  }
  redireccionAdministrador() {
    this.router.navigate(['administrador']);
  }
}
