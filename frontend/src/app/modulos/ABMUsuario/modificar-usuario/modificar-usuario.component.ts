import { Component, ViewEncapsulation } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, ValidatorFn, Validators } from '@angular/forms';
import Swal from 'sweetalert2';
import { ModalService } from '../../registro/servicios/modal.service';
import { IdiomaService } from '../../ABMIdioma/service/idioma.service';
import { Router } from '@angular/router';
import { SpinnerServiceGeneral } from 'src/app/compartidos/spinner-carga-general/spinner.service';
import { UsuarioService } from '../services/usuario.service';
import {
  EMPTY,
  Observable,
  combineLatest,
  map,
  of,
  switchMap,
  tap,
} from 'rxjs';
import { NavbarService } from 'src/app/compartidos/navbar/service/navbar.service';

@Component({
  selector: 'app-modificar-usuario',
  templateUrl: './modificar-usuario.component.html',
  styleUrls: ['./modificar-usuario.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class ModificarUsuarioComponent {
  rolForm: FormGroup;
  submitForm: boolean = false;
  nombreIdioma: string;
  rolesList: string[] = [];

  isUser$: Observable<any>;

  id: number;
  nombreApellido: string;
  correo: string;
  nombreRol: string;

  constructor(
    private modalService: ModalService,
    private idiomaService: IdiomaService,
    private router: Router,
    private spinnerServiceGeneral: SpinnerServiceGeneral,
    private usuarioService: UsuarioService,
    private navbarService: NavbarService,
  ) {}

  ngOnInit() {
    this.rolForm = new FormGroup(
      {
        rol: new FormControl('', [Validators.required, this.customRoleValidator()])
      },
    );
    this.isUser$ = combineLatest([
      this.usuarioService.getId(),
      this.usuarioService.getNombreApellido(),
      this.usuarioService.getcorreo(),
      this.usuarioService.getNombreRol(),
      this.usuarioService.traerRoles(),
    ]).pipe(
      map(([id, nombreApellido, correo, rol, roles]) => {
        this.spinnerServiceGeneral.showSpinner();
        const rolesArray = Object.values(roles);
        for (const rolItem of rolesArray) {
          this.rolesList.push(rolItem.nombreRol);
        }
        this.id = id;
        this.nombreApellido = nombreApellido;
        this.correo = correo;
        this.nombreRol = rol;
        return true;
      }),
      tap(() => this.spinnerServiceGeneral.hideSpinner())
    );
  }
  
  customRoleValidator(): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any } | null => {
      const selectedRol = control.value;
      if (selectedRol === this.nombreRol) {
        return { 'rolIgualnombre': true };
      }
      return null;
    };
  }

  dismissModal() {
    this.modalService.dismissActiveModal();
  }

  enviarDatos() {
    this.submitForm = true;
    if (this.rolForm.invalid) return;
    this.spinnerServiceGeneral.showSpinner();
    this.usuarioService
      .modificarUsuario(this.id, this.rolForm.get('rol').value)
      .subscribe({
        next: (response) => {
          this.spinnerServiceGeneral.hideSpinner();
          this.dismissModal();
          Swal.fire({
            position: 'top-end',
            icon: 'success',
            title: 'Usuario actualizado correctamente',
            showConfirmButton: false,
            timer: 2000,
          });
          this.reloadPage();
        },
        error: (error) => {
          Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: 'Algo salió mal! Intente nuevamente mas tarde.',
          });
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
    const patron = /^[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ ]+$/;
    if (!patron.test(control.value)) {
      return { soloLetras: true };
    }
    return null;
  }
}
