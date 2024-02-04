import { Component, ViewEncapsulation } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import Swal from 'sweetalert2';
import { ModalService } from '../../registro/servicios/modal.service';
import { Router } from '@angular/router';
import { SpinnerServiceGeneral } from 'src/app/compartidos/spinner-carga-general/spinner.service';
import { IdiomaService } from '../service/idioma.service';

@Component({
  selector: 'app-modificar-idioma',
  templateUrl: './modificar-idioma.component.html',
  styleUrls: ['./modificar-idioma.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ModificarIdiomaComponent {
  idiomaForm: FormGroup;
  id:number;
  submitForm: boolean = false;
  nombreIdioma:string;
  constructor(private modalService: ModalService,
              private idiomaService: IdiomaService,
              private router: Router,
              private spinnerServiceGeneral: SpinnerServiceGeneral){}

  ngOnInit(){
    this.idiomaService.getIdioma().subscribe({next: (response)=> this.nombreIdioma = response});
    this.idiomaForm = new FormGroup(
      {
        idioma: new FormControl(this.nombreIdioma,[Validators.required, this.validarLetras])
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
    this.idiomaService.modificarIdioma(this.id,this.idiomaForm.get('idioma').value).subscribe({
      next: (response) => {                    
          this.spinnerServiceGeneral.hideSpinner();
          this.dismissModal();
          Swal.fire({
            position: 'top-end',
            icon: 'success',
            title: 'Idioma actualizado correctamente',
            showConfirmButton: false,
            timer: 2000
          })
          this.reloadPage()
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
