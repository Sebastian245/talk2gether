import { Component, ViewEncapsulation } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import {
  EMPTY,
  Observable,
  catchError,
  combineLatest,
  finalize,
  map,
  of,
  switchMap,
  tap,
} from 'rxjs';
import { ModalService } from '../../registro/servicios/modal.service';

import { Router } from '@angular/router';
import { SpinnerServiceGeneral } from 'src/app/compartidos/spinner-carga-general/spinner.service';
import { UsuarioService } from '../services/usuario.service';
import Swal from 'sweetalert2';
import { NavbarService } from 'src/app/compartidos/navbar/service/navbar.service';
import { ChatService } from '../../chat/services/chat-service.service';

@Component({
  selector: 'app-baja-usuario',
  templateUrl: './baja-usuario.component.html',
  styleUrls: ['./baja-usuario.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class BajaUsuarioComponent {
  bajaForm: FormGroup;
  submitForm: boolean = false;
  nombreIdioma: string;
  motivoList: string[] = [];
  selectedMotivos: string[] = [];
  isUserEliminar$: Observable<any>;
  idAdmin: number;

  id: number;
  nombreApellido: string;
  correo: string;
  nombreRol: string;

  constructor(
    private modalService: ModalService,

    private router: Router,
    private spinnerServiceGeneral: SpinnerServiceGeneral,
    private usuarioService: UsuarioService,
    private navbarService: NavbarService,
    private chatService: ChatService
  ) {}

  ngOnInit() {
    this.navbarService.getIdLogueado().subscribe({
      next: (response) => {
        this.idAdmin = parseInt(response);
      },
    });
    this.bajaForm = new FormGroup({
      descripcion: new FormControl('', Validators.required),
      selectedMotivos: new FormControl([], Validators.required),
    });
    this.isUserEliminar$ = combineLatest([
      this.usuarioService.getId(),
      this.usuarioService.getNombreApellido(),
      this.usuarioService.getcorreo(),
      this.usuarioService.traerMotivos(),
    ]).pipe(
      switchMap(([id, nombreApellido, correo, motivos]) => {
        this.spinnerServiceGeneral.showSpinner();
        for (let i = 0; i < motivos['listaNombreMotivo'].length; i++) {
          const motivo = motivos['listaNombreMotivo'][i];
          this.motivoList.push(motivo);
        }

        this.id = id;
        this.nombreApellido = nombreApellido;
        this.correo = correo;
        return of(true);
      }),
      tap(() => this.spinnerServiceGeneral.hideSpinner())
    );
  }

  dismissModal() {
    this.modalService.dismissActiveModal();
  }

  enviarDatos() {
    this.submitForm = true;
    if (this.bajaForm.invalid) return;
    this.spinnerServiceGeneral.showSpinner();
    const bajaUsuario$ = this.usuarioService.bajaUsuario(
      this.id,
      this.idAdmin,
      this.bajaForm.get('selectedMotivos').value,
      this.bajaForm.get('descripcion').value
    );
    const eliminarUsuarioChat$ = this.chatService.eliminarUsuario(this.id).pipe(
      catchError((error) => {
        if (error.status === 404) {
          console.log(
            'Usuario no encontrado en el chat, pero se manejará como éxito'
          );
          return of({
            success: true,
            message: 'Usuario no encontrado en el chat',
          });
        }
        throw error;
      })
    );

    combineLatest([bajaUsuario$, eliminarUsuarioChat$])
      .pipe(tap(() => this.spinnerServiceGeneral.hideSpinner()))
      .subscribe({
        next: ([bajaUsuarioResponse, eliminarUsuarioChatResponse]) => {
          console.log('Respuesta de bajaUsuario:', bajaUsuarioResponse);
          console.log(
            'Respuesta de eliminarUsuarioChat:',
            eliminarUsuarioChatResponse
          );
          this.modalService.dismissActiveModal();
          this.spinnerServiceGeneral.hideSpinner();
          this.reloadPage();
          Swal.fire({
            position: 'top-end',
            icon: 'success',
            title: 'La cuenta ha sido eliminada exitosamente.',
            showConfirmButton: false,
            timer: 2000,
          });
        },
        error: (error) => {
          this.modalService.dismissActiveModal();
          this.spinnerServiceGeneral.hideSpinner();
          Swal.fire({
            icon: 'error',
            title: 'Oops...',
            confirmButtonColor: '#199DE8',
            text: 'Algo salió mal! Reinicie la página. Si el error persiste intente mas tarde.',
          });
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
    const patron = /^[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ ]+$/;
    if (!patron.test(control.value)) {
      return { soloLetras: true };
    }
    return null;
  }
}
