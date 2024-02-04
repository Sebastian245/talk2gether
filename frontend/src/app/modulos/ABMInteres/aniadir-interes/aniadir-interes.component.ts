import { Component, ViewEncapsulation } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ModalService } from '../../registro/servicios/modal.service';
import { Router } from '@angular/router';
import { SpinnerServiceGeneral } from 'src/app/compartidos/spinner-carga-general/spinner.service';
import Swal from 'sweetalert2';
import { InteresService } from '../service/interes.service';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { InfoPaisComponent } from '../../ABMPais/info-pais/info-pais.component';
import { InfoInteresComponent } from '../info-interes/info-interes.component';

@Component({
  selector: 'app-aniadir-interes',
  templateUrl: './aniadir-interes.component.html',
  styleUrls: ['./aniadir-interes.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class AniadirInteresComponent {
  interesForm: FormGroup;
  id:number;
  submitForm: boolean = false;
  modalRef: NgbModalRef;
  constructor(private modalService: ModalService,
              private interesService: InteresService,
              private router: Router,
              private spinnerServiceGeneral: SpinnerServiceGeneral){}

  ngOnInit(){
    this.interesForm = new FormGroup(
      {
        interes: new FormControl('',[Validators.required, this.validarLetras]),
        urlInteres:new FormControl('',Validators.required)
      },
    );
    
    this.interesService.getId().subscribe({
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
    if (this.interesForm.invalid) return;
    this.spinnerServiceGeneral.showSpinner();
    this.interesService.crearInteres(this.interesForm.get('interes').value,this.interesForm.get('urlInteres').value).subscribe({
      next: (response) => {                    
          this.spinnerServiceGeneral.hideSpinner();
          this.dismissModal();
          this.reloadPage();
          Swal.fire({
            position: 'top-end',
            icon: 'success',
            title: 'Interés creado correctamente',
            showConfirmButton: false,
            timer: 1500
          })
      },                 
      error: (error) => {
        if (error.error.mensaje === 'Ya existe un interés con el mismo nombre.') {
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
  abrirInfo(){
    this.modalRef = this.modalService.open(InfoInteresComponent, {
      centered: true,
    });
  }
}
