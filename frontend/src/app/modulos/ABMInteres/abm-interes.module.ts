import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { VisualizarInteresComponent } from './visualizar-interes/visualizar-interes.component';
import { ModificarInteresComponent } from './modificar-interes/modificar-interes.component';
import { AniadirInteresComponent } from './aniadir-interes/aniadir-interes.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { BrowserModule } from '@angular/platform-browser';
import { NgbModalModule } from '@ng-bootstrap/ng-bootstrap';
import { InfoInteresComponent } from './info-interes/info-interes.component';



@NgModule({
  declarations: [VisualizarInteresComponent, ModificarInteresComponent, AniadirInteresComponent, InfoInteresComponent],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule,
    BrowserModule,
    NgbModalModule,
  ],
  exports: [VisualizarInteresComponent, ModificarInteresComponent]
})
export class AbmInteresModule { }
