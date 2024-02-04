import { Component, ViewEncapsulation } from '@angular/core';
import { ModalService } from '../../registro/servicios/modal.service';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { NivelIdiomaService } from '../service/nivel-idioma.service';
import { Router } from '@angular/router';
import { SpinnerServiceGeneral } from 'src/app/compartidos/spinner-carga-general/spinner.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-aniadir-nivel-idioma',
  templateUrl: './aniadir-nivel-idioma.component.html',
  styleUrls: ['./aniadir-nivel-idioma.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class AniadirNivelIdiomaComponent {
  nivelIdiomaForm: FormGroup;
  id:number;
  submitForm: boolean = false;
  constructor(private modalService: ModalService,
              private nivelIdiomaService: NivelIdiomaService,
              private router: Router,
              private spinnerServiceGeneral: SpinnerServiceGeneral){}

  ngOnInit(){
    this.nivelIdiomaForm = new FormGroup(
      {
        nivelIdioma: new FormControl('',[Validators.required, this.validarLetras])
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
    this.nivelIdiomaService.agregarNivelIdioma(this.nivelIdiomaForm.get('nivelIdioma').value).subscribe({
      next: (response) => {                    
          this.spinnerServiceGeneral.hideSpinner();
          this.dismissModal();
          this.reloadPage();
          Swal.fire({
            position: 'top-end',
            icon: 'success',
            title: 'Nivel de idioma creado correctamente',
            showConfirmButton: false,
            timer: 1500
          })
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
