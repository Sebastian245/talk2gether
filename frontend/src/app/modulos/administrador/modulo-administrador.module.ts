import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SideBarComponent } from './side-bar/side-bar.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { ABMPaisModule } from '../ABMPais/abm-pais.module';
import { AbmIdiomaModule } from '../ABMIdioma/abm-idioma.module';
import { AbmInteresModule } from '../ABMInteres/abm-interes.module';
import { AbmNivelIdiomaModule } from '../ABMNiveldeIdioma/abm-nivel-idioma.module';
import { RecuperacionBackUpComponent } from './recuperacion-back-up/recuperacion-back-up.component';
import { ABMUsuarioModule } from '../ABMUsuario/ABMusuario.module';
import { AbmMotivoModule } from '../ABMMotivo/abm-motivo.module';
import { ReporteUsuarioModule } from '../reporte-usuarios/reporte-usuario.module';
import { ReporteUsuarioChatModule } from '../reporte-usuarios-chat/reporte-usuario-chat.module';
import { AbmRolesModule } from '../ABMRoles/abm-roles.module';
import { PermisosModule } from '../permisos/permisos.module';
import { PuntosPorActividadModule } from '../ABMPuntoActividad/puntos-por-actividad.module';
import { BackupDbComponent } from './backup-db/backup-db.component';
import { DashboardAdministradorComponent } from './dashboard-administrador/dashboard/dashboard-administrador.component';
import { FiltroFechaComponent } from './dashboard-administrador/filtro-fecha/filtro-fecha.component';
import { NavbarModule } from 'src/app/compartidos/navbar/navbar.module';




@NgModule({

  declarations: [SideBarComponent, RecuperacionBackUpComponent,DashboardAdministradorComponent, BackupDbComponent,FiltroFechaComponent],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule,
    ABMPaisModule,
    AbmIdiomaModule,
    AbmInteresModule,
    AbmNivelIdiomaModule,
    ABMUsuarioModule,
    AbmMotivoModule,
    ReporteUsuarioModule,
    ReporteUsuarioChatModule,
    AbmRolesModule,
    PermisosModule,
    PuntosPorActividadModule,
    NavbarModule
  ],
})
export class ModuloAdministradorModule { }
