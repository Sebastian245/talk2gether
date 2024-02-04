import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SpinnerCargaGeneralComponent } from './spinner/spinner-carga-general/spinner-carga-general.component';
import { BrowserModule } from '@angular/platform-browser';



@NgModule({
  declarations: [SpinnerCargaGeneralComponent],
  imports: [
    CommonModule,
    BrowserModule,
  ],
  exports: [
    SpinnerCargaGeneralComponent
  ]
})
export class SpinnerCargaGeneralModule { }
