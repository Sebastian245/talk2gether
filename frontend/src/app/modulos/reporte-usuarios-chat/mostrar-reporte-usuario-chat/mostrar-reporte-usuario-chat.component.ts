import {
  Component,
  ElementRef,
  ViewChild,
} from '@angular/core';
import { Router } from '@angular/router';
import { ChatReport } from '../models/chatReport';
import {
  AbstractControl,
  FormControl,
  FormGroup,
  ValidationErrors,
  ValidatorFn,
  Validators,
} from '@angular/forms';
import { ReporteUsuarioChatService } from '../services/reporte-usuario-chat.service';
import { Observable, Subscription, debounceTime, distinctUntilChanged, fromEvent, of } from 'rxjs';
import { SpinnerServiceGeneral } from 'src/app/compartidos/spinner-carga-general/spinner.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-mostrar-reporte-usuario-chat',
  templateUrl: './mostrar-reporte-usuario-chat.component.html',
  styleUrls: ['./mostrar-reporte-usuario-chat.component.scss'],
})
export class MostrarReporteUsuarioChatComponent {
  messages: any[];
  idsForm: FormGroup;
  chatsArmados: ChatReport[] = [];
  mostrar: boolean = false;
  seleccionado: boolean = false;
  subirBloqueado: boolean = false;
  subirArchivo: boolean = false;
  submitForm: boolean = false;
  archivoSubido: boolean = false;
  chats$ = Observable<any[]>;
  
  @ViewChild('fileInput', { static: true })
  fileInputRef!: ElementRef<HTMLInputElement>;

  constructor(
    private route: Router,
    private reporteChat: ReporteUsuarioChatService,
    private spinnerService: SpinnerServiceGeneral
  ) {}

  ngOnInit() {
    this.idsForm = new FormGroup(
      {
        id1: new FormControl('', [Validators.required,Validators.pattern(/^[0-9]*$/)]),
        id2: new FormControl('', [Validators.required,Validators.pattern(/^[0-9]*$/)]),
        fechaDesde: new FormControl(''),
        fechaHasta: new FormControl(''),
      },
      { validators: this.validarFechas() }
    );
  }
  onInputChange(event: Event): void {
    const inputValue = (event.target as HTMLInputElement).value;
    this.eliminarVistaChats();
  }

  eliminarVistaChats(): void {
    this.mostrar = false;
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
  onSubmit() {
    this.idsForm.markAllAsTouched();
    if (this.idsForm.valid) {
      console.log('Formulario válido, se puede enviar.');
    } else {
      console.log('Formulario inválido, no se puede enviar.');
    }
  }

  onFileSelected(event: any) {
    const file: File = event.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.readAsText(file, 'UTF-8');
      reader.onload = (evt) => {
        const archivoLeido = JSON.parse(evt.target.result as string);
        this.subirArchivo = true;
        this.armarChats(archivoLeido);
        this.archivoSubido = true;
      };
      reader.onerror = (evt) => {
        error: (error) => {
          this.spinnerService.hideSpinner()
          Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: 'Algo salió mal! Reinicie la página o suba nuevamente el archivo.',
          });
        }
      };
    }
  }
  onLinkSelectedFirebase() {
    this.spinnerService.showSpinner()
    this.eliminarVistaChats()
    this.reporteChat.traerJsonBackUp().subscribe({
      next: (response) => {
        this.spinnerService.hideSpinner()
        this.subirBloqueado = true;
        this.borraArchivoSubido();
        this.seleccionado = true;
        this.subirArchivo = false;
        this.archivoSubido = true;
        const archivoLeido = response;
        this.armarChats(archivoLeido)
      },
      error: (error) => {
        this.spinnerService.hideSpinner()
        Swal.fire({
          icon: 'error',
          title: 'Oops...',
          text: 'Algo salió mal! Reinicie la página o intente nuevamente mas tarde.',
        });
      },
    });
  }
  
  borraArchivoSubido() {
    if (this.subirBloqueado && this.chatsArmados.length != 0) {
      const fileInput = this.fileInputRef.nativeElement;
      if (fileInput) {
        fileInput.value = '';
        this.reporteChat.eliminarChats(this.chatsArmados);
      }
    }
  }
  eliminarSeleccionado() {
    this.seleccionado = false;
    this.reloadPage();
  }

  armarChats(archivoLeido: any) {
    this.chatsArmados = [];
    for (let i = 0; i < archivoLeido['data'].length; i++) {
      const item = archivoLeido['data'][i];
      const chatArmado: ChatReport = {
        conversationId: item['conversationId'],
        entities: {
          receiver: {
            entity: {
              uid: item['data']['entities']['receiver']['entity']['uid'],
              name: item['data']['entities']['receiver']['entity']['name'],
              avatar: item['data']['entities']['receiver']['entity']['avatar'],
            },
          },
          sender: {
            entity: {
              uid: item['data']['entities']['sender']['entity']['uid'],
              name: item['data']['entities']['sender']['entity']['name'],
              avatar: item['data']['entities']['sender']['entity']['avatar'],
            },
          },
        },
        text: item['data']['text'],
        deletedAt: item['deletedAt'],
        id: item['id'],
        sender: item['sender'],
        sentAt: item['sentAt'],
        type: item['type'],
        updatedAt: item['updatedAt'],
      };
      this.chatsArmados.push(chatArmado);
    }
    console.log(this.chatsArmados);
    this.reporteChat.guardarChats(this.chatsArmados);
  }

  mostrarChats() {
    this.submitForm = true;
    if (this.idsForm.invalid) return;
    if (
      this.idsForm.get('id1').value != null &&
      this.idsForm.get('id2').value != null
    ) {
      this.reporteChat.guardarId(this.idsForm.get('id1').value);
      this.reporteChat.guardarId2(this.idsForm.get('id2').value);
      this.mostrar = true;
    }
  }
  reloadPage() {
    const currentUrl = this.route.url;
    this.route.navigateByUrl('/administrador', { skipLocationChange: true }).then(() => {
      this.route.navigateByUrl(currentUrl);
    });
  }
}
