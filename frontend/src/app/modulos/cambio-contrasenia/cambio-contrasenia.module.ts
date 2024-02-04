import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NuevaContraseniaComponent } from './nueva-contrasenia/nueva-contrasenia.component';
import { ConfirmarContraseniaComponent } from './confirmar-contrasenia/confirmar-contrasenia.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';



@NgModule({
  declarations: [
    NuevaContraseniaComponent,
    ConfirmarContraseniaComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
  ]
})
export class CambioContraseniaModule { }
