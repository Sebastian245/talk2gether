import { Component, ViewEncapsulation } from '@angular/core';
import { ModalService } from '../../registro/servicios/modal.service';
import { Router } from '@angular/router';
import { SpinnerServiceGeneral } from 'src/app/compartidos/spinner-carga-general/spinner.service';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Observable, combineLatest, of, switchMap, tap } from 'rxjs';
import { PermisosService } from '../service/permisos.service';
import { UsuarioService } from '../../ABMUsuario/services/usuario.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-editar-permiso',
  templateUrl: './editar-permiso.component.html',
  styleUrls: ['./editar-permiso.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class EditarPermisoComponent {
  permisosForm: FormGroup;
  submitForm: boolean = false;
  nombreRolList: string[] = [];
  isPermisosCargados$: Observable<any>;

  idRuta: number;
  ruta: string;
  permisosActuales: string[] = [];
  rolesAct: string[] = [];

  constructor(
    private modalService: ModalService,
    private router: Router,
    private spinnerServiceGeneral: SpinnerServiceGeneral,
    private permisoService: PermisosService,
    private usuarioService: UsuarioService
  ) { }

  ngOnInit() {
    this.isPermisosCargados$ = combineLatest([
      this.permisoService.getId(),
      this.permisoService.getNombreRolList(),
      this.permisoService.getRuta(),
      this.usuarioService.traerRoles(),
    ]).pipe(
      switchMap(([id, nombreRolList, ruta, rolesActivos]) => {
        this.idRuta = id;
        this.ruta = ruta;
        this.permisosActuales = nombreRolList;
        const rolesArray = Object.values(rolesActivos);
        for (const rolItem of rolesArray) {
          this.rolesAct.push(rolItem.nombreRol);
        }
        this.permisosForm = new FormGroup({
          roles: new FormControl(this.permisosActuales),
        });

        return of(true);
      })
    );
  }

  enviarDatos() {
    this.submitForm = true;
    if (this.permisosForm.invalid) return;
    Swal.fire({
      title: '¿Seguro que desea actualizar los permisos de "' + this.ruta + '" ?',
      showCancelButton: true,
      reverseButtons: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Actualizar',
      cancelButtonText: 'Cancelar',
    }).then((result) => {
      if (result.isConfirmed) {
        this.spinnerServiceGeneral.showSpinner();
        this.permisoService
          .editarPermiso(this.ruta, this.permisosForm.get('roles').value)
          .subscribe({
            next: (response) => {
              this.spinnerServiceGeneral.hideSpinner();
              this.modalService.dismissActiveModal();
              Swal.fire({
                position: 'top-end',
                icon: 'success',
                title: 'Permiso actualizado correctamente',
                showConfirmButton: false,
                timer: 2000,
              });
              this.reloadPage();
            },
            error: (error) => {
              this.spinnerServiceGeneral.hideSpinner();

              Swal.fire({
                icon: 'error',
                title: 'Oops...',
                text: 'Algo salió mal! Intente nuevamente mas tarde.',
              });
            },
          });
      }
    });


  }

  reloadPage() {
    const currentUrl = this.router.url;
    this.router.navigateByUrl('/administrador', { skipLocationChange: true }).then(() => {
      this.router.navigateByUrl(currentUrl);
    });
  }
  dismissModal() {
    this.modalService.dismissActiveModal();
  }
}
