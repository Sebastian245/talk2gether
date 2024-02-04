import { NgModule } from '@angular/core';
import { CommonModule, NgIf } from '@angular/common';
import { ReporteUsuarioComponent } from './reporte-usuario/reporte-usuario.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { BrowserModule } from '@angular/platform-browser';
import { NgbModalModule } from '@ng-bootstrap/ng-bootstrap';
import { ReporteModalComponent } from './modal-reportes/reporte-modal/reporte-modal.component';
import { FiltrosModalComponent } from './filtros-modal/filtros-modal.component';



@NgModule({
  declarations: [
    ReporteUsuarioComponent,
    ReporteModalComponent,
    FiltrosModalComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule,
    BrowserModule,
    NgbModalModule,
    NgIf,

  ],
  exports: [
    ReporteUsuarioComponent
  ]
})
export class ReporteUsuarioModule { }
