import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';  
import { BrowserModule } from '@angular/platform-browser';
import { CalificarModalComponent } from './calificar-modal/calificar-modal.component';


@NgModule({
  declarations: [
    CalificarModalComponent
  ],
  imports: [
    CommonModule,
    BrowserModule,
    FormsModule,    
    ReactiveFormsModule,
    HttpClientModule
  ]
})
export class CalificarUsuarioModule { }
