import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ReportarUsuarioModalComponent } from './reportar-usuario-modal.component';
import { NgIf } from '@angular/common';
import { NgSelectModule } from '@ng-select/ng-select';

@NgModule({
  declarations: [ReportarUsuarioModalComponent],
  imports: [
    FormsModule,
    NgIf,
    NgSelectModule,
    ReactiveFormsModule
  ],
  exports: [
    ReportarUsuarioModalComponent
  ]
})

export class ReportarUsuarioModalModule { }
