import { NgModule } from '@angular/core';
import { CommonModule, NgIf } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { BrowserModule } from '@angular/platform-browser';
import { NgbModalModule } from '@ng-bootstrap/ng-bootstrap';
import { VisualizarUsuarioComponent } from './visualizar-usuario/visualizar-usuario.component';
import { AniadirUsuarioComponent } from './aniadir-usuario/aniadir-usuario.component';
import { BajaUsuarioComponent } from './baja-usuario/baja-usuario.component';
import { ModificarUsuarioComponent } from './modificar-usuario/modificar-usuario.component';
import { NgSelectModule } from '@ng-select/ng-select';
import { VisualizarCalificacionComponent } from './visualizar-calificacion/visualizar-calificacion.component';



@NgModule({
  declarations: [
    VisualizarUsuarioComponent, AniadirUsuarioComponent, BajaUsuarioComponent, ModificarUsuarioComponent, VisualizarCalificacionComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule,
    BrowserModule,
    NgbModalModule,
    NgIf,
    NgSelectModule,

  ],
  exports:[VisualizarUsuarioComponent,AniadirUsuarioComponent,VisualizarCalificacionComponent]
})
export class ABMUsuarioModule { }
