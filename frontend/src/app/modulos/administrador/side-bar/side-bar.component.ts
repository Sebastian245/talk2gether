import { HttpClient } from '@angular/common/http';
import { Component, HostListener } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-side-bar',
  templateUrl: './side-bar.component.html',
  styleUrls: ['./side-bar.component.scss'],
})
export class SideBarComponent {
  isMenuOpenDashboard: boolean = false;

  isMenuOpenUsuarios: boolean = false;
  isMenuOpenReportes: boolean = false;
  isMenuOpenGamificacion: boolean = false;
  isMenuOpenParametros: boolean = false;
  isMenuOpenRecuperate: boolean = false;
  sidebarAbierto: boolean = false;
  redireccion: string;
  widthScreen: number;

  mostrarPais: boolean = false;
  mostrarInteres: boolean = false;
  mostrarIdioma: boolean = false;
  mostrarNivelIdioma: boolean = false;
  mostrarUsuario: boolean = false;
  mostrarCrearUsuario: boolean = false;
  mostrarMotivos: boolean = false;
  mostrarRoles: boolean = false;
  reportesUsuarios: boolean = false;
  mostrarReporteChats: boolean = false;
  mostrarPermisos: boolean = false;
  mostrarCalificaciones: boolean = false;
  mostrarPuntosPorActividad: boolean = false;
  mostrarBackUpDB: boolean = false;
  mostrarDashBoard: boolean = false;

  @HostListener('window:resize', ['$event'])
  onResize(event) {
    this.widthScreenOpenSidebar(event.target.innerWidth);
  }
  constructor(
    private route: Router,
    private router: ActivatedRoute,
    private http: HttpClient
  ) {}
  ngOnInit() {
    this.router.params.subscribe({
      next: (response) => {
        this.redireccion = response['redireccion'];
        this.seleccionarRuta();
      },
    });
    this.widthScreenOpenSidebar(window.screen.width);
  }

  widthScreenOpenSidebar(width: number) {
    if (width > 768) {
      this.abrirSidebar(true);
    } else {
      this.abrirSidebar(false);
    }
  }

  seleccionarRuta() {
    switch (this.redireccion) {
      case 'administrarPais':
        this.mostrarPais = true;
        this.mostrarInteres = false;
        this.mostrarIdioma = false;
        this.mostrarNivelIdioma = false;
        this.mostrarUsuario = false;
        this.mostrarCrearUsuario = false;
        this.mostrarMotivos = false;
        this.mostrarRoles = false;
        this.reportesUsuarios = false;
        this.mostrarReporteChats = false;
        this.mostrarDashBoard = false;
        this.mostrarPermisos = false;
        this.mostrarCalificaciones = false;
        this.mostrarPuntosPorActividad = false;
        this.mostrarBackUpDB = false;
        break;
      case 'administrarInteres':
        this.mostrarPais = false;
        this.mostrarInteres = true;
        this.mostrarIdioma = false;
        this.mostrarNivelIdioma = false;
        this.mostrarUsuario = false;
        this.mostrarCrearUsuario = false;
        this.mostrarMotivos = false;
        this.mostrarRoles = false;
        this.reportesUsuarios = false;
        this.mostrarReporteChats = false;
        this.mostrarDashBoard = false;
        this.mostrarPermisos = false;
        this.mostrarCalificaciones = false;
        this.mostrarPuntosPorActividad = false;
        this.mostrarBackUpDB = false;
        break;
      case 'administrarIdioma':
        this.mostrarPais = false;
        this.mostrarInteres = false;
        this.mostrarIdioma = true;
        this.mostrarNivelIdioma = false;
        this.mostrarUsuario = false;
        this.mostrarCrearUsuario = false;
        this.mostrarMotivos = false;
        this.mostrarRoles = false;
        this.reportesUsuarios = false;
        this.mostrarReporteChats = false;
        this.mostrarDashBoard = false;
        this.mostrarPermisos = false;
        this.mostrarCalificaciones = false;
        this.mostrarPuntosPorActividad = false;
        this.mostrarBackUpDB = false;
        break;
      case 'administrarNivelIdioma':
        this.mostrarPais = false;
        this.mostrarInteres = false;
        this.mostrarIdioma = false;
        this.mostrarNivelIdioma = true;
        this.mostrarUsuario = false;
        this.mostrarCrearUsuario = false;
        this.mostrarMotivos = false;
        this.mostrarRoles = false;
        this.reportesUsuarios = false;
        this.mostrarReporteChats = false;
        this.mostrarDashBoard = false;
        this.mostrarPermisos = false;
        this.mostrarCalificaciones = false;
        this.mostrarPuntosPorActividad = false;
        this.mostrarBackUpDB = false;
        break;
      case 'administrarUsuario':
        this.mostrarPais = false;
        this.mostrarInteres = false;
        this.mostrarIdioma = false;
        this.mostrarNivelIdioma = false;
        this.mostrarUsuario = true;
        this.mostrarCrearUsuario = false;
        this.mostrarMotivos = false;
        this.mostrarRoles = false;
        this.reportesUsuarios = false;
        this.mostrarReporteChats = false;
        this.mostrarDashBoard = false;
        this.mostrarPermisos = false;
        this.mostrarCalificaciones = false;
        this.mostrarPuntosPorActividad = false;
        this.mostrarBackUpDB = false;
        break;
      case 'recuperacion':
        this.mostrarPais = false;
        this.mostrarInteres = false;
        this.mostrarIdioma = false;
        this.mostrarNivelIdioma = false;
        this.mostrarUsuario = false;
        this.mostrarCrearUsuario = false;
        this.mostrarMotivos = false;
        this.mostrarRoles = false;
        this.reportesUsuarios = false;
        this.mostrarReporteChats = false;
        this.mostrarDashBoard = false;
        this.mostrarPermisos = false;
        this.mostrarCalificaciones = false;
        this.mostrarPuntosPorActividad = false;
        this.mostrarBackUpDB = true;
        break;
      case 'crearUsuario':
        this.mostrarPais = false;
        this.mostrarInteres = false;
        this.mostrarIdioma = false;
        this.mostrarNivelIdioma = false;
        this.mostrarUsuario = false;
        this.mostrarCrearUsuario = true;
        this.mostrarMotivos = false;
        this.mostrarRoles = false;
        this.reportesUsuarios = false;
        this.mostrarReporteChats = false;
        this.mostrarDashBoard = false;
        this.mostrarPermisos = false;
        this.mostrarCalificaciones = false;
        this.mostrarPuntosPorActividad = false;
        this.mostrarBackUpDB = false;
        break;
      case 'administrarMotivos':
        this.mostrarPais = false;
        this.mostrarInteres = false;
        this.mostrarIdioma = false;
        this.mostrarNivelIdioma = false;
        this.mostrarUsuario = false;
        this.mostrarCrearUsuario = false;
        this.mostrarMotivos = true;
        this.mostrarRoles = false;
        this.reportesUsuarios = false;
        this.mostrarReporteChats = false;
        this.mostrarDashBoard = false;
        this.mostrarPermisos = false;
        this.mostrarCalificaciones = false;
        this.mostrarPuntosPorActividad = false;
        this.mostrarBackUpDB = false;
        break;
      case 'administrarRoles':
        this.mostrarPais = false;
        this.mostrarInteres = false;
        this.mostrarIdioma = false;
        this.mostrarNivelIdioma = false;
        this.mostrarUsuario = false;
        this.mostrarCrearUsuario = false;
        this.mostrarMotivos = false;
        this.mostrarRoles = true;
        this.reportesUsuarios = false;
        this.mostrarReporteChats = false;
        this.mostrarDashBoard = false;
        this.mostrarPermisos = false;
        this.mostrarCalificaciones = false;
        this.mostrarPuntosPorActividad = false;
        this.mostrarBackUpDB = false;
        break;
      case 'reportesUsuarios':
        this.mostrarPais = false;
        this.mostrarInteres = false;
        this.mostrarIdioma = false;
        this.mostrarNivelIdioma = false;
        this.mostrarUsuario = false;
        this.mostrarCrearUsuario = false;
        this.mostrarMotivos = false;
        this.mostrarRoles = false;
        this.reportesUsuarios = true;
        this.mostrarReporteChats = false;
        this.mostrarDashBoard = false;
        this.mostrarPermisos = false;
        this.mostrarCalificaciones = false;
        this.mostrarPuntosPorActividad = false;
        this.mostrarBackUpDB = false;

        break;
      case 'reportesChatsUsuarios':
        this.mostrarPais = false;
        this.mostrarInteres = false;
        this.mostrarIdioma = false;
        this.mostrarNivelIdioma = false;
        this.mostrarUsuario = false;
        this.mostrarCrearUsuario = false;
        this.mostrarMotivos = false;
        this.mostrarRoles = false;
        this.reportesUsuarios = false;
        this.mostrarReporteChats = true;
        this.mostrarDashBoard = false;
        this.mostrarPermisos = false;
        this.mostrarCalificaciones = false;
        this.mostrarPuntosPorActividad = false;
        this.mostrarBackUpDB = false;
        break;
      case 'administrarDashboard':
        this.mostrarPais = false;
        this.mostrarInteres = false;
        this.mostrarIdioma = false;
        this.mostrarNivelIdioma = false;
        this.mostrarUsuario = false;
        this.mostrarCrearUsuario = false;
        this.mostrarMotivos = false;
        this.mostrarRoles = false;
        this.reportesUsuarios = false;
        this.mostrarReporteChats = false;
        this.mostrarDashBoard = true;
        this.mostrarPermisos = false;
        this.mostrarCalificaciones = false;
        this.mostrarPuntosPorActividad = false;
        this.mostrarBackUpDB = false;
        break;
      case 'administrarPermisos':
        this.mostrarPais = false;
        this.mostrarInteres = false;
        this.mostrarIdioma = false;
        this.mostrarNivelIdioma = false;
        this.mostrarUsuario = false;
        this.mostrarCrearUsuario = false;
        this.mostrarMotivos = false;
        this.mostrarRoles = false;
        this.reportesUsuarios = false;
        this.mostrarReporteChats = false;
        this.mostrarPermisos = true;
        this.mostrarCalificaciones = false;
        this.mostrarPuntosPorActividad = false;
        this.mostrarBackUpDB = false;
        break;
      case 'visualizarCalificacion':
        this.mostrarPais = false;
        this.mostrarInteres = false;
        this.mostrarIdioma = false;
        this.mostrarNivelIdioma = false;
        this.mostrarUsuario = false;
        this.mostrarCrearUsuario = false;
        this.mostrarMotivos = false;
        this.mostrarRoles = false;
        this.reportesUsuarios = false;
        this.mostrarReporteChats = false;
        this.mostrarPermisos = false;
        this.mostrarCalificaciones = true;
        this.mostrarPuntosPorActividad = false;
        this.mostrarBackUpDB = false;
        break;
      case 'puntosPorActividad':
        this.mostrarPais = false;
        this.mostrarInteres = false;
        this.mostrarIdioma = false;
        this.mostrarNivelIdioma = false;
        this.mostrarUsuario = false;
        this.mostrarCrearUsuario = false;
        this.mostrarMotivos = false;
        this.mostrarRoles = false;
        this.reportesUsuarios = false;
        this.mostrarReporteChats = false;
        this.mostrarPermisos = false;
        this.mostrarCalificaciones = false;
        this.mostrarPuntosPorActividad = true;
        this.mostrarBackUpDB = false;
        break;
      case 'reportesUsuarios':
        this.mostrarPais = false;
        this.mostrarInteres = false;
        this.mostrarIdioma = false;
        this.mostrarNivelIdioma = false;
        this.mostrarUsuario = false;
        this.mostrarCrearUsuario = false;
        this.mostrarMotivos = false;
        this.mostrarRoles = false;
        this.reportesUsuarios = true;
        this.mostrarReporteChats = false;
        this.mostrarPermisos = false;
        this.mostrarCalificaciones = false;
        this.mostrarPuntosPorActividad = false;
        break;
      default:
        this.route.navigate['administrador'];
        break;
    }
  }

  dirigirDashboard() {
    const url = `estadisticasPlataforma`;
    window.open(url, '_blank');
  }

  descargarManual() {
    const pdfUrl =
      'https://firebasestorage.googleapis.com/v0/b/talk2gether-a8b61.appspot.com/o/manuales-de-usuario%2FManual%20del%20Administrador%20-%20Talk2gether%20-%20PDF.pdf?alt=media&token=909564c8-276a-4bf5-a97c-0e7ede6f54f1&_gl=1*dymthx*_ga*MTczMzczOTM5NS4xNjkwNjcxMjcx*_ga_CW55HF8NVT*MTY5Njc5NTUxMC4xMC4xLjE2OTY3OTYzMzEuNDMuMC4w'; 

    this.http
      .get(pdfUrl, { responseType: 'arraybuffer' })
      .subscribe((data: ArrayBuffer) => {
        const blob = new Blob([data], { type: 'application/pdf' });

        const link = document.createElement('a');
        link.href = window.URL.createObjectURL(blob);
        link.download = 'Manual_Administrador_Talk2gether.pdf';
        link.click();
      });
  }

  dirigirAdministrarPais() {
    this.route.navigate(['administrador', 'administrarPais']);
  }
  dirigirAdministrarIdiomas() {
    this.route.navigate(['administrador', 'administrarIdioma']);
  }

  dirigirAdministrarInteres() {
    this.route.navigate(['administrador', 'administrarInteres']);
  }

  dirigirAdministrarNivelIdioma() {
    this.route.navigate(['administrador', 'administrarNivelIdioma']);
  }

  dirigirBackUp() {
    this.route.navigate(['administrador', 'recuperacion']);
  }

  dirigirAdministrarUsuarios() {
    this.route.navigate(['administrador', 'administrarUsuario']);
  }

  dirigirAdministrarMotivos() {
    this.route.navigate(['administrador', 'administrarMotivos']);
  }
  dirigirAdministrarRoles() {
    this.route.navigate(['administrador', 'administrarRoles']);
  }

  dirigirAdministrarReporteUsuarios() {
    this.route.navigate(['administrador', 'reportesUsuarios']);
  }

  dirigirAdministrarPuntosPorActividad() {
    this.route.navigate(['administrador', 'puntosPorActividad']);
  }

  abrirSidebar(isActive: boolean) {
    var sidebar = document.getElementById('sidebar');
    var content = document.getElementById('content');
    this.sidebarAbierto = !this.sidebarAbierto;
    if (isActive) {
      sidebar.classList.add('active');
      content.classList.add('margin-sidebar');
    } else {
      sidebar.classList.remove('active');
      content.classList.remove('margin-sidebar');
    }
  }
  inicio() {
    this.route.navigate(['inicio']);
  }
}
