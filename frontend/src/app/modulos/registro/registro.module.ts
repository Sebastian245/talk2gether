import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { InteresesModalComponent } from './registrar-usuario2/intereses-modal/intereses-modal.component';
import { BrowserModule } from '@angular/platform-browser';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { RegistrarUsuario1Component } from './registrar-usuario1/registrar-usuario1.component';
import { RegistrarUsuario2Component } from './registrar-usuario2/registrar-usuario2.component';
import { RegistroPadreComponent } from './registro-padre.component';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { TerminosComponent } from './terminos/terminos.component';

@NgModule({
  declarations: [
    InteresesModalComponent,
    RegistrarUsuario1Component,
    RegistrarUsuario2Component,
    RegistroPadreComponent,
    TerminosComponent
  ],
  imports: [
    CommonModule,
    BrowserModule,
    
    HttpClientModule,
    ReactiveFormsModule,
  ],
  exports: [
    InteresesModalComponent
  ]
})
export class RegistroModule { }