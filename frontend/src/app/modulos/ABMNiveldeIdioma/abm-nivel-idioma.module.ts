import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AniadirNivelIdiomaComponent } from './aniadir-nivel-idioma/aniadir-nivel-idioma.component';
import { VisualizarNivelIdiomaComponent } from './visualizar-nivel-idioma/visualizar-nivel-idioma.component';
import { ModificarNivelIdiomaComponent } from './modificar-nivel-idioma/modificar-nivel-idioma.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { BrowserModule } from '@angular/platform-browser';
import { NgbModalModule } from '@ng-bootstrap/ng-bootstrap';



@NgModule({
  declarations: [
    AniadirNivelIdiomaComponent,
    VisualizarNivelIdiomaComponent,
    ModificarNivelIdiomaComponent
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
    AniadirNivelIdiomaComponent,
    VisualizarNivelIdiomaComponent,
    ModificarNivelIdiomaComponent
  ],
})
export class AbmNivelIdiomaModule { }
