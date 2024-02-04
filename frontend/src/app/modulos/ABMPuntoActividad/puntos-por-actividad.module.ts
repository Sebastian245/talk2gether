import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { VisualizarPuntosComponent } from './visualizar-puntos/visualizar-puntos.component';
import { ModificarPuntosComponent } from './modificar-puntos/modificar-puntos.component';
import { ReactiveFormsModule } from '@angular/forms';



@NgModule({
  declarations: [VisualizarPuntosComponent, ModificarPuntosComponent],
  imports: [
    CommonModule,
    ReactiveFormsModule
  ],
  exports: [
    VisualizarPuntosComponent
  ]
})
export class PuntosPorActividadModule { }
