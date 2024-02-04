import { NgModule } from '@angular/core';
import { CommonModule, NgIf } from '@angular/common';
import { MostrarReporteUsuarioChatComponent } from './mostrar-reporte-usuario-chat/mostrar-reporte-usuario-chat.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { ReporteChatsVisualizarComponent } from './reporte-chats-visualizar/reporte-chats-visualizar.component';



@NgModule({
  declarations: [
    MostrarReporteUsuarioChatComponent,
    ReporteChatsVisualizarComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    BrowserModule,
    NgIf
  ],
  exports:[MostrarReporteUsuarioChatComponent]
})
export class ReporteUsuarioChatModule { }
