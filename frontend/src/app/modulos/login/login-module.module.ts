import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { LoginComponent } from './login/login.component';
import { HttpClientModule } from '@angular/common/http';
import { LoginInhabilitadoComponent } from './login-inhabilitado/login-inhabilitado.component';



@NgModule({
  declarations: [
    LoginComponent,
    LoginInhabilitadoComponent,
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule
  ],
  exports: [
    LoginComponent
  ],
  providers: [
    
  ]
})
export class LoginModule { }
