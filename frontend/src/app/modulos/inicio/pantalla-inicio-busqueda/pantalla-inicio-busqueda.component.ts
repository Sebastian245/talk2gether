import { Component } from '@angular/core';
import { BuscarUsuariosService } from '../servicios/buscar-usuarios.service';
import { UsuarioBusqueda } from '../modelos/usuario-busqueda';
import { ModalService } from '../../registro/servicios/modal.service';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { FiltrosModalComponent } from '../filtros-modal/filtros-modal.component';
import { Router } from '@angular/router';
import { SpinnerServiceGeneral } from 'src/app/compartidos/spinner-carga-general/spinner.service';
import Swal from 'sweetalert2';
import { NavbarService } from 'src/app/compartidos/navbar/service/navbar.service';

@Component({
  selector: 'app-pantalla-inicio-busqueda',
  templateUrl: './pantalla-inicio-busqueda.component.html',
  styleUrls: ['./pantalla-inicio-busqueda.component.scss']
})
export class PantallaInicioBusquedaComponent {

  constructor(
    private buscarUsuariosServicio: BuscarUsuariosService,
    private modalService: ModalService,
    private router: Router,
    private navbarService: NavbarService,
    private spinner: SpinnerServiceGeneral) {}

  modalRef: NgbModalRef;
  page: number;
  linkReferido = "https://invite.talk2gether.com/BDHTZTB5" //Recuperar desde el backend
  idUsuario: number;
  idiomaUsuario: string;
  usuarios: UsuarioBusqueda[] = [];
  busqueda = {}
  parametroBusqueda = '';

  ngOnInit(){
    this.spinner.showSpinner();
    this.navbarService.traerUsuario().subscribe(
      {
        next: response => {
          this.idUsuario = response['id']
          this.buscarUsuarios();
          this.buscarUsuariosServicio.idiomaAprendiz(this.idUsuario).subscribe((data: any) => {
            this.idiomaUsuario = data.mensaje;
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

  buscarUsuarios(){
    this.buscarUsuariosServicio.buscarUsuarios(this.idUsuario).subscribe(
      data =>{
        this.usuarios = data;
        this.spinner.hideSpinner();
      },
      err =>{
        this.spinner.hideSpinner();
        Swal.fire({
          icon: 'error',
          title: 'Oops...',
          confirmButtonColor: '#199DE8',
          text: '¡Algo salió mal! Reinicie la página. Si el error persiste intente mas tarde.'
        })
      }
    );
  }

  buscarUsuariosTexto(){
    this.spinner.showSpinner();
    if (this.parametroBusqueda === ''){
      this.buscarUsuarios();
    }else{
      this.buscarUsuariosServicio.buscarUsuariosBuscador(this.parametroBusqueda).subscribe({
        next: subscribe =>{
          this.usuarios = subscribe;
          this.parametroBusqueda = '';
          this.spinner.hideSpinner();
        },
        error: next =>{
          this.spinner.hideSpinner();
          Swal.fire({
            icon: 'error',
            title: 'Oops...',
            confirmButtonColor: '#199DE8',
            text: '¡Algo salió mal! Reinicie la página. Si el error persiste intente mas tarde.'
          })
        }
      });
    }
  }

  buscarUsuariosFiltrados(){
    this.spinner.showSpinner();
    this.buscarUsuariosServicio.buscarUsuariosFiltrados(this.idUsuario, this.busqueda).subscribe({
      next:(data) =>{
        this.usuarios = data;
        this.spinner.hideSpinner();
      },
      error:(err) =>{
        this.spinner.hideSpinner();
        Swal.fire({
          icon: 'error',
          title: 'Oops...',
          confirmButtonColor: '#199DE8',
          text: '¡Algo salió mal! Reinicie la página. Si el error persiste intente mas tarde.'
        })
      }
    });
  }

  unirseAVideollamada(linkReunionVirtual: string){
    Swal.fire({
    title: '¿Seguro que desea unirse a ésta videollamada?',
    showCancelButton: true,
    reverseButtons: true,
    confirmButtonColor: '#3085d6',
    cancelButtonColor: '#d33',
    confirmButtonText: 'Continuar',
    cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        this.buscarUsuariosServicio.guardarLink(linkReunionVirtual);
        this.spinner.showSpinner();
        this.buscarUsuariosServicio.unirseAVideollamada(this.idUsuario, linkReunionVirtual).subscribe({
          next:(data) =>{
            this.spinner.hideSpinner();
            this.router.navigate(['reunion-virtual']);
          },
          error:(err) =>{
            this.spinner.hideSpinner();
            Swal.fire({
              icon: 'error',
              title: 'No es posible unirse a la sala.',
              confirmButtonColor: '#199DE8',
              text: err.error.mensaje,
              didClose: () => {
                this.reloadPage();
              }
            })
          }
        });
      }
    })
  }

  crearVideollamada(){
    Swal.fire({
      title: '¿Está seguro?',
      showCancelButton: true,
      reverseButtons: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Confirmar',
      cancelButtonText: 'Cancelar'
      }).then((result) => {
        if (result.isConfirmed) {
          this.spinner.showSpinner();
          this.buscarUsuariosServicio.crearVideollamada(this.idUsuario).subscribe({
            next:(data) =>{
              this.buscarUsuariosServicio.guardarLink(data['linkReunionVirtual']);
              this.spinner.hideSpinner();
              this.router.navigate(['reunion-virtual']);
            },
            error:(err) =>{
              this.spinner.hideSpinner();
              Swal.fire({
                icon: 'error',
                title: 'No es posible crear una nueva sala.',
                confirmButtonColor: '#199DE8',
                text: err.error.mensaje,
                didClose: () => {
                  this.reloadPage();
                }
              })
            }
          });
        }
      })
  }

  redirigirPerfilUsuario(id: number) {
    this.router.navigate(['perfilUsuario', id]);
  }

  iniciarChat(id: number) {
    this.router.navigate(['chat',id]);
  }

  openModal() {
    let modalInstance: any;
    this.modalRef = this.modalService.open(FiltrosModalComponent, {
      centered: true
    });
    modalInstance = this.modalRef.componentInstance;
    modalInstance.idiomaUsuario = this.idiomaUsuario;
    this.modalRef.dismissed.subscribe(data => {
      this.busqueda = modalInstance.busqueda;
      this.buscarUsuariosFiltrados();
    })
  }

  reloadPage() {
    const currentUrl = this.router.url;
    this.router.navigateByUrl('/inicio', { skipLocationChange: true }).then(() => {
      this.router.navigateByUrl(currentUrl);
    });
  }

}
