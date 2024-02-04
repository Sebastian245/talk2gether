import { Component } from '@angular/core';
import { UsuarioService } from '../services/usuario.service';
import { ActivatedRoute, Router } from '@angular/router';
import { AbstractControl, FormControl, FormGroup, ValidationErrors, ValidatorFn } from '@angular/forms';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-visualizar-calificacion',
  templateUrl: './visualizar-calificacion.component.html',
  styleUrls: ['./visualizar-calificacion.component.scss']
})
export class VisualizarCalificacionComponent {
  nombreUsuario: string;
  apellidoUsuario: string;
  correo: string;
  nombreRol: string;
  calificaciones: any[] = [];
  fechasForm: FormGroup;
  id: number;
  submitForm: boolean = false;
  constructor(private usuarioService: UsuarioService, private router: ActivatedRoute, private route: Router){}
  ngOnInit(): void{
    this.fechasForm = new FormGroup(
      {
        fechaDesde: new FormControl(''),
        fechaHasta: new FormControl(''),
      },
      { validators: this.validarFechas() }
    );
    this.router.params.subscribe({
      next:(response)=>{
        this.usuarioService.visualizarCalificaciones(response['id']).subscribe({
          next: (r) =>{
            this.id = response['id']
            this.nombreUsuario = r['nombreUsuario']
            this.apellidoUsuario = r['apellidoUsuario']
            this.correo = r['correo']
            this.nombreRol = r['nombreRol']
            this.calificaciones = r['calificaciones']
            
          },
          error: (e) =>{
            Swal.fire({
              icon: 'error',
              title: 'Oops...',
              text: 'Algo salió mal!  No se pudieron cargar las calificaciones.',
            });
          }
        })
      }
    })
  }
  
  redireccionPerfil(idCuenta: number){
    const url = `/perfilUsuario/`+idCuenta;
    window.open(url, '_blank');
  }
  mostrarCalificaciones(){
    this.submitForm = true;
    if (this.fechasForm.invalid) return;
    this.usuarioService.visualizarCalificacionesFiltradas(this.id,this.fechasForm.get('fechaDesde').value,this.fechasForm.get('fechaHasta').value).subscribe({
      next: (r) =>{
        this.nombreUsuario = r['nombreUsuario']
        this.apellidoUsuario = r['apellidoUsuario']
        this.correo = r['correo']
        this.nombreRol = r['nombreRol']
        this.calificaciones = r['calificaciones']
      },
      error: (e) =>{
        Swal.fire({
          icon: 'error',
          title: 'Oops...',
          text: 'Algo salió mal! No se pudieron cargar las calificaciones.',
        });
      }
    });
  }

  validarFechas(): ValidatorFn {
    return (formGroup: AbstractControl): ValidationErrors | null => {
      const fechaDesde = formGroup.get('fechaDesde').value;
      const fechaHasta = formGroup.get('fechaHasta').value;

      if (fechaDesde && !fechaHasta) {
        formGroup.get('fechaHasta').setErrors({ required: true });
        return { required: true };
      }

      if (fechaHasta && !fechaDesde) {
        formGroup.get('fechaDesde').setErrors({ required: true });
        return { required: true };
      }

      if (fechaDesde && fechaHasta && fechaDesde > fechaHasta) {
        formGroup.get('fechaHasta').setErrors({ validarFecha: true });
        return { validarFecha: true };
      }

      formGroup.get('fechaDesde').setErrors(null);
      formGroup.get('fechaHasta').setErrors(null);
      return null;
    };
  }
}
