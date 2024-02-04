import { Component, ViewEncapsulation } from '@angular/core';
import { ModalService } from '../../registro/servicios/modal.service';
import { FormGroup, Validators } from '@angular/forms';
import { BuscarUsuariosService } from '../../inicio/servicios/buscar-usuarios.service';
import { CalificarUsuarioService } from '../services/calificar-usuario.service';
import { SpinnerServiceGeneral } from 'src/app/compartidos/spinner-carga-general/spinner.service';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';

@Component({
  selector: 'app-calificar-modal',
  templateUrl: './calificar-modal.component.html',
  styleUrls: ['./calificar-modal.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class CalificarModalComponent {
    
  selectedRating: number = 0;
  submitForm: boolean = false;
  constructor(private modalService: ModalService,private buscarUsuarioService: BuscarUsuariosService, 
    private calificarService: CalificarUsuarioService, private spinner: SpinnerServiceGeneral ,private route:Router){}
  
  puntosForm: FormGroup;
  nombreApellido: string;
  idCalificador: number;
  idParticipante2: number;
  idReunion: number;
  ngOnInit(){
    this.buscarUsuarioService.getOtroUsuario().subscribe({
      next: (response)=>{
        this.nombreApellido = response['nombreUsuarioCalificado']+" "+response['apellidoUsuarioCalificado']
        this.idCalificador = response['idCalificador']
        this.idParticipante2 = response['idCalificado']
        this.idReunion = response['idReunionVirtual']
      }
    })
  }

  dismissModal() {
    this.modalService.dismissActiveModal();
  }
  
  dirigirPerfil(){
    const url = `/perfilUsuario/`+this.idParticipante2;
    window.open(url, '_blank');
  }

  onRatingSelected(value:number){
    this.selectedRating = value;
    
  }

  calificar(){
    if (this.selectedRating == 0){
      this.submitForm = true;
    }else{
      this.submitForm = false;
      this.spinner.showSpinner()
      this.calificarService.calificarUsuario(this.idCalificador,this.idParticipante2,this.idReunion, this.selectedRating).subscribe({
        next:(response)=>{
          this.spinner.hideSpinner()
          this.modalService.dismissActiveModal()
          const Toast = Swal.mixin({
            toast: true,
            position: 'top-end',
            showConfirmButton: false,
            timer: 3000,
            timerProgressBar: true,
            didOpen: (toast) => {
              toast.addEventListener('mouseenter', Swal.stopTimer)
              toast.addEventListener('mouseleave', Swal.resumeTimer)
            }
          })
          Toast.fire({
            icon: 'success',
            title: 'Aprendiz calificado.'
          })
        },
        error:(err)=>{
          const Toast = Swal.mixin({
            toast: true,
            position: 'top-end',
            showConfirmButton: false,
            timer: 5000,
            timerProgressBar: true,
            didOpen: (toast) => {
              toast.addEventListener('mouseenter', Swal.stopTimer)
              toast.addEventListener('mouseleave', Swal.resumeTimer)
            }
          })
          Toast.fire({
            icon: 'error',
            title: 'Error al puntuar.'
          })
          this.spinner.hideSpinner()
          this.modalService.dismissActiveModal();
        }
      })
    }
  }
}