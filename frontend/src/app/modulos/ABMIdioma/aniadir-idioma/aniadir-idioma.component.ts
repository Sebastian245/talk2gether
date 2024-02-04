import { Component, ViewEncapsulation } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import Swal from 'sweetalert2';
import { ModalService } from '../../registro/servicios/modal.service';
import { IdiomaService } from '../service/idioma.service';
import { Router } from '@angular/router';
import { SpinnerServiceGeneral } from 'src/app/compartidos/spinner-carga-general/spinner.service';

@Component({
  selector: 'app-aniadir-idioma',
  templateUrl: './aniadir-idioma.component.html',
  styleUrls: ['./aniadir-idioma.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class AniadirIdiomaComponent {
  idiomaForm: FormGroup;
  id:number;
  submitForm: boolean = false;
  constructor(private modalService: ModalService,
              private idiomaService: IdiomaService,
              private router: Router,
              private spinnerServiceGeneral: SpinnerServiceGeneral){}

  ngOnInit(){
    this.idiomaForm = new FormGroup(
      {
        idioma: new FormControl('',[Validators.required, this.validarLetras])
      },
    );
    
    this.idiomaService.getId().subscribe({
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
    if (this.idiomaForm.invalid) return;
    this.spinnerServiceGeneral.showSpinner();
    this.idiomaService.agregarIdioma(this.idiomaForm.get('idioma').value).subscribe({
      next: (response) => {                    
          this.spinnerServiceGeneral.hideSpinner();
          this.dismissModal();
          this.reloadPage();
          Swal.fire({
            position: 'top-end',
            icon: 'success',
            title: 'Idioma creado correctamente',
            showConfirmButton: false,
            timer: 1500
          })
      },                 
      error: (error) => {
        if (error.error.mensaje === 'Ya existe un idioma con el mismo nombre.') {
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
