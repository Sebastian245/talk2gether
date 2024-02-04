import { Component, EventEmitter, Output } from '@angular/core';
import { Router } from '@angular/router';
import { EditarUsuarioService } from '../servicios/editar-usuario.service';
import { NavbarService } from 'src/app/compartidos/navbar/service/navbar.service';
import { ModalService } from '../../registro/servicios/modal.service';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import Swal from 'sweetalert2';
import { SpinnerServiceGeneral } from 'src/app/compartidos/spinner-carga-general/spinner.service';
import { EliminarCuentaModalComponent } from '../eliminar-cuenta-modal/eliminar-cuenta-modal.component';
import { CambiarContraseniaModalComponent } from '../cambiar-contrasenia-modal/cambiar-contrasenia-modal.component';
import { UsuariosBloqueadosModalComponent } from '../usuarios-bloqueados-modal/usuarios-bloqueados-modal.component';


@Component({
  selector: 'app-pantalla-configuracion',
  templateUrl: './pantalla-configuracion.component.html',
  styleUrls: ['./pantalla-configuracion.component.scss']
})
export class PantallaConfiguracionComponent {

  idUsuario: number;
  datosUsuario: any;
  modalRef: NgbModalRef;
  informacionCargada: boolean = false;
  cadenaIntereses = '';
  nombresIntereses: string;

  @Output() pasarPantalla: EventEmitter<any> = new EventEmitter();

  constructor(
    private editarUsuario: EditarUsuarioService,
    private modalService: ModalService,
    private navbarService: NavbarService,
    private spinner: SpinnerServiceGeneral,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.spinner.showSpinner();
    this.navbarService.traerUsuario().subscribe(
      {
        next: response => {
          this.idUsuario = response['id'];
          this.editarUsuario.obtenerDatosPersonales(this.idUsuario).subscribe((data: any) => {
            this.datosUsuario = data;
            this.nombresIntereses = this.datosUsuario.listaIntereses.map(interes => interes.name).join(', ');
            this.datosUsuario.nombresIntereses = this.nombresIntereses;
            this.spinner.hideSpinner();
            this.informacionCargada = true;
          });
        },
        error: error => {
          this.spinner.hideSpinner();
          localStorage.clear();
          sessionStorage.clear();
          localStorage.setItem('isLoggedIn', 'false');
          Swal.fire(
            'La sesión fue cerrada',
            'Inicie nuevamente para continuar en la plataforma',
            'question'
          )
          this.router.navigate([''])
        }
      }
    );
  }

  editarDatos() {
    this.editarUsuario.guardarId(this.idUsuario);
    this.editarUsuario.guardarInformacion(this.datosUsuario);
    //this.router.navigate(['editar-perfil']);
    this.pasarPantalla.emit();
  }


  eliminarCuenta() {
    console.log("Eliminar cuenta.");
  }

  openEliminarCuentaModal() {
    let modalInstance: any;
    this.modalRef = this.modalService.open(EliminarCuentaModalComponent, {
      centered: true
    });
    modalInstance = this.modalRef.componentInstance;
  }

  openCambiarContraseniaModal() {
    let modalInstance: any;
    this.modalRef = this.modalService.open(CambiarContraseniaModalComponent, {
      centered: true
    });
    modalInstance = this.modalRef.componentInstance;
  }

  openUsuariosBloqueadosModal() {
    let modalInstance: any;
    this.modalRef = this.modalService.open(UsuariosBloqueadosModalComponent, {
      centered: true
    });
    modalInstance = this.modalRef.componentInstance;
  }

}
