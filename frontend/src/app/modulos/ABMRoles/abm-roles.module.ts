import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { VisualizarRolComponent } from './visualizar-rol/visualizar-rol.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { BrowserModule } from '@angular/platform-browser';
import { NgbModalModule } from '@ng-bootstrap/ng-bootstrap';
import { ModificarRolComponent } from './modificar-rol/modificar-rol.component';
import { AniadirRolComponent } from './aniadir-rol/aniadir-rol.component';



@NgModule({
  declarations: [
    VisualizarRolComponent,
    ModificarRolComponent,
    AniadirRolComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule,
    BrowserModule,
    NgbModalModule,
  ],
  exports:[VisualizarRolComponent]
})
export class AbmRolesModule { }
