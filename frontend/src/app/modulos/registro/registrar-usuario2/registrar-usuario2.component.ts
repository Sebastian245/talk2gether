import { Component, EventEmitter, Output } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { InteresesModalComponent } from './intereses-modal/intereses-modal.component';
import { Interes } from './models/Interes';
import { Storage, ref, uploadBytes, getDownloadURL, StorageReference} from '@angular/fire/storage';
import Swal from 'sweetalert2';
import { NuevoUsuario2 } from '../modelos/nuevo-usuario2';
import { RegistroService } from '../servicios/registro.service';
import { ModalService } from '../servicios/modal.service';

@Component({
  selector: 'app-registrar-usuario2',
  templateUrl: './registrar-usuario2.component.html',
  styleUrls: ['./registrar-usuario2.component.scss']
})

export class RegistrarUsuario2Component {
  modalRef: NgbModalRef;
  intereses: Interes[] = [];
  listaPaises = [];
  listaIdiomas = [];
  listaNivelesIdiomas = [];

  paisVacio: boolean = true;
  idiomaNativoVacio: boolean = true;
  idiomaAprendizVacio: boolean = true;
  nivelIdiomaVacio: boolean = true;
  checkBoxDesmarcado: boolean = true;
  registroDeshabilitado: boolean = false;

  nombreUsuario: string;
  apellidoUsuario: string;
  fechaNacimiento: string;
  correo: string;
  contrasenia: string;
  listaIntereses = [];

  urlFoto = '';
  file!: File;
  imgRef!: StorageReference;

  @Output() enviarData2: EventEmitter<any> = new EventEmitter();
  @Output() volverPantalla1: EventEmitter<any> = new EventEmitter();

  constructor(
    private modalService: ModalService,
    private registroService: RegistroService,
    private storage: Storage) {}

  registroForm: FormGroup;

  ngOnInit(): void {
    this.registroService.opcionesPais().subscribe((data: any[]) => {
      this.listaPaises = data;
    });

    this.registroService.opcionesIdiomas().subscribe((data: any[]) => {
      this.listaIdiomas = data;
    });

    this.registroService.opcionesNivelIdiomas().subscribe((data: any[]) => {
      this.listaNivelesIdiomas = data;
    });

    this.registroForm = new FormGroup({
      pais: new FormControl('', [Validators.required]),
      idiomaNativo: new FormControl('', [Validators.required]),
      urlFoto: new FormControl('', [Validators.required]),
      descripcionUsuario: new FormControl('', [Validators.maxLength(200)]),
      terminos: new FormControl('', [Validators.required]),
      idiomaAprendiz: new FormControl('', [Validators.required]),
      nivelIdioma: new FormControl('', [Validators.required]),
      intereses: new FormControl('', [Validators.required])
    }, {
      updateOn: 'change'
    });

    if(sessionStorage.getItem("pais") || sessionStorage.getItem("idiomaNativo") ||
    sessionStorage.getItem("descripcionUsuario") || sessionStorage.getItem("idiomaAprendiz")
    || sessionStorage.getItem("nivelIdioma") || sessionStorage.getItem("intereses")){
      this.registroForm.get('pais').setValue(sessionStorage.getItem("pais"));
      this.registroForm.get('idiomaNativo').setValue(sessionStorage.getItem("idiomaNativo"));
      this.registroForm.get('descripcionUsuario').setValue(sessionStorage.getItem("descripcionUsuario"));
      this.registroForm.get('idiomaAprendiz').setValue(sessionStorage.getItem("idiomaAprendiz"));
      this.registroForm.get('nivelIdioma').setValue(sessionStorage.getItem("nivelIdioma"));
      this.intereses = JSON.parse(sessionStorage.getItem("intereses"));
      this.registroForm.get('intereses').setValue(sessionStorage.getItem("interesesNombres").split(','));
      this.registroForm.get('intereses').markAsTouched();
      this.registroForm.get('intereses').setErrors(null);
    }

  }

  validarPais() {
    this.registroForm.get('pais').markAsTouched();
    const selectPais = document.getElementById('pais') as HTMLSelectElement;
    if (selectPais.value !== '') {
      this.paisVacio = false;
      this.registroForm.get('pais').setErrors(null);
      this.registroForm.get('pais').setValue(selectPais.value);
    } else {
      this.paisVacio = true;
    }
  }

  validarIdiomaNativo() {
    this.registroForm.get('idiomaNativo').markAsTouched();
    const selectIdiomaNativo = document.getElementById('idiomaNativo') as HTMLSelectElement;
    if (selectIdiomaNativo.value !== '') {
      this.idiomaNativoVacio = false;
      this.registroForm.get('idiomaNativo').setErrors(null);
      this.registroForm.get('idiomaNativo').setValue(selectIdiomaNativo.value);
    } else {
      this.idiomaNativoVacio = true;
    }
  }

  validarIdiomaAprendiz() {
    this.registroForm.get('idiomaAprendiz').markAsTouched();
    const selectIdiomaAprendiz = document.getElementById('idiomaAprendiz') as HTMLSelectElement;
    if (selectIdiomaAprendiz.value !== '') {
      this.idiomaAprendizVacio = false;
      this.registroForm.get('idiomaAprendiz').setErrors(null);
      this.registroForm.get('idiomaAprendiz').setValue(selectIdiomaAprendiz.value);
    } else {
      this.idiomaAprendizVacio = true;
    }
  }

  validarNivelIdioma() {
    this.registroForm.get('nivelIdioma').markAsTouched();
    const selectNivelIdioma = document.getElementById('nivelIdioma') as HTMLSelectElement;
    if (selectNivelIdioma.value !== '') {
      this.nivelIdiomaVacio = false;
      this.registroForm.get('nivelIdioma').setErrors(null);
      this.registroForm.get('nivelIdioma').setValue(selectNivelIdioma.value);
    } else {
      this.nivelIdiomaVacio = true;
    }
  }

  modificarCheckbox(){
    this.registroForm.get('terminos').markAsTouched();
    var checkbox = document.getElementById('checkBox');
      if (checkbox instanceof HTMLInputElement && checkbox.checked) {
        this.checkBoxDesmarcado = false;
      } else {
        this.checkBoxDesmarcado = true;
        this.registroForm.get('terminos').setErrors({ required: true });
      }
  }

  popUpNivelIdioma() {
    Swal.fire({
      html: `
        <div class="form-group" style="text-align: left; font-size: 20px; margin-bottom: 25px;">
          <label id="titles" for="name" style="margin-bottom: 5px;"><strong>PRINCIPIANTE</strong></label>
          <p style="text-align: justify; font-size: 20px;">Elige el nivel <strong>Principiante</strong> si apenas estás empezando con el idioma. Puede que no hables una sola palabra, o que sepas algunas cosas básicas. Si estás familiarizado con el <strong>Marco Común Europeo de Referencia para las Lenguas</strong>, esto equivale a los <strong>niveles A1 y A2</strong>.</p>
        </div>

        <div class="form-group" style="text-align: left; font-size: 20px; margin-bottom: 25px;">
          <label id="titles" for="name" style="margin-bottom: 5px;"><strong>INTERMEDIO</strong></label>
          <p style="text-align: justify; font-size: 20px;">Elige el nivel <strong>Intermedio</strong> si hablas el idioma con una <strong>confianza básica</strong>. Puedes hacer planes con otros, hablar sobre tus viajes y describir una película. Si estás familiarizado con el <strong>Marco Común Europeo de Referencia para las Lenguas</strong>, esto equivale al <strong>nivel B1</strong>.</p>
        </div>

        <div class="form-group" style="text-align: left; font-size: 20px;">
          <label id="titles" for="name" style="margin-bottom: 5px;"><strong>AVANZADO</strong></label>
          <p style="text-align: justify; font-size: 20px;">Elige el nivel Avanzado si te sientes <strong>muy seguro hablando el idioma</strong>. Puedes tener conversaciones fluidas con hablantes nativos y utilizar el idioma efectivamente para trabajar y estudiar. Si estás familiarizado con el <strong>Marco Común Europeo de Referencia para las Lenguas</strong>, esto equivale al <strong>nivel B2 y superiores</strong>.</p>
        </div>
      `,
      showCloseButton: true,
      allowEnterKey: false,
      closeButtonHtml: '<i class="fa-regular fa-circle-xmark" style="color: #ffffff; font-size: 25px;"></i>',
      color: '#ffffff',
      background: '#2b6a78',
      width: '40em',
      showConfirmButton: false,
    })
  }

  openModal() {
    this.registroForm.get('intereses').markAsTouched();
    this.listaIntereses = [];
    let modalInstance: any;
    this.modalRef = this.modalService.open(InteresesModalComponent, {
      centered: true
    });
    modalInstance = this.modalRef.componentInstance;
    this.modalRef.dismissed.subscribe(data => {
      this.intereses = modalInstance.interesesSeleccionados;
      if(this.intereses.length > 0){
        modalInstance.interesesPrecargados = this.listaIntereses;
        this.registroForm.get('intereses').setErrors(null);
        for (let interes of this.intereses){
          this.listaIntereses.push(interes.name);
        }
        this.registroForm.get('intereses').setValue(this.listaIntereses);
      }else{
        this.registroForm.get('intereses').setErrors({ required: true });
        this.registroForm.get('intereses').setValue(this.listaIntereses);
      }
    })
  }

  onFileSelected($event: any){
    try{
      this.file = <File>$event.target.files[0];
      this.imgRef = ref(this.storage, `images/${this.file.name}`);
      if(this.file.size > 5242880){
        this.registroForm.get('urlFoto').setErrors({ tamanioInvalido: true });
      }else{
        this.registroForm.get('urlFoto').setErrors(null);
      }
      if(!this.verificarFormato(this.file.name)){
        this.registroForm.get('urlFoto').setErrors({ formato: true });
      }
    }catch{
      Swal.fire({
        position: 'top-end',
        icon: 'error',
        title: 'Debe seleccionar una foto de perfil',
        showConfirmButton: false,
        timer: 3000
      })
    }
  }

  registrarUsuario(){
    this.registroDeshabilitado = true;
      if(this.file != undefined){
        uploadBytes(this.imgRef, this.file)
          .then(async response => {
            this.urlFoto = await getDownloadURL(this.imgRef);
            const infoUsuario = new NuevoUsuario2(
              this.registroForm.get('pais').value,
              this.registroForm.get('idiomaNativo').value,
              this.urlFoto,
              this.registroForm.get('descripcionUsuario').value,
              this.registroForm.get('idiomaAprendiz').value,
              this.registroForm.get('nivelIdioma').value,
              this.registroForm.get('intereses').value
            );
            this.enviarData2.emit(infoUsuario);
          })
          .catch(error => console.log(error));
      }
  }

  verificarFormato(nombreArchivo: string): boolean {
    const extensionesPermitidas = /\.(jpg|jpeg|png)$/i;
    return extensionesPermitidas.test(nombreArchivo);
  }

  volver(){
    sessionStorage.setItem("pais", this.registroForm.get('pais').value);
    sessionStorage.setItem("idiomaNativo", this.registroForm.get('idiomaNativo').value);
    sessionStorage.setItem("descripcionUsuario", this.registroForm.get('descripcionUsuario').value);
    sessionStorage.setItem("idiomaAprendiz", this.registroForm.get('idiomaAprendiz').value);
    sessionStorage.setItem("nivelIdioma", this.registroForm.get('nivelIdioma').value);
    sessionStorage.setItem("intereses", JSON.stringify(this.intereses));
    sessionStorage.setItem("interesesNombres", this.registroForm.get('intereses').value);
    this.volverPantalla1.emit(false);
  }

}