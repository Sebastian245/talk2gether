import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PantallaInicioBusquedaComponent } from './pantalla-inicio-busqueda/pantalla-inicio-busqueda.component';
import { NgxPaginationModule } from 'ngx-pagination';
import { FiltrosModalComponent } from './filtros-modal/filtros-modal.component';
import { RankingComponent } from './ranking/ranking.component';
import { ReunionVirtualComponent } from './reunion-virtual/reunion-virtual.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    PantallaInicioBusquedaComponent,
    FiltrosModalComponent,
    RankingComponent,
    ReunionVirtualComponent
  ],
  imports: [
    CommonModule,
    NgxPaginationModule,
    ReactiveFormsModule,
    FormsModule
  ]
})

export class InicioModule { }
