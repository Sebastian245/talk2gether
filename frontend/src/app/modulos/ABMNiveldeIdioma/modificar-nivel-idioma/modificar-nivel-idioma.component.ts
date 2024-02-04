import { Component, ViewEncapsulation } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ModalService } from '../../registro/servicios/modal.service';
import { NivelIdiomaService } from '../service/nivel-idioma.service';
import { Router } from '@angular/router';
import { SpinnerServiceGeneral } from 'src/app/compartidos/spinner-carga-general/spinner.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-modificar-nivel-idioma',
  templateUrl: './modificar-nivel-idioma.component.html',
  styleUrls: ['./modificar-nivel-idioma.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ModificarNivelIdiomaComponent {
  nivelIdiomaForm: FormGroup;
  id:number;
  submitForm: boolean = false;
  nombreNivelIdioma:string;
  constructor(private modalService: ModalService,
              private nivelIdiomaService: NivelIdiomaService,
              private router: Router,
              private spinnerServiceGeneral: SpinnerServiceGeneral){}

  ngOnInit(){
    this.nivelIdiomaService.getNivelIdioma().subscribe({next: (response)=> this.nombreNivelIdioma = response});
    this.nivelIdiomaForm = new FormGroup(
      {
        nivelIdioma: new FormControl(this.nombreNivelIdioma,[Validators.required, this.validarLetras])
      },
    );
    
    this.nivelIdiomaService.getId().subscribe({
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
    if (this.nivelIdiomaForm.invalid) return;
    this.spinnerServiceGeneral.showSpinner();
    this.nivelIdiomaService.modificarNivelIdioma(this.id,this.nivelIdiomaForm.get('nivelIdioma').value).subscribe({
      next: (response) => {                    
          this.spinnerServiceGeneral.hideSpinner();
          this.dismissModal();
          Swal.fire({
            position: 'top-end',
            icon: 'success',
            title: 'Nivel de Idioma actualizado correctamente',
            showConfirmButton: false,
            timer: 1500
          })
          this.reloadPage();
      },                 
      error: (error) => {
        if (error.error.mensaje === 'Ya existe un nivel de idioma con el mismo nombre.') {
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

  validarLetras(control) {
    const patron = /^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$/; 
    if (!patron.test(control.value)) {
      return { soloLetras: true };
    }
    return null;
  }

  reloadPage() {
    const currentUrl = this.router.url;
    this.router.navigateByUrl('/administrador', { skipLocationChange: true }).then(() => {
      this.router.navigateByUrl(currentUrl);
    });
  }
}
