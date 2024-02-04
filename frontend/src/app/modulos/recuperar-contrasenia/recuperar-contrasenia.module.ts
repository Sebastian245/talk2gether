import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RecuperarContraseniaComponent } from './recuperar-contrasenia.component';
import { RecuperarContraseniaFormComponent } from './recuperar-contrasenia-form/recuperar-contrasenia-form.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RecuperarContraseniaConfirmacionComponent } from './recuperar-contrasenia-confirmacion/recuperar-contrasenia-confirmacion.component';



@NgModule({
  declarations: [
    RecuperarContraseniaComponent,
    RecuperarContraseniaFormComponent,
    RecuperarContraseniaConfirmacionComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
  ]
})
export class RecuperarContraseniaModule { }
