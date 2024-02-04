import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PantallaConfiguracionComponent } from './pantalla-configuracion/pantalla-configuracion.component';
import { EditarPerfilComponent } from './editar-perfil/editar-perfil.component';
import { CuentaEliminadaComponent } from './cuenta-eliminada/cuenta-eliminada.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { EliminarCuentaModalComponent } from './eliminar-cuenta-modal/eliminar-cuenta-modal.component';
import { ConfiguracionPadreComponent } from './configuracion-padre.component';
import { CambiarContraseniaModalComponent } from './cambiar-contrasenia-modal/cambiar-contrasenia-modal.component';
import { UsuariosBloqueadosModalComponent } from './usuarios-bloqueados-modal/usuarios-bloqueados-modal.component';
import { SpinnerModule } from 'src/app/compartidos/spinner-carga/spinner.module';



@NgModule({
  declarations: [
    PantallaConfiguracionComponent,
    EditarPerfilComponent,
    CuentaEliminadaComponent,
    EliminarCuentaModalComponent,
    CambiarContraseniaModalComponent,
    ConfiguracionPadreComponent,
    UsuariosBloqueadosModalComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    SpinnerModule
  ]
})
export class ConfiguracionModule { }
