import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-side-bar-ayuda',
  templateUrl: './side-bar-ayuda.component.html',
  styleUrls: ['./side-bar-ayuda.component.scss'],
})
export class SideBarAyudaComponent {
  seccion: string;

  mostrarAyudaIntroduccion: boolean = false;
  mostrarAyudaRegistro: boolean = false;
  mostrarAyudaVerificarCuenta: boolean = false;
  mostrarAyudaCambiarContrasenia: boolean = false;
  mostrarAyudaReferirAmigos: boolean = false;
  mostrarAyudaRecuperarContrasenia: boolean = false;
  mostrarAyudaBusquedaAprendiz: boolean = false;
  mostrarAyudaCrearVideollamada: boolean = false;
  mostrarAyudaUnirseVideollamada: boolean = false;
  mostrarAyudaReportarUsuario: boolean = false;
  mostrarAyudaBloquearUsuario: boolean = false;
  mostrarAyudaChat: boolean = false;
  mostrarAyudaPerfilUsuario: boolean = false;
  mostrarAyudaRanking: boolean = false;
  mostrarAyudaEditarPerfil: boolean = false;
  mostrarAyudaEliminarCuenta: boolean = false;

  constructor(
    private route: Router,
    private router: ActivatedRoute,
    private http: HttpClient
  ) { }

  ngOnInit() {
    this.router.params.subscribe({
      next: (response) => {
        this.seccion = response['seccion'];
        this.seleccionarRuta();
      },
    });
  }

  seleccionarRuta() {
    switch (this.seccion) {
      case 'introduccion':
        this.mostrarAyudaIntroduccion = true;
        this.mostrarAyudaRegistro = false;
        this.mostrarAyudaVerificarCuenta = false;
        this.mostrarAyudaCambiarContrasenia = false;
        this.mostrarAyudaReferirAmigos = false;
        this.mostrarAyudaRecuperarContrasenia = false;
        this.mostrarAyudaBusquedaAprendiz = false;
        this.mostrarAyudaCrearVideollamada = false;
        this.mostrarAyudaUnirseVideollamada = false;
        this.mostrarAyudaReportarUsuario = false;
        this.mostrarAyudaBloquearUsuario = false;
        this.mostrarAyudaChat = false;
        this.mostrarAyudaPerfilUsuario = false;
        this.mostrarAyudaRanking = false;
        this.mostrarAyudaEditarPerfil = false;
        this.mostrarAyudaEliminarCuenta = false;
        break;
      case 'registro':
        this.mostrarAyudaIntroduccion = false;
        this.mostrarAyudaRegistro = true;
        this.mostrarAyudaVerificarCuenta = false;
        this.mostrarAyudaCambiarContrasenia = false;
        this.mostrarAyudaReferirAmigos = false;
        this.mostrarAyudaRecuperarContrasenia = false;
        this.mostrarAyudaBusquedaAprendiz = false;
        this.mostrarAyudaCrearVideollamada = false;
        this.mostrarAyudaUnirseVideollamada = false;
        this.mostrarAyudaReportarUsuario = false;
        this.mostrarAyudaBloquearUsuario = false;
        this.mostrarAyudaChat = false;
        this.mostrarAyudaPerfilUsuario = false;
        this.mostrarAyudaRanking = false;
        this.mostrarAyudaEditarPerfil = false;
        this.mostrarAyudaEliminarCuenta = false;
        break;
      case 'verificarCuenta':
        this.mostrarAyudaIntroduccion = false;
        this.mostrarAyudaRegistro = false;
        this.mostrarAyudaVerificarCuenta = true;
        this.mostrarAyudaCambiarContrasenia = false;
        this.mostrarAyudaReferirAmigos = false;
        this.mostrarAyudaRecuperarContrasenia = false;
        this.mostrarAyudaBusquedaAprendiz = false;
        this.mostrarAyudaCrearVideollamada = false;
        this.mostrarAyudaUnirseVideollamada = false;
        this.mostrarAyudaReportarUsuario = false;
        this.mostrarAyudaBloquearUsuario = false;
        this.mostrarAyudaChat = false;
        this.mostrarAyudaPerfilUsuario = false;
        this.mostrarAyudaRanking = false;
        this.mostrarAyudaEditarPerfil = false;
        this.mostrarAyudaEliminarCuenta = false;
        break;
      case 'cambiarContrasenia':
        this.mostrarAyudaIntroduccion = false;
        this.mostrarAyudaRegistro = false;
        this.mostrarAyudaVerificarCuenta = false;
        this.mostrarAyudaCambiarContrasenia = true;
        this.mostrarAyudaReferirAmigos = false;
        this.mostrarAyudaRecuperarContrasenia = false;
        this.mostrarAyudaBusquedaAprendiz = false;
        this.mostrarAyudaCrearVideollamada = false;
        this.mostrarAyudaUnirseVideollamada = false;
        this.mostrarAyudaReportarUsuario = false;
        this.mostrarAyudaBloquearUsuario = false;
        this.mostrarAyudaChat = false;
        this.mostrarAyudaPerfilUsuario = false;
        this.mostrarAyudaRanking = false;
        this.mostrarAyudaEditarPerfil = false;
        this.mostrarAyudaEliminarCuenta = false;
        break;
      case 'referirAmigo':
        this.mostrarAyudaIntroduccion = false;
        this.mostrarAyudaRegistro = false;
        this.mostrarAyudaVerificarCuenta = false;
        this.mostrarAyudaCambiarContrasenia = false;
        this.mostrarAyudaReferirAmigos = true;
        this.mostrarAyudaRecuperarContrasenia = false;
        this.mostrarAyudaBusquedaAprendiz = false;
        this.mostrarAyudaCrearVideollamada = false;
        this.mostrarAyudaUnirseVideollamada = false;
        this.mostrarAyudaReportarUsuario = false;
        this.mostrarAyudaBloquearUsuario = false;
        this.mostrarAyudaChat = false;
        this.mostrarAyudaPerfilUsuario = false;
        this.mostrarAyudaRanking = false;
        this.mostrarAyudaEditarPerfil = false;
        this.mostrarAyudaEliminarCuenta = false;
        break;
      case 'recuperarContrasenia':
        this.mostrarAyudaIntroduccion = false;
        this.mostrarAyudaRegistro = false;
        this.mostrarAyudaVerificarCuenta = false;
        this.mostrarAyudaCambiarContrasenia = false;
        this.mostrarAyudaReferirAmigos = false;
        this.mostrarAyudaRecuperarContrasenia = true;
        this.mostrarAyudaBusquedaAprendiz = false;
        this.mostrarAyudaCrearVideollamada = false;
        this.mostrarAyudaUnirseVideollamada = false;
        this.mostrarAyudaReportarUsuario = false;
        this.mostrarAyudaBloquearUsuario = false;
        this.mostrarAyudaChat = false;
        this.mostrarAyudaPerfilUsuario = false;
        this.mostrarAyudaRanking = false;
        this.mostrarAyudaEditarPerfil = false;
        this.mostrarAyudaEliminarCuenta = false;
        break;
      case 'buscarAprendiz':
        this.mostrarAyudaIntroduccion = false;
        this.mostrarAyudaRegistro = false;
        this.mostrarAyudaVerificarCuenta = false;
        this.mostrarAyudaCambiarContrasenia = false;
        this.mostrarAyudaReferirAmigos = false;
        this.mostrarAyudaRecuperarContrasenia = false;
        this.mostrarAyudaBusquedaAprendiz = true;
        this.mostrarAyudaCrearVideollamada = false;
        this.mostrarAyudaUnirseVideollamada = false;
        this.mostrarAyudaReportarUsuario = false;
        this.mostrarAyudaBloquearUsuario = false;
        this.mostrarAyudaChat = false;
        this.mostrarAyudaPerfilUsuario = false;
        this.mostrarAyudaRanking = false;
        this.mostrarAyudaEditarPerfil = false;
        this.mostrarAyudaEliminarCuenta = false;
        break;
      case 'crearVideollamada':
        this.mostrarAyudaIntroduccion = false;
        this.mostrarAyudaRegistro = false;
        this.mostrarAyudaVerificarCuenta = false;
        this.mostrarAyudaCambiarContrasenia = false;
        this.mostrarAyudaReferirAmigos = false;
        this.mostrarAyudaRecuperarContrasenia = false;
        this.mostrarAyudaBusquedaAprendiz = false;
        this.mostrarAyudaCrearVideollamada = true;
        this.mostrarAyudaUnirseVideollamada = false;
        this.mostrarAyudaReportarUsuario = false;
        this.mostrarAyudaBloquearUsuario = false;
        this.mostrarAyudaChat = false;
        this.mostrarAyudaPerfilUsuario = false;
        this.mostrarAyudaRanking = false;
        this.mostrarAyudaEditarPerfil = false;
        this.mostrarAyudaEliminarCuenta = false;
        break;
      case 'unirseVideollamada':
        this.mostrarAyudaIntroduccion = false;
        this.mostrarAyudaRegistro = false;
        this.mostrarAyudaVerificarCuenta = false;
        this.mostrarAyudaCambiarContrasenia = false;
        this.mostrarAyudaReferirAmigos = false;
        this.mostrarAyudaRecuperarContrasenia = false;
        this.mostrarAyudaBusquedaAprendiz = false;
        this.mostrarAyudaCrearVideollamada = false;
        this.mostrarAyudaUnirseVideollamada = true;
        this.mostrarAyudaReportarUsuario = false;
        this.mostrarAyudaBloquearUsuario = false;
        this.mostrarAyudaChat = false;
        this.mostrarAyudaPerfilUsuario = false;
        this.mostrarAyudaRanking = false;
        this.mostrarAyudaEditarPerfil = false;
        this.mostrarAyudaEliminarCuenta = false;
        break;
      case 'reportarUsuario':
        this.mostrarAyudaIntroduccion = false;
        this.mostrarAyudaRegistro = false;
        this.mostrarAyudaVerificarCuenta = false;
        this.mostrarAyudaCambiarContrasenia = false;
        this.mostrarAyudaReferirAmigos = false;
        this.mostrarAyudaRecuperarContrasenia = false;
        this.mostrarAyudaBusquedaAprendiz = false;
        this.mostrarAyudaCrearVideollamada = false;
        this.mostrarAyudaUnirseVideollamada = false;
        this.mostrarAyudaReportarUsuario = true;
        this.mostrarAyudaBloquearUsuario = false;
        this.mostrarAyudaChat = false;
        this.mostrarAyudaPerfilUsuario = false;
        this.mostrarAyudaRanking = false;
        this.mostrarAyudaEditarPerfil = false;
        this.mostrarAyudaEliminarCuenta = false;
        break;
      case 'bloquearUsuario':
        this.mostrarAyudaIntroduccion = false;
        this.mostrarAyudaRegistro = false;
        this.mostrarAyudaVerificarCuenta = false;
        this.mostrarAyudaCambiarContrasenia = false;
        this.mostrarAyudaReferirAmigos = false;
        this.mostrarAyudaRecuperarContrasenia = false;
        this.mostrarAyudaBusquedaAprendiz = false;
        this.mostrarAyudaCrearVideollamada = false;
        this.mostrarAyudaUnirseVideollamada = false;
        this.mostrarAyudaReportarUsuario = false;
        this.mostrarAyudaBloquearUsuario = true;
        this.mostrarAyudaChat = false;
        this.mostrarAyudaPerfilUsuario = false;
        this.mostrarAyudaRanking = false;
        this.mostrarAyudaEditarPerfil = false;
        this.mostrarAyudaEliminarCuenta = false;
        break;
      case 'chat':
        this.mostrarAyudaIntroduccion = false;
        this.mostrarAyudaRegistro = false;
        this.mostrarAyudaVerificarCuenta = false;
        this.mostrarAyudaCambiarContrasenia = false;
        this.mostrarAyudaReferirAmigos = false;
        this.mostrarAyudaRecuperarContrasenia = false;
        this.mostrarAyudaBusquedaAprendiz = false;
        this.mostrarAyudaCrearVideollamada = false;
        this.mostrarAyudaUnirseVideollamada = false;
        this.mostrarAyudaReportarUsuario = false;
        this.mostrarAyudaBloquearUsuario = false;
        this.mostrarAyudaChat = true;
        this.mostrarAyudaPerfilUsuario = false;
        this.mostrarAyudaRanking = false;
        this.mostrarAyudaEditarPerfil = false;
        this.mostrarAyudaEliminarCuenta = false;
        break;
      case 'perfilUsuario':
        this.mostrarAyudaIntroduccion = false;
        this.mostrarAyudaRegistro = false;
        this.mostrarAyudaVerificarCuenta = false;
        this.mostrarAyudaCambiarContrasenia = false;
        this.mostrarAyudaReferirAmigos = false;
        this.mostrarAyudaRecuperarContrasenia = false;
        this.mostrarAyudaBusquedaAprendiz = false;
        this.mostrarAyudaCrearVideollamada = false;
        this.mostrarAyudaUnirseVideollamada = false;
        this.mostrarAyudaReportarUsuario = false;
        this.mostrarAyudaBloquearUsuario = false;
        this.mostrarAyudaChat = false;
        this.mostrarAyudaPerfilUsuario = true;
        this.mostrarAyudaRanking = false;
        this.mostrarAyudaEditarPerfil = false;
        this.mostrarAyudaEliminarCuenta = false;
        break;
      case 'ranking':
        this.mostrarAyudaIntroduccion = false;
        this.mostrarAyudaRegistro = false;
        this.mostrarAyudaVerificarCuenta = false;
        this.mostrarAyudaCambiarContrasenia = false;
        this.mostrarAyudaReferirAmigos = false;
        this.mostrarAyudaRecuperarContrasenia = false;
        this.mostrarAyudaBusquedaAprendiz = false;
        this.mostrarAyudaCrearVideollamada = false;
        this.mostrarAyudaUnirseVideollamada = false;
        this.mostrarAyudaReportarUsuario = false;
        this.mostrarAyudaBloquearUsuario = false;
        this.mostrarAyudaChat = false;
        this.mostrarAyudaPerfilUsuario = false;
        this.mostrarAyudaRanking = true;
        this.mostrarAyudaEditarPerfil = false;
        this.mostrarAyudaEliminarCuenta = false;
        break;
      case 'editarPerfil':
        this.mostrarAyudaIntroduccion = false;
        this.mostrarAyudaRegistro = false;
        this.mostrarAyudaVerificarCuenta = false;
        this.mostrarAyudaCambiarContrasenia = false;
        this.mostrarAyudaReferirAmigos = false;
        this.mostrarAyudaRecuperarContrasenia = false;
        this.mostrarAyudaBusquedaAprendiz = false;
        this.mostrarAyudaCrearVideollamada = false;
        this.mostrarAyudaUnirseVideollamada = false;
        this.mostrarAyudaReportarUsuario = false;
        this.mostrarAyudaBloquearUsuario = false;
        this.mostrarAyudaChat = false;
        this.mostrarAyudaPerfilUsuario = false;
        this.mostrarAyudaRanking = false;
        this.mostrarAyudaEditarPerfil = true;
        this.mostrarAyudaEliminarCuenta = false;
        break;
      case 'eliminarCuenta':
        this.mostrarAyudaIntroduccion = false;
        this.mostrarAyudaRegistro = false;
        this.mostrarAyudaVerificarCuenta = false;
        this.mostrarAyudaCambiarContrasenia = false;
        this.mostrarAyudaReferirAmigos = false;
        this.mostrarAyudaRecuperarContrasenia = false;
        this.mostrarAyudaBusquedaAprendiz = false;
        this.mostrarAyudaCrearVideollamada = false;
        this.mostrarAyudaUnirseVideollamada = false;
        this.mostrarAyudaReportarUsuario = false;
        this.mostrarAyudaBloquearUsuario = false;
        this.mostrarAyudaChat = false;
        this.mostrarAyudaPerfilUsuario = false;
        this.mostrarAyudaRanking = false;
        this.mostrarAyudaEditarPerfil = false;
        this.mostrarAyudaEliminarCuenta = true;
        break;
      default:
        this.route.navigate(['ayuda', 'introduccion']);
        break;
    }
  }

  redirigirFaqIntroduccion() {
    this.route.navigate(['ayuda', 'introduccion']);
  }

  redirigirFaqRegistro() {
    this.route.navigate(['ayuda', 'registro']);
  }

  redirigirFaqVerificarCuenta() {
    this.route.navigate(['ayuda', 'verificarCuenta']);
  }

  redirigirFaqCambiarContrasenia() {
    this.route.navigate(['ayuda', 'cambiarContrasenia']);
  }

  redirigirFaqReferirAmigos() {
    this.route.navigate(['ayuda', 'referirAmigo']);
  }

  redirigirFaqRecuperarContrasenia() {
    this.route.navigate(['ayuda', 'recuperarContrasenia']);
  }

  redirigirFaqBuscarAprendiz() {
    this.route.navigate(['ayuda', 'buscarAprendiz']);
  }

  redirigirFaqVideollamada() {
    this.route.navigate(['ayuda', 'crearVideollamada']);
  }

  redirigirFaqUnirseVideollamada() {
    this.route.navigate(['ayuda', 'unirseVideollamada']);
  }

  redirigirFaqReportarUsuario() {
    this.route.navigate(['ayuda', 'reportarUsuario']);
  }

  redirigirFaqBloquearUsuario() {
    this.route.navigate(['ayuda', 'bloquearUsuario']);
  }

  redirigirFaqChat() {
    this.route.navigate(['ayuda', 'chat']);
  }

  redirigirFaqPerfilUsuario() {
    this.route.navigate(['ayuda', 'perfilUsuario']);
  }

  redirigirFaqRanking() {
    this.route.navigate(['ayuda', 'ranking']);
  }

  redirigirFaqEditarPerfil() {
    this.route.navigate(['ayuda', 'editarPerfil']);
  }

  redirigirFaqEliminarCuenta() {
    this.route.navigate(['ayuda', 'eliminarCuenta']);
  }

  descargarManual() {
    const pdfUrl = 'https://firebasestorage.googleapis.com/v0/b/talk2gether-a8b61.appspot.com/o/manuales-de-usuario%2FManual%20del%20Usuario%20-%20Talk2gether%20.pdf?alt=media&token=d46dad99-b319-4739-b867-94972e851254&_gl=1*obkvj6*_ga*MTczMzczOTM5NS4xNjkwNjcxMjcx*_ga_CW55HF8NVT*MTY5Njc5NTUxMC4xMC4xLjE2OTY3OTY0MTMuNTcuMC4w';  // Reemplaza 'URL_DEL_PDF' con tu URL real

    this.http.get(pdfUrl, { responseType: 'arraybuffer' })
      .subscribe((data: ArrayBuffer) => {
        const blob = new Blob([data], { type: 'application/pdf' });

        const link = document.createElement('a');
        link.href = window.URL.createObjectURL(blob);
        link.download = 'Manual_Usuario_Talk2gether.pdf';
        link.click();
      });
  }

}
