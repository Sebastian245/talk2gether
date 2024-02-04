import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BrowserModule } from '@angular/platform-browser';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { VerificarCuentaComponent } from './verificar-cuenta/verificar-cuenta.component';
import { CuentaVerificadaComponent } from './cuenta-verificada/cuenta-verificada.component';



@NgModule({
  declarations: [
    VerificarCuentaComponent,
    CuentaVerificadaComponent
  ],
  imports: [
    CommonModule,
    BrowserModule,
    NgbModule
  ]
})
export class VerificarCuentaModule { }
