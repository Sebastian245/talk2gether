import { Component, ViewEncapsulation } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import Swal from 'sweetalert2';
import { ModalService } from '../../registro/servicios/modal.service';
import { Router } from '@angular/router';
import { SpinnerServiceGeneral } from 'src/app/compartidos/spinner-carga-general/spinner.service';
import { MotivoService } from '../service/motivo.service';

@Component({
  selector: 'app-aniadir-motivo',
  templateUrl: './aniadir-motivo.component.html',
  styleUrls: ['./aniadir-motivo.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class AniadirMotivoComponent {
  motivosForm: FormGroup;
  id:number;
  submitForm: boolean = false;
  constructor(private modalService: ModalService,
              private motivosService: MotivoService,
              private router: Router,
              private spinnerServiceGeneral: SpinnerServiceGeneral){}

  ngOnInit(){
    this.motivosForm = new FormGroup(
      {
        motivo: new FormControl('',[Validators.required, this.validarLetras]),
        tipoMotivo: new FormControl('',[Validators.required])
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
    this.motivosService.crearMotivo(this.motivosForm.get('motivo').value,this.motivosForm.get('tipoMotivo').value).subscribe({
      next: (response) => {                  
          this.spinnerServiceGeneral.hideSpinner();
          this.dismissModal();
          this.reloadPage();
          Swal.fire({
            position: 'top-end',
            icon: 'success',
            title: 'Motivo creado correctamente',
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
