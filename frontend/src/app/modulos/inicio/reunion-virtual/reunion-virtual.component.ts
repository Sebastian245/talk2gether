import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { BuscarUsuariosService } from '../servicios/buscar-usuarios.service';
import { NavbarService } from 'src/app/compartidos/navbar/service/navbar.service';
import Swal from 'sweetalert2';
import { SpinnerServiceGeneral } from 'src/app/compartidos/spinner-carga-general/spinner.service';
import { Router } from '@angular/router';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ModalService } from '../../registro/servicios/modal.service';
import { CalificarModalComponent } from '../../calificar-usuario/calificar-modal/calificar-modal.component';
import { UsuarioCalificar } from '../modelos/usuario-calificar';
declare var JitsiMeetExternalAPI: any;

@Component({
  selector: 'app-reunion-virtual',
  templateUrl: './reunion-virtual.component.html',
  styleUrls: ['./reunion-virtual.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ReunionVirtualComponent implements OnInit {
  modalRef: NgbModalRef;
  linkSala: string;
  nombreUsuario: string;
  correoUsuario: string;
  idUsuario: number;
  urlFoto: string;

  domain: string = 'proyectofinal.website';
  options: any;
  api: any | undefined;

  isAudioMuted = false;
  isVideoMuted = false;
  bandera: boolean = true;
  constructor(
    private buscarUsuariosServicio: BuscarUsuariosService,
    private spinner: SpinnerServiceGeneral,
    private navbarService: NavbarService,
    private router: Router,
    private modalService: ModalService
    ) {}

    ngOnInit(){
      this.spinner.showSpinner();
      this.navbarService.traerUsuario().subscribe(
        {
          next: response => {
            this.idUsuario = response['id'];
            this.correoUsuario = response['correo'];
            this.buscarUsuariosServicio.consultarDatos(this.idUsuario).subscribe((data: any) =>{
              this.nombreUsuario = data.nombreUsuario + ' ' + data.apellidoUsuario;
              this.urlFoto = data.urlFoto;
              this.buscarUsuariosServicio.getLink().subscribe((data: any) =>{
                if (data == null){
                  this.router.navigate(['/inicio']);
                  this.buscarUsuariosServicio.finalizarVideollamadaID(this.idUsuario).subscribe({
                    next: response => {
                      console.log("Videollamada finalizada");
                    },
                    error: error => {
                      console.log("No se encuentra en una reunión virtual");
                    }
                  });
                }else{
                  this.linkSala = data;
                  this.cargarReunion();
                }
              });
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

    cargarReunion(): void {
      this.options = {
          roomName: this.linkSala,
          configOverwrite: { prejoinPageEnabled: false },
          parentNode: document.querySelector('#meet'),
          width: '100%',
          height: '100%',
          userInfo: {
              email: this.correoUsuario,
              displayName: this.nombreUsuario
          }
      }
      this.api = new JitsiMeetExternalAPI(this.domain, this.options);
      this.api.executeCommand('avatarUrl', this.urlFoto);
      this.api.addEventListeners({
        readyToClose: this.handleClose,
        participantLeft: this.handleParticipantLeft,
        participantJoined: this.handleParticipantJoined,
        videoConferenceJoined: this.handleVideoConferenceJoined,
        videoConferenceLeft: this.handleVideoConferenceLeft,
        audioMuteStatusChanged: this.handleMuteStatus,
        videoMuteStatusChanged: this.handleVideoStatus
      });
      this.spinner.hideSpinner();
    }

    handleClose = () => {
      console.log("handleClose");
    }

  handleParticipantLeft = async (participant: any) => {
    console.log("handleParticipantLeft", participant); // { id: "2baa184e" }
    const data = await this.getParticipants();
    console.log(data);
    this.api.executeCommand("hangup");
    }

  handleParticipantJoined = async (participant: any) => {
      console.log("handleParticipantJoined", participant); // { id: "2baa184e", displayName: "Shanu Verma", formattedDisplayName: "Shanu Verma" }
      const data = await this.getParticipants();
  }

  handleVideoConferenceJoined = async (participant: any) => {
      console.log("handleVideoConferenceJoined", participant); // { roomName: "bwb-bfqi-vmh", id: "8c35a951", displayName: "Akash Verma", formattedDisplayName: "Akash Verma (me)"}
      const data = await this.getParticipants();
  }

  handleVideoConferenceLeft = () => {
    console.log("handleVideoConferenceLeft");
    //Se ejecuta cuando se sale el otro
    if(this.bandera==true){
      this.bandera = false;
      this.buscarUsuariosServicio.reunionOtroParticipante(this.idUsuario,this.linkSala).subscribe({
        next:(response)=>{
          const user: UsuarioCalificar = {
            "nombreUsuarioCalificado": response['nombreParticipante'],
            "apellidoUsuarioCalificado": response['apellidoParticipante'],
            "idCalificado": response['idParticipanteDos'],
            "idCalificador": this.idUsuario,
            "idReunionVirtual": response['idReunion'],
          }
          this.buscarUsuariosServicio.guardarOtroUsuario(user)
          this.modalRef = this.modalService.open(CalificarModalComponent, {
            centered: true,
          });
        },
        error:(error)=>{
          console.log(error)
        }
      })
    }
    this.router.navigate(['/inicio']);
  }

  handleMuteStatus = (audio: any) => {
      console.log("handleMuteStatus", audio); // { muted: true }
  }

  handleVideoStatus = (video: any) => {
      console.log("handleVideoStatus", video); // { muted: true }
  }

  getParticipants() {
      return new Promise((resolve, reject) => {
          setTimeout(() => {
              resolve(this.api.getParticipantsInfo()); // get all participants
          }, 500)
      });
  }

  executeCommand(command: string) {
    this.api.executeCommand(command);;
    if (command == 'hangup' && this.bandera==true) {

      this.buscarUsuariosServicio.finalizarVideollamada(this.linkSala).subscribe(()=>{
        //Ejecuta cuando me salgo yo
        console.log("Se actualizo la base de datos");
        this.buscarUsuariosServicio.reunionOtroParticipante(this.idUsuario,this.linkSala).subscribe({
          next:(response)=>{
            const user: UsuarioCalificar = {
              "nombreUsuarioCalificado": response['nombreParticipante'],
              "apellidoUsuarioCalificado": response['apellidoParticipante'],
              "idCalificado": response['idParticipanteDos'],
              "idCalificador": this.idUsuario,
              "idReunionVirtual": response['idReunion'],
            }
            this.buscarUsuariosServicio.guardarOtroUsuario(user)
            this.modalRef = this.modalService.open(CalificarModalComponent, {
              centered: true,
            });
            this.router.navigate(['/inicio']);
          },
          error:(error)=>{
            console.log(error)
          }
        }).unsubscribe()
      });

    return;
    }

    if (command == 'toggleAudio') {
      this.isAudioMuted = !this.isAudioMuted;
    }
    if (command == 'toggleVideo') {
      this.isVideoMuted = !this.isVideoMuted;
    }
  }

}
