import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { VerificarCuentaComponent } from './modulos/verificar-cuenta/verificar-cuenta/verificar-cuenta.component';
import { CuentaVerificadaComponent } from './modulos/verificar-cuenta/cuenta-verificada/cuenta-verificada.component';
import { RegistroPadreComponent } from './modulos/registro/registro-padre.component';
import { TerminosComponent } from './modulos/registro/terminos/terminos.component';
import { PantallaInicioBusquedaComponent } from './modulos/inicio/pantalla-inicio-busqueda/pantalla-inicio-busqueda.component';
import { LoginComponent } from './modulos/login/login/login.component';
import { PrincipalComponent } from './modulos/pantalla-principal/principal/principal.component';
import { RecuperarContraseniaComponent } from './modulos/recuperar-contrasenia/recuperar-contrasenia.component';
import { SinFuncionarComponent } from './sin-funcionar/sin-funcionar.component';
import { AuthGuard } from './security/auth-guard.guard';
import { LoginInhabilitadoComponent } from './modulos/login/login-inhabilitado/login-inhabilitado.component';
import { VisualizarPerfilComponent } from './modulos/visualizar-perfil/visualizar-perfil/visualizar-perfil.component';
import { NuevaContraseniaComponent } from './modulos/cambio-contrasenia/nueva-contrasenia/nueva-contrasenia.component';
import { ConfirmarContraseniaComponent } from './modulos/cambio-contrasenia/confirmar-contrasenia/confirmar-contrasenia.component';
import { BloqueadoComponent } from './modulos/visualizar-perfil/bloqueado/bloqueado.component';
import { loggedGuard } from './security/logged.guard';
import { SideBarComponent } from './modulos/administrador/side-bar/side-bar.component';
import { ChatPadreComponent } from './modulos/chat/chat-padre.component';
import { RankingComponent } from './modulos/inicio/ranking/ranking.component';
import { ReunionVirtualComponent } from './modulos/inicio/reunion-virtual/reunion-virtual.component';
import { SideBarAyudaComponent } from './compartidos/side-bar-ayuda/side-bar-ayuda.component';
import { CuentaEliminadaComponent } from './modulos/configuracion/cuenta-eliminada/cuenta-eliminada.component';
import { ChatPadreAbiertoComponent } from './modulos/chat/chat-padre-abierto.component';
import { ConfiguracionPadreComponent } from './modulos/configuracion/configuracion-padre.component';
import { blockedRoutes } from './security/blocked-routes';
import { LogrosComponent } from './modulos/visualizar-perfil/logros/logros.component';
import { EstadisticasComponent } from './modulos/visualizar-perfil/estadisticas/estadisticas.component';
import { DashboardAdministradorComponent } from './modulos/administrador/dashboard-administrador/dashboard/dashboard-administrador.component';


const routes: Routes = [
  {path: 'registro', component: RegistroPadreComponent, canActivate:[AuthGuard]},
  {path: 'inicio', component: PantallaInicioBusquedaComponent,  canActivate:[loggedGuard,blockedRoutes]},
  {path: 'reunion-virtual', component: ReunionVirtualComponent,  canActivate:[loggedGuard,blockedRoutes]},
  {path: 'configuracion', component: ConfiguracionPadreComponent,  canActivate:[loggedGuard,blockedRoutes]},
  {path: 'cuenta-eliminada', component: CuentaEliminadaComponent, canActivate:[AuthGuard]},
  {path: 'ranking', component: RankingComponent,  canActivate:[loggedGuard,blockedRoutes]},
  {path: 'terminos-y-condiciones', component: TerminosComponent,canActivate:[AuthGuard]} ,
  {path: 'verificar-cuenta', component: VerificarCuentaComponent,canActivate:[AuthGuard]},
  {path: 'cuenta-verificada', component: CuentaVerificadaComponent,canActivate:[AuthGuard]},
  {path: '', component: PrincipalComponent},
  {path: 'proximamente', component: SinFuncionarComponent},
  {path: 'login', component: LoginComponent, canActivate:[AuthGuard]},
  {path: 'recuperarContrasenia', component: RecuperarContraseniaComponent,canActivate:[AuthGuard]},
  {path: 'nuevaContrasenia', component: NuevaContraseniaComponent,canActivate:[AuthGuard]},
  {path: 'confirmacion', component: ConfirmarContraseniaComponent,canActivate:[AuthGuard]},
  {path: 'bloqueado', component: LoginInhabilitadoComponent,canActivate:[AuthGuard]},
  {path: 'perfilUsuario/:id', component: VisualizarPerfilComponent,canActivate:[loggedGuard,blockedRoutes]},
  {path: 'perfilUsuario/logros/:id', component: LogrosComponent,canActivate:[loggedGuard,blockedRoutes]},
  {path: 'perfilUsuario/estadisticas/:id', component: EstadisticasComponent,canActivate:[loggedGuard,blockedRoutes]},
  {path: 'usuarioBloqueado', component: BloqueadoComponent,canActivate:[loggedGuard,blockedRoutes]},
  {path: 'administrador', component: SideBarComponent,canActivate:[loggedGuard,blockedRoutes]},
  {path: 'administrador/:redireccion', component: SideBarComponent,canActivate:[loggedGuard,blockedRoutes]},
  {path: 'estadisticasPlataforma', component: DashboardAdministradorComponent,canActivate:[loggedGuard,blockedRoutes]},
  {path: 'administrador/:redireccion/:id', component: SideBarComponent,canActivate:[loggedGuard]},
  {path: 'chat', component: ChatPadreComponent,canActivate:[loggedGuard,blockedRoutes]},
  {path: 'ayuda/:seccion', component: SideBarAyudaComponent},
  {path: "chat/:id",component:ChatPadreAbiertoComponent,canActivate:[loggedGuard,blockedRoutes]},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { }