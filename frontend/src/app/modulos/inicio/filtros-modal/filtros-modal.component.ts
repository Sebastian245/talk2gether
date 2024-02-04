import { Component, ViewEncapsulation } from '@angular/core';
import { ModalService } from '../../registro/servicios/modal.service';
import { RegistroService } from '../../registro/servicios/registro.service';

@Component({
  selector: 'app-filtros-modal',
  templateUrl: './filtros-modal.component.html',
  styleUrls: ['./filtros-modal.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class FiltrosModalComponent {

  listaPaises = [];
  listaNivelesIdiomas = [];
  listaIntereses = [];
  idiomaUsuario: string;
  edades: number[] = [];
  busqueda = {}

  constructor(
    private modalService: ModalService,
    private parametrosService: RegistroService){
      for (let i = 18; i <= 100; i++) {
        this.edades.push(i);
      }
     }

  ngOnInit(): void {
    this.parametrosService.opcionesPais().subscribe((data: any[]) => {
      this.listaPaises = data;
    });

    this.parametrosService.opcionesNivelIdiomas().subscribe((data: any[]) => {
      this.listaNivelesIdiomas = data;
    });

    this.parametrosService.obtenerIntereses().subscribe((data: any[]) => {
      this.listaIntereses = data.map(diccionario => diccionario.name);
    });
  }

  dismissModal() {
    this.modalService.dismissActiveModal();
  }

  realizarBusqueda(){
    const nivelMinimo = document.getElementById("nivel-minimo") as HTMLSelectElement;
    const pais = document.getElementById("pais") as HTMLSelectElement;
    const edadDesde = document.getElementById("edad-desde") as HTMLSelectElement;
    const edadHasta = document.getElementById("edad-hasta") as HTMLSelectElement;
    this.busqueda = {
      nivelMinimo: nivelMinimo.value,
      pais: pais.value,
      edadDesde: edadDesde.value,
      edadHasta: edadHasta.value,
      intereses: this.obtenerCheckboxMarcados()
    }
    this.dismissModal();
  }

  obtenerCheckboxMarcados() {
    const intereses = [];
    const checkboxes = document.querySelectorAll('input[type="checkbox"]');
    checkboxes.forEach(checkbox =>{
      var check = checkbox as HTMLInputElement
      if(check.checked){
        intereses.push(check.value);
      }
    })
    return intereses.join(',');
  }

}
