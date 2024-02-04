import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { VisualizarMotivoComponent } from './visualizar-motivo/visualizar-motivo.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { BrowserModule } from '@angular/platform-browser';
import { NgbModalModule } from '@ng-bootstrap/ng-bootstrap';
import { AniadirMotivoComponent } from './aniadir-motivo/aniadir-motivo.component';
import { ModificarMotivoComponent } from './modificar-motivo/modificar-motivo.component';



@NgModule({
  declarations: [
    VisualizarMotivoComponent,
    AniadirMotivoComponent,
    ModificarMotivoComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule,
    BrowserModule,
    NgbModalModule,
  ],
  exports: [
    VisualizarMotivoComponent
  ]
})
export class AbmMotivoModule { }
