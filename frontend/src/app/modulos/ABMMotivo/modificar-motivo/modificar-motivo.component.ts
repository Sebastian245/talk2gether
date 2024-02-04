import { Component, ViewEncapsulation } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import Swal from 'sweetalert2';
import { ModalService } from '../../registro/servicios/modal.service';
import { MotivoService } from '../service/motivo.service';
import { Router } from '@angular/router';
import { SpinnerServiceGeneral } from 'src/app/compartidos/spinner-carga-general/spinner.service';

@Component({
  selector: 'app-modificar-motivo',
  templateUrl: './modificar-motivo.component.html',
  styleUrls: ['./modificar-motivo.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ModificarMotivoComponent {
  motivosForm: FormGroup;
  id:number;
  submitForm: boolean = false;
  nombreMotivo: string;
  constructor(private modalService: ModalService,
              private motivosService: MotivoService,
              private router: Router,
              private spinnerServiceGeneral: SpinnerServiceGeneral){}

  ngOnInit(){
    this.motivosService.getMotivo().subscribe({next: (response)=> this.nombreMotivo = response});
    this.motivosForm = new FormGroup(
      {
        motivo: new FormControl(this.nombreMotivo,[Validators.required, this.validarLetras])
      },
    );
    
    this.motivosService.getId().subscribe({
      next: (response)=>{
        this.id = response;
      }
    })

  }

  dismissModal() {
    this.modalService.dismissActiveModal();
  }

  enviarDatos(){
    this.submitForm = true;
    if (this.motivosForm.invalid) return;
    this.spinnerServiceGeneral.showSpinner();
    this.motivosService.modificarMotivo(this.id,this.motivosForm.get('motivo').value).subscribe({
      next: (response) => {                    
          this.spinnerServiceGeneral.hideSpinner();
          this.dismissModal();
          this.reloadPage();
          Swal.fire({
            position: 'top-end',
            icon: 'success',
            title: 'Motivo actualizado correctamente',
            showConfirmButton: false,
            timer: 1500
          })
      },                 
      error: (error) => {
        if (error.error.mensaje === 'Ya existe un motivo con el mismo nombre.') {
          Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: error.error.mensaje,
          });
        } else {
          Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: 'Algo salió mal! Intente nuevamente mas tarde.',
          });
        }
        this.dismissModal();
        this.spinnerServiceGeneral.hideSpinner();
    },
  });
  }
  reloadPage() {
    const currentUrl = this.router.url;
    this.router.navigateByUrl('/administrador', { skipLocationChange: true }).then(() => {
      this.router.navigateByUrl(currentUrl);
    });
  }
  validarLetras(control) {
    const patron = /^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$/; 
    if (!patron.test(control.value)) {
      return { soloLetras: true };
    }
    return null;
  }
}
