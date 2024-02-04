import { Component, EventEmitter, Output } from '@angular/core';
import { Router } from '@angular/router';
import { CometChat } from '@cometchat-pro/chat';
import { COMETCHAT_CONSTANTS } from 'src/CONSTS';
import { NavbarService } from 'src/app/compartidos/navbar/service/navbar.service';
import { SpinnerServiceGeneral } from 'src/app/compartidos/spinner-carga-general/spinner.service';
import Swal from 'sweetalert2';
import { ChatService } from '../services/chat-service.service';

@Component({
  selector: 'app-login-chat',
  templateUrl: './login-chat.component.html',
  styleUrls: ['./login-chat.component.scss']
})
export class LoginChatComponent {
  onLoginError: boolean = false;
  
  @Output() dataLogin: EventEmitter<boolean> = new EventEmitter();
  @Output() dataLoginAbierto: EventEmitter<boolean> = new EventEmitter();
  errorMsg: string = "";
  uid:string;
  userslist: any[];
  constructor(private router:Router,
              private actualUsuario: NavbarService,
              private chatService: ChatService,
              private spinner: SpinnerServiceGeneral){}

  ngOnInit(){
    this.spinner.showSpinner();
    this.chatService.consultarListaUsuario().subscribe({
      next: (response)=>{
        this.userslist = response['data'];
        if(this.userslist.length <=25){
          this.actualUsuario.traerUsuario().subscribe({
            next: (response)=>{
              this.uid = response['id'];
              this.chatService.consultarDatos(this.uid).subscribe({
                next:(responseDatos)=>{
                  this.chatService.consultarUsuario(this.uid).subscribe(
                    {
                      next: (usuario) =>{
                        this.onLogin();
                        this.spinner.hideSpinner()
                      },
                      error: (usuarioError)=>{
                        const id = responseDatos['id'];
                        const nombreCompleto = responseDatos['nombreUsuario']+" "+responseDatos['apellidoUsuario'];
                        const urlFoto = responseDatos['urlFoto']
                        this.chatService.registroChat(id,nombreCompleto,urlFoto).subscribe({
                          next: (usuarioCreado) =>{

                              this.spinner.hideSpinner()
                              this.reloadPage();
                          },
                          error: (error)=>{
                            this.spinner.hideSpinner()
                            Swal.fire({
                              icon: 'error',
                              title: 'Oops...',
                              text: 'Algo salió mal! Reinicie la página o intente nuevamente mas tarde.',
                            });
                          },
                        })
                      }
                    }
                  )
                },
                error:(error)=>{
                  this.spinner.hideSpinner()
                  Swal.fire({
                    icon: 'error',
                    title: 'Oops...',
                    text: 'Algo salió mal! Reinicie la página o intente nuevamente mas tarde.',
                  });
                }
              })
            },
            error: (error)=>{
              this.spinner.hideSpinner()
              Swal.fire({
                icon: 'error',
                title: 'Oops...',
                text: 'Algo salió mal! Reinicie la página o intente nuevamente mas tarde.',
              });
            }
          })
        } else {
          this.spinner.hideSpinner()
          Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: 'El chat se encuentra deshabilitado momentáneamente. Intente mas tarde',
          });

        }
      },
      error: (errorLista)=>{
        this.spinner.hideSpinner()
        Swal.fire({
          icon: 'error',
          title: 'Oops...',
          text: 'Algo salió mal! Reinicie la página o intente nuevamente mas tarde.',
        });
      }
    })
  }

  onLogin() {
    CometChat.login(this.uid, COMETCHAT_CONSTANTS.AUTH_KEY).then(
      (user) => {
        // console.log("Login Successful:", { user });
        this.dataLogin.emit(true);
        this.dataLoginAbierto.emit(true)
      },
      (error) => {
        Swal.fire({
          icon: 'error',
          title: 'Oops...',
          text: 'Algo salió mal! Reinicie la página o intente nuevamente mas tarde.',
        });
        this.dataLogin.emit(false);
        this.dataLoginAbierto.emit(false)
        // console.log("Login failed with exception:", { error });
        this.onLoginError = true;
        this.errorMsg = error.message;
      }
    );
  }
  reloadPage() {
    const currentUrl = this.router.url;
    this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
      this.router.navigateByUrl(currentUrl);
    });
  }
}
