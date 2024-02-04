import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PermisosComponent } from './permisos.component';
import { EditarPermisoComponent } from './editar-permiso/editar-permiso.component';
import { ReactiveFormsModule } from '@angular/forms';
import { NgSelectModule } from '@ng-select/ng-select';



@NgModule({
  declarations: [PermisosComponent, EditarPermisoComponent],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    NgSelectModule
  ],
  exports: [
    PermisosComponent
  ]

})
export class PermisosModule { }
