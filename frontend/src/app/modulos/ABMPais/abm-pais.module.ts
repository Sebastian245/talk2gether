import { NgModule } from "@angular/core";
import { visualizarPais } from "./visualizar-pais/visualizar-pais.component";
import { CommonModule } from "@angular/common";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { HttpClientModule } from "@angular/common/http";
import { BrowserModule } from "@angular/platform-browser";
import { NgbModalModule } from "@ng-bootstrap/ng-bootstrap";
import { ModificarPaisComponent } from "./modificar-pais/modificar-pais.component";
import { AniadirPaisComponent } from './aniadir-pais/aniadir-pais.component';
import { InfoPaisComponent } from './info-pais/info-pais.component';



@NgModule({
  declarations: [visualizarPais, ModificarPaisComponent, AniadirPaisComponent, InfoPaisComponent],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule,
    BrowserModule,
    NgbModalModule,
  ],
  exports: [visualizarPais,ModificarPaisComponent]
  
})
export class ABMPaisModule { }
