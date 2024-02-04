import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SideBarAyudaComponent } from './side-bar-ayuda.component';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { PregFreqModule } from 'src/app/modulos/preguntas-frecuentes/preg-freq.module';



@NgModule({
  declarations: [SideBarAyudaComponent],
  imports: [
    CommonModule,
    BrowserModule,
    PregFreqModule
  ],
  exports:[SideBarAyudaComponent]
})
export class SideBarAyudaModule { }
