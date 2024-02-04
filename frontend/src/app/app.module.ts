import { CommonModule, TitleCasePipe } from '@angular/common';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { initializeApp, provideFirebaseApp } from '@angular/fire/app';
import { getStorage, provideStorage } from '@angular/fire/storage';
import { BrowserModule } from '@angular/platform-browser';
import { NgxPaginationModule } from 'ngx-pagination';
import { environment } from '../environments/environment';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavbarModule } from './compartidos/navbar/navbar.module';
import { SpinnerCargaGeneralModule } from './compartidos/spinner-carga-general/spinner-carga-general.module';
import { AuthInterceptor } from './interceptors/auth.interceptor';
import { AUTH_TOKEN } from './interceptors/auth.token';
import { SesionService } from './interceptors/sesion.service';
import { ModuloAdministradorModule } from './modulos/administrador/modulo-administrador.module';
import { CambioContraseniaModule } from './modulos/cambio-contrasenia/cambio-contrasenia.module';
import { InicioModule } from './modulos/inicio/inicio.module';
import { LoginModule } from './modulos/login/login-module.module';
import { PantallaPrincipalModule } from './modulos/pantalla-principal/pantalla-principal.module';
import { RecuperarContraseniaModule } from './modulos/recuperar-contrasenia/recuperar-contrasenia.module';
import { RegistroModule } from './modulos/registro/registro.module';
import { VerificarCuentaModule } from './modulos/verificar-cuenta/verificar-cuenta.module';
import { SeguirModalModule } from './modulos/visualizar-perfil/seguir-modal/seguir-modal.module';
import { SinFuncionarComponent } from './sin-funcionar/sin-funcionar.component';
import { CalificarUsuarioModule } from './modulos/calificar-usuario/calificar-usuario.module';
import { ChatModule } from './modulos/chat/chat.module';
import { SideBarAyudaModule } from './compartidos/side-bar-ayuda/side-bar-ayuda.module';
import { PregFreqModule } from './modulos/preguntas-frecuentes/preg-freq.module';
import { ConfiguracionModule } from './modulos/configuracion/configuracion.module';
import { ReportarUsuarioModalModule } from './modulos/visualizar-perfil/reportar-usuario-modal/reportar-usuario-modal.module';

import { PuntosPorActividadModule } from './modulos/ABMPuntoActividad/puntos-por-actividad.module';

@NgModule({
  declarations: [
    AppComponent,
    SinFuncionarComponent,
    

  ],

  imports: [
    CommonModule,
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    NgxPaginationModule,
    provideFirebaseApp(() => initializeApp(environment.firebase)),
    provideStorage(() => getStorage()),
    RegistroModule,
    VerificarCuentaModule,
    InicioModule,
    AppRoutingModule,
    NavbarModule,
    LoginModule,
    RecuperarContraseniaModule,
    PantallaPrincipalModule,
    CambioContraseniaModule,
    SeguirModalModule,
    SpinnerCargaGeneralModule,
    ModuloAdministradorModule,
    CalificarUsuarioModule,
    ReportarUsuarioModalModule,
    ChatModule,
    SideBarAyudaModule,
    PregFreqModule,
    ConfiguracionModule,
  ],
  providers: [
    SesionService,
    { provide: AUTH_TOKEN, useValue: JSON.parse(localStorage.getItem('currentSession'))?.token || null },
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }, 
    TitleCasePipe
  ],

  bootstrap: [AppComponent]
})

export class AppModule {}
