import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ModalService } from '../../registro/servicios/modal.service';
import { NavbarService } from 'src/app/compartidos/navbar/service/navbar.service';
import { EliminarCuentaService } from '../servicios/eliminar-cuenta.service';
import { SpinnerServiceGeneral } from 'src/app/compartidos/spinner-carga-general/spinner.service';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';
import { SesionService } from 'src/app/interceptors/sesion.service';

@Component({
  selector: 'app-eliminar-cuenta-modal',
  templateUrl: './eliminar-cuenta-modal.component.html',
  styleUrls: ['./eliminar-cuenta-modal.component.scss'],
  encapsulation: ViewEncapsulation.None
})

export class EliminarCuentaModalComponent implements OnInit {

  idUsuario: number;
  otroCheckboxChecked = false;
  textareaContent = "";
  listaMotivos: any;
  eliminacion = {};
  motivosCargados = false;
  motivosVacios = false;
  contraseniaVacia = false;
  password = '';
  inputClicked: boolean = false;

  showPassword: boolean = false;
  showRepeatPassword: boolean = false;

  constructor(
    private modalService: ModalService,
    private navbarService: NavbarService,
    private eliminarCuentaService: EliminarCuentaService,
    private spinner: SpinnerServiceGeneral,
    private sesionService: SesionService,
    private router: Router
    ){}

  ngOnInit() {
    this.spinner.showSpinner();
    this.navbarService.traerUsuario().subscribe({
      next: response => {
        this.idUsuario = response['id'];
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
    });

    this.eliminarCuentaService.listarMotivosEliminacion().subscribe({
      next: response => {
        this.listaMotivos = response.listaNombreMotivo;
        this.motivosCargados = true;
        this.spinner.hideSpinner();
      },
      error: error =>{
      }
    });

  }

  eliminarCuenta(){
    //const contrasenia = document.getElementById("password") as HTMLSelectElement;
    const listaMotivos = this.obtenerCheckboxMarcados();

    if (listaMotivos.length == 0 && this.textareaContent == ""){
      this.motivosVacios = true;
      return;
    }else{
      this.motivosVacios = false;
    }

    if(this.password == ''){
      this.inputClicked = true;
      return;
    }

    this.eliminacion = {
      "idCuenta" : this.idUsuario,
      "listaMotivos": listaMotivos,
      "descripcionCuentaEliminada": this.textareaContent,
      "contrasenia": this.password
    }
    //console.log(this.eliminacion);
    this.dismissModal();
    this.spinner.showSpinner();
    this.eliminarCuentaService.eliminarCuenta(this.eliminacion).subscribe({
      next: response => {
        this.spinner.hideSpinner();
        this.sesionService.clearLocalSession();
        this.router.navigate(['cuenta-eliminada']);
      },
      error: error => {
        this.spinner.hideSpinner();
        console.log(error.error);
        Swal.fire({
          icon: 'error',
          title: 'No fue posible eliminar su cuenta',
          text: error.error,
        });
      }
    })
  }

  obtenerCheckboxMarcados() {
    const motivosMarcados = [];
    const checkboxes = document.querySelectorAll('input[type="checkbox"]');
    checkboxes.forEach(checkbox =>{
      var check = checkbox as HTMLInputElement
      if(check.checked && check.id != 'OtroCheckbox'){
        motivosMarcados.push(check.value);
      }
    })
    return motivosMarcados;
  }


  dismissModal() {
    this.modalService.dismissActiveModal();
  }

  togglePasswordView(): void {
    this.showPassword = !this.showPassword;
  }

  toggleRepeatPasswordView(): void {
    this.showRepeatPassword = !this.showRepeatPassword;
  }

  toggleInputClicked() {
    this.inputClicked = true;
  }

}
