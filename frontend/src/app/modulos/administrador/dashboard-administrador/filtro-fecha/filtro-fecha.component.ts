import { Component, ElementRef, ViewChild, ViewEncapsulation } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { ModalService } from 'src/app/modulos/registro/servicios/modal.service';

@Component({
  selector: 'app-filtro-fecha',
  templateUrl: './filtro-fecha.component.html',
  styleUrls: ['./filtro-fecha.component.scss'],
  encapsulation: ViewEncapsulation.None

})
export class FiltroFechaComponent {
  fechasForm:FormGroup;
  busqueda = {};
  submitForm : boolean = false;

  constructor(private modalService: ModalService){}


  ngOnInit():void{
    this.fechasForm = new FormGroup(
      {
        fechaDesde: new FormControl('',Validators.required),
        fechaHasta: new FormControl('',Validators.required)
      },
      { validators: this.validarFechas() }
    );
  }


  dismissModal() {
    this.modalService.dismissActiveModal();
  }

  filtrar(){
    this.submitForm = true;
    if (this.fechasForm.invalid) return;
    this.busqueda = {
      fechaDesde: this.fechasForm.get('fechaDesde').value,
      fechaHasta: this.fechasForm.get('fechaHasta').value
    }
    this.dismissModal()
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
