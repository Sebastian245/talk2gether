import { NgModule } from '@angular/core';
import { VisualizarIdiomaComponent } from './visualizar-idioma/visualizar-idioma.component';
import { ModificarIdiomaComponent } from './modificar-idioma/modificar-idioma.component';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { BrowserModule } from '@angular/platform-browser';
import { NgbModalModule } from '@ng-bootstrap/ng-bootstrap';
import { AniadirIdiomaComponent } from './aniadir-idioma/aniadir-idioma.component';


@NgModule({
  declarations: [VisualizarIdiomaComponent, ModificarIdiomaComponent, AniadirIdiomaComponent],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule,
    BrowserModule,
    NgbModalModule,
  ],
  exports: [VisualizarIdiomaComponent,ModificarIdiomaComponent]
})
export class AbmIdiomaModule { }
