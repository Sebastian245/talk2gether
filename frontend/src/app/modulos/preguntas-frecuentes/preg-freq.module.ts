import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PregFreqRegistroComponent } from './preg-freq-registro/preg-freq-registro.component';
import { BrowserModule } from '@angular/platform-browser';
import { PregFreqIntroduccionComponent } from './preg-freq-introduccion/preg-freq-introduccion.component';
import { PregFreqCrearVideollamadaComponent } from './preg-freq-crear-videollamada/preg-freq-crear-videollamada.component';
import { PregFreqReferirAmigosComponent } from './preg-freq-referir-amigos/preg-freq-referir-amigos.component';
import { PregFreqVerificarCuentaComponent } from './preg-freq-verificar-cuenta/preg-freq-verificar-cuenta.component';
import { PregFreqCambiarContraseniaComponent } from './preg-freq-cambiar-contrasenia/preg-freq-cambiar-contrasenia.component';
import { PregFreqRecuperarContraseniaComponent } from './preg-freq-recuperar-contrasenia/preg-freq-recuperar-contrasenia.component';
import { PregFreqBusquedaAprendizComponent } from './preg-freq-busqueda-aprendiz/preg-freq-busqueda-aprendiz.component';
import { PregFreqUnirseVideollamadaComponent } from './preg-freq-unirse-videollamada/preg-freq-unirse-videollamada.component';
import { PregFreqReportarAprendizComponent } from './preg-freq-reportar-aprendiz/preg-freq-reportar-aprendiz.component';
import { PregFreqBloquearAprendizComponent } from './preg-freq-bloquear-aprendiz/preg-freq-bloquear-aprendiz.component';
import { PregFreqChatComponent } from './preg-freq-chat/preg-freq-chat.component';
import { PregFreqPerfilUsuarioComponent } from './preg-freq-perfil-usuario/preg-freq-perfil-usuario.component';
import { PregFreqRankingComponent } from './preg-freq-ranking/preg-freq-ranking.component';
import { PregFreqEditarPerfilComponent } from './preg-freq-editar-perfil/preg-freq-editar-perfil.component';
import { PregFreqEliminarCuentaComponent } from './preg-freq-eliminar-cuenta/preg-freq-eliminar-cuenta.component';

@NgModule({
  declarations: [
    PregFreqRegistroComponent,
    PregFreqIntroduccionComponent,
    PregFreqCrearVideollamadaComponent,
    PregFreqReferirAmigosComponent,
    PregFreqVerificarCuentaComponent,
    PregFreqCambiarContraseniaComponent,
    PregFreqRecuperarContraseniaComponent,
    PregFreqBusquedaAprendizComponent,
    PregFreqUnirseVideollamadaComponent,
    PregFreqReportarAprendizComponent,
    PregFreqBloquearAprendizComponent,
    PregFreqChatComponent,
    PregFreqPerfilUsuarioComponent,
    PregFreqRankingComponent,
    PregFreqEditarPerfilComponent,
    PregFreqEliminarCuentaComponent
  ],
  imports: [
    CommonModule,
    BrowserModule
  ],
  exports: [
    PregFreqRegistroComponent,
    PregFreqIntroduccionComponent,
    PregFreqCrearVideollamadaComponent,
    PregFreqReferirAmigosComponent,
    PregFreqVerificarCuentaComponent,
    PregFreqCambiarContraseniaComponent,
    PregFreqRecuperarContraseniaComponent,
    PregFreqBusquedaAprendizComponent,
    PregFreqUnirseVideollamadaComponent,
    PregFreqReportarAprendizComponent,
    PregFreqBloquearAprendizComponent,
    PregFreqChatComponent,
    PregFreqPerfilUsuarioComponent,
    PregFreqRankingComponent,
    PregFreqEditarPerfilComponent,
    PregFreqEliminarCuentaComponent
  ]

})

export class PregFreqModule { }
