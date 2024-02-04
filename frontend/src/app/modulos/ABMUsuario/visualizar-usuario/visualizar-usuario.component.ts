import { Component, ViewEncapsulation } from '@angular/core';
import { SpinnerServiceGeneral } from 'src/app/compartidos/spinner-carga-general/spinner.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ModalService } from '../../registro/servicios/modal.service';
import Swal from 'sweetalert2';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { BajaUsuarioComponent } from '../baja-usuario/baja-usuario.component';
import { ModificarUsuarioComponent } from '../modificar-usuario/modificar-usuario.component';
import { UsuarioForm } from '../modelo/usuarioForm';
import { UsuarioService } from '../services/usuario.service';
import { FormControl, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-visualizar-usuario',
  templateUrl: './visualizar-usuario.component.html',
  styleUrls: ['./visualizar-usuario.component.scss'],
})
export class VisualizarUsuarioComponent {
  usuarioList: UsuarioForm[] = [];
  modalRef: NgbModalRef;
  nuevoUsuario: UsuarioForm;
  busquedaForm: FormGroup;
  constructor(
    private usuarioService: UsuarioService,
    private spinnerServiceGeneral: SpinnerServiceGeneral,
    private route: Router,
    private router: ActivatedRoute,
    private modalService: ModalService,
  ) {}

  ngOnInit(): void {
    this.busquedaForm = new FormGroup(
      {
        cadena: new FormControl('')
      },
    );
   this.traerUsuarios();
  }
  

  traerUsuarios(cadena?: string){
    if(cadena){
      this.usuarioList = []
      this.spinnerServiceGeneral.showSpinner();
      this.usuarioService.buscador(cadena).subscribe({
        next: (usuarios: any[]) => {
          for (const usuario of usuarios) {
            this.nuevoUsuario = {
              idCuenta: usuario['idCuenta'],
              apellidoUsuario: usuario['apellidoUsuario'],
              correo: usuario['correo'],
              nombreUsuario: usuario['nombreUsuario'],
              nombreRol: usuario['nombreRol'],
            };
            this.usuarioList.push(this.nuevoUsuario);
          }
          this.spinnerServiceGeneral.hideSpinner();
        },
        error: (error) => {
          Swal.fire({
            icon: 'error',
            title: 'Oops...',
            confirmButtonColor: '#199DE8',
            text: 'Algo salió mal! Reinicie la página. Si el error persiste intente mas tarde.',
          });
          this.spinnerServiceGeneral.hideSpinner();
        },
      });
    
    } else {
      this.usuarioList = []
      this.spinnerServiceGeneral.showSpinner();
      this.usuarioService.traerUsuarios().subscribe({
        next: (usuarios: any[]) => {
          for (const usuario of usuarios) {
            this.nuevoUsuario = {
              idCuenta: usuario['idCuenta'],
              apellidoUsuario: usuario['apellidoUsuario'],
              correo: usuario['correo'],
              nombreUsuario: usuario['nombreUsuario'],
              nombreRol: usuario['nombreRol'],
            };
            this.usuarioList.push(this.nuevoUsuario);
          }
          this.spinnerServiceGeneral.hideSpinner();
        },
        error: (error) => {
          Swal.fire({
            icon: 'error',
            title: 'Oops...',
            confirmButtonColor: '#199DE8',
            text: 'Algo salió mal! Reinicie la página. Si el error persiste intente mas tarde.',
          });
          this.spinnerServiceGeneral.hideSpinner();
        },
      });
    }
  }

  buscarUsuariosAdministrador(){
    this.traerUsuarios(this.busquedaForm.get('cadena').value)
  }

  eliminarUsuario(id:number, nombre:string, apellido:string, correo:string, nombreRol:string) {
    this.usuarioService.guardarId(id);
    const nombreApellido = nombre+", "+apellido
    this.usuarioService.guardarNombreApellido(nombreApellido);
    this.usuarioService.guardarcorreo(correo);
    this.usuarioService.guardarNombreRol(nombreRol);
    this.modalRef = this.modalService.open(BajaUsuarioComponent, {
      centered: true,
    });
  }
  modificarUsuario(id:number, nombre:string, apellido:string, correo:string, nombreRol:string) {
    this.usuarioService.guardarId(id);
    const nombreApellido = nombre+", "+apellido
    this.usuarioService.guardarNombreApellido(nombreApellido);
    this.usuarioService.guardarcorreo(correo);
    this.usuarioService.guardarNombreRol(nombreRol);
    this.modalRef = this.modalService.open(ModificarUsuarioComponent, {
      centered: true,
    });
  }


  redireccionarCrearUsuario(){
    this.route.navigate(['administrador','crearUsuario'])
  } 
  

  dirigirPerfil(id: number){
    const url = `/perfilUsuario/`+id;
    window.open(url, '_blank');

  }

  visualizarCalificacion(idCuenta: number){
    const url = `/administrador/visualizarCalificacion/`+idCuenta;
    window.open(url, '_blank');
    // this.route.navigate(['administrador','visualizarCalificacion',idCuenta])
  }
  reloadPage() {
    const currentUrl = this.route.url;
    this.route.navigateByUrl('/administrador', { skipLocationChange: true }).then(() => {
      this.route.navigateByUrl(currentUrl);
    });
  }
}
