import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { Storage, ref, uploadBytes, getDownloadURL, StorageReference} from '@angular/fire/storage';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ModalService } from '../../registro/servicios/modal.service';
import { Interes } from '../../registro/registrar-usuario2/models/Interes';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { InteresesModalComponent } from '../../registro/registrar-usuario2/intereses-modal/intereses-modal.component';
import Swal from 'sweetalert2';
import { RegistroService } from '../../registro/servicios/registro.service';
import { Router } from '@angular/router';
import { EditarUsuarioService } from '../servicios/editar-usuario.service';
import { EditarUsuario } from '../modelos/editar-usuario';
import { lastValueFrom } from 'rxjs';
import { ChatService } from '../../chat/services/chat-service.service';
import { SpinnerServiceGeneral } from 'src/app/compartidos/spinner-carga-general/spinner.service';

@Component({
  selector: 'app-editar-perfil',
  templateUrl: './editar-perfil.component.html',
  styleUrls: ['./editar-perfil.component.scss']
})
export class EditarPerfilComponent implements OnInit{

  editarUsuarioForm: FormGroup;
  datosUsuario: any;
  idUsuario: number;
  usuarioEditado: EditarUsuario;
  urlFoto = null;
  imageSrc: string;
  file!: File;
  imgRef!: StorageReference;
  modalRef: NgbModalRef;
  intereses: Interes[] = [];
  listaPaises = [];
  listaIdiomas = [];
  listaNivelesIdiomas = [];
  listaIntereses = [];
  fechaVacia: boolean = true;

  paisVacio: boolean;
  idiomaNativoVacio: boolean;
  idiomaAprendizVacio: boolean;
  nivelIdiomaVacio: boolean;

  @Output() volverPantalla: EventEmitter<any> = new EventEmitter();

  constructor(
    private modalService: ModalService,
    private registroService: RegistroService,
    private editarUsuarioService: EditarUsuarioService,
    private chatService: ChatService,
    private spinnerServiceGeneral: SpinnerServiceGeneral,
    private storage: Storage,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.editarUsuarioService.getInformacion().subscribe((data: any) =>{
      this.datosUsuario = data;

      this.registroService.opcionesPais().subscribe((data: any[]) => {
        this.listaPaises = data;
        if (!this.listaPaises.includes(this.datosUsuario.nombrePais)) {
          this.paisVacio = true;
        }
      });

      this.registroService.opcionesIdiomas().subscribe((data: any[]) => {
        this.listaIdiomas = data;

        if (!this.listaIdiomas.includes(this.datosUsuario.nombreIdiomaNativo)) {
          this.idiomaNativoVacio = true;
        }

        if (!this.listaIdiomas.includes(this.datosUsuario.nombreIdiomaAprender)) {
          this.idiomaAprendizVacio = true;
        }
      });

      this.registroService.opcionesNivelIdiomas().subscribe((data: any[]) => {
        this.listaNivelesIdiomas = data;
        if (!this.listaNivelesIdiomas.includes(this.datosUsuario.nombreNivelIdioma)) {
          this.nivelIdiomaVacio = true;
        }
      });
    });

    this.editarUsuarioService.getIdCuenta().subscribe((data: any) =>{
      this.idUsuario = data;
    });

    this.editarUsuarioForm = new FormGroup({
      urlFoto: new FormControl(''),
      nombreUsuario: new FormControl('', [Validators.required, Validators.pattern(/^[A-Za-zÁÉÍÓÚáéíóúÑñÜü]+$/)]),
      apellidoUsuario: new FormControl('', [Validators.required, Validators.pattern(/^[A-Za-zÁÉÍÓÚáéíóúÑñÜü]+$/)]),
      descripcionUsuario: new FormControl('', [Validators.maxLength(200)]),
      fechaNacimiento: new FormControl(''),
      pais: new FormControl('default'),
      intereses: new FormControl('', [Validators.required]),
      idiomaNativo: new FormControl('default'),
      idiomaAprendiz: new FormControl('default'),
      nivelIdioma: new FormControl('default'),
    }, {
      updateOn: 'change'
    });

    const nacimiento = document.getElementById('nacimiento') as HTMLInputElement;
    let today = new Date();
    let minAge = 18;
    nacimiento.max = new Date(today.getFullYear() - minAge, today.getMonth(), today.getDate()).toISOString().split("T")[0];

    const inputFecha = document.getElementById("nacimiento") as HTMLInputElement;
    inputFecha.value = this.datosUsuario.fechaNacimiento;

    this.editarUsuarioForm.get('nombreUsuario').setValue(this.datosUsuario.nombreUsuario);
    this.editarUsuarioForm.get('apellidoUsuario').setValue(this.datosUsuario.apellidoUsuario);
    this.editarUsuarioForm.get('descripcionUsuario').setValue(this.datosUsuario.descripcion);
    this.editarUsuarioForm.get('fechaNacimiento').setValue(inputFecha.value);
    this.editarUsuarioForm.get('pais').setValue(this.datosUsuario.nombrePais);
    this.editarUsuarioForm.get('idiomaNativo').setValue(this.datosUsuario.nombreIdiomaNativo);
    this.editarUsuarioForm.get('idiomaAprendiz').setValue(this.datosUsuario.nombreIdiomaAprender);
    this.editarUsuarioForm.get('nivelIdioma').setValue(this.datosUsuario.nombreNivelIdioma);

    this.intereses = JSON.parse(JSON.stringify(this.datosUsuario.listaIntereses));
    this.editarUsuarioForm.get('intereses').setValue(this.datosUsuario.nombresIntereses.split(', '));
    this.editarUsuarioForm.get('intereses').markAsTouched();
    this.editarUsuarioForm.get('intereses').setErrors(null);
  }

  validarPais() {
    this.editarUsuarioForm.get('pais').markAsTouched();
    const selectPais = document.getElementById('pais') as HTMLSelectElement;
    if (selectPais.value !== 'default' && selectPais.value !== '') {
      this.paisVacio = false;
      this.editarUsuarioForm.get('pais').setErrors(null);
      this.editarUsuarioForm.get('pais').setValue(selectPais.value);
    } else {
      this.paisVacio = true;
    }
  }

  validarIdiomaNativo() {
    this.editarUsuarioForm.get('idiomaNativo').markAsTouched();
    const selectIdiomaNativo = document.getElementById('idiomaNativo') as HTMLSelectElement;
    if (selectIdiomaNativo.value !== 'default' && selectIdiomaNativo.value !== '') {
      this.idiomaNativoVacio = false;
      this.editarUsuarioForm.get('idiomaNativo').setErrors(null);
      this.editarUsuarioForm.get('idiomaNativo').setValue(selectIdiomaNativo.value);
    } else {
      this.idiomaNativoVacio = true;
    }
  }

  validarIdiomaAprendiz() {
    this.editarUsuarioForm.get('idiomaAprendiz').markAsTouched();
    const selectIdiomaAprendiz = document.getElementById('idiomaAprendiz') as HTMLSelectElement;
    if (selectIdiomaAprendiz.value !== 'default' && selectIdiomaAprendiz.value !== '') {
      this.idiomaAprendizVacio = false;
      this.editarUsuarioForm.get('idiomaAprendiz').setErrors(null);
      this.editarUsuarioForm.get('idiomaAprendiz').setValue(selectIdiomaAprendiz.value);
    } else {
      this.idiomaAprendizVacio = true;
    }
  }

  validarNivelIdioma() {
    this.editarUsuarioForm.get('nivelIdioma').markAsTouched();
    const selectNivelIdioma = document.getElementById('nivelIdioma') as HTMLSelectElement;
    if (selectNivelIdioma.value !== 'default' && selectNivelIdioma.value !== '') {
      this.nivelIdiomaVacio = false;
      this.editarUsuarioForm.get('nivelIdioma').setErrors(null);
      this.editarUsuarioForm.get('nivelIdioma').setValue(selectNivelIdioma.value);
    } else {
      this.nivelIdiomaVacio = true;
    }
  }

  openModal() {
    this.editarUsuarioForm.get('intereses').markAsTouched();
    this.editarUsuarioForm.markAsDirty();
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
        this.editarUsuarioForm.get('intereses').setErrors(null);
        for (let interes of this.intereses){
          this.listaIntereses.push(interes.name);
        }
        this.editarUsuarioForm.get('intereses').setValue(this.listaIntereses);
      }else{
        this.editarUsuarioForm.get('intereses').setErrors({ required: true });
        this.editarUsuarioForm.get('intereses').setValue(this.listaIntereses);
      }
    })
  }

  onFileSelected($event: any){
    try{
      this.file = <File>$event.target.files[0];
      if (this.file) {
        const reader = new FileReader();
        reader.onload = e => this.imageSrc = reader.result as string;
        reader.readAsDataURL(this.file);
      }
      this.imgRef = ref(this.storage, `images/${this.file.name}`);
      if(this.file.size > 5242880){
        this.editarUsuarioForm.get('urlFoto').setErrors({ tamanioInvalido: true });
      }else{
        this.editarUsuarioForm.get('urlFoto').setErrors(null);
      }
      if(!this.verificarFormato(this.file.name)){
        this.editarUsuarioForm.get('urlFoto').setErrors({ formato: true });
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

  elegirFecha() {
    this.editarUsuarioForm.get('fechaNacimiento').markAsTouched();
    this.editarUsuarioForm.get('fechaNacimiento').markAsDirty();
    const inputFecha = document.getElementById("nacimiento") as HTMLInputElement;
    inputFecha.oninput = (event) => {
      const fechaSeleccionada = inputFecha.value;
      if (fechaSeleccionada != '') {
        this.fechaVacia = false;
        this.editarUsuarioForm.get('fechaNacimiento').setErrors(null);
        this.editarUsuarioForm.get('fechaNacimiento').setValue(inputFecha.value);
      } else {
        this.fechaVacia = true;
        this.editarUsuarioForm.get('fechaNacimiento').setErrors({ required: true });
      }
    };
  }

  modificarDatos(){
        this.usuarioEditado = {
          nombreUsuario: this.editarUsuarioForm.get('nombreUsuario').value,
          apellidoUsuario: this.editarUsuarioForm.get('apellidoUsuario').value,
          fechaNacimiento: this.editarUsuarioForm.get('fechaNacimiento').value,
          nombrePais: this.editarUsuarioForm.get('pais').value,
          nombreIdiomaNativo: this.editarUsuarioForm.get('idiomaNativo').value,
          urlFoto: null,
          descripcion: this.editarUsuarioForm.get('descripcionUsuario').value,
          nombreIdiomaAprendiz: this.editarUsuarioForm.get('idiomaAprendiz').value,
          nombreNivelIdiomaAprendiz: this.editarUsuarioForm.get('nivelIdioma').value,
          nombreIntereses: this.editarUsuarioForm.get('intereses').value
        }
        //console.log(this.usuarioEditado);
        this.guardarDatosUsuario();
  }

  async guardarDatosUsuario() {
    Swal.fire({
      title: 'Modificando datos de usuario...',
      allowOutsideClick: false,
      didOpen: async () => {
        Swal.showLoading();
        if (this.file != undefined) {
          try {
            await uploadBytes(this.imgRef, this.file);
            this.urlFoto = await getDownloadURL(this.imgRef);
            this.usuarioEditado.urlFoto = this.urlFoto;
          } catch (error) {
            console.log(error);
          }
        }
        try {
          await lastValueFrom(this.editarUsuarioService.editarUsuario(this.usuarioEditado, this.idUsuario));
          Swal.hideLoading();
          this.mensajeConfirmacion();
          this.volverPantalla.emit();
        } catch (error) {
          Swal.hideLoading();
          this.mensajeError(error);
        }
        this.modificarDatosChat();
      }
    });
  }

  modificarDatosChat() {
    const uid = this.idUsuario.toString();
    const nombre = this.usuarioEditado.nombreUsuario + ' ' + this.usuarioEditado.apellidoUsuario;
    let foto: string;
    if (this.urlFoto == null){
      foto = this.datosUsuario.urlFoto;
    }else{
      foto = this.urlFoto;
    }
    this.chatService.editarUsuario(uid, nombre, foto).subscribe({
      next: response =>{
      },
      error: error =>{
        console.log(error);
      }
    });
  }

  verificarFormato(nombreArchivo: string): boolean {
    const extensionesPermitidas = /\.(jpg|jpeg|png)$/i;
    return extensionesPermitidas.test(nombreArchivo);
  }

  volver(){
    this.volverPantalla.emit();
  }

  mensajeConfirmacion(){
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
      title: 'Usuario editado correctamente'
    })
  }

  mensajeError(error){
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
      title: 'Error al modificar sus datos',
      text: error.error
    })
  }

}
