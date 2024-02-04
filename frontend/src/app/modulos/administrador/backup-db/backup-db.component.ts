import { Component, OnInit } from '@angular/core';
import { BackupService } from './backup.service';
import Swal from 'sweetalert2';
import { FormControl, FormGroup } from '@angular/forms';
import { SpinnerServiceGeneral } from 'src/app/compartidos/spinner-carga-general/spinner.service';

@Component({
  selector: 'app-backup-db',
  templateUrl: './backup-db.component.html',
  styleUrls: ['./backup-db.component.scss']
})
export class BackupDbComponent implements OnInit {

  backupForm: FormGroup;
  listaBackups = [];

  constructor(
    private backupService: BackupService,
    private spinner: SpinnerServiceGeneral
  ) { }


  ngOnInit(): void {
    this.backupService.listarBackup().subscribe({
      next: (response) => {
        this.listaBackups = response;
      },
      error: (error) => {
        console.log(error);
        Swal.fire({
          icon: 'error',
          title: 'Oops...',
          text: 'Algo salió mal. Intente nuevamente más tarde.'
        })
      }
    })

    this.backupForm = new FormGroup({
      backupSeleccionado: new FormControl('default')
    }, {
      updateOn: 'change'
    });

  }

  generarBackup() {
    this.spinner.showSpinner();
    this.backupService.generarBackup().subscribe({
      next: (subscribe) => {
        this.spinner.hideSpinner();
        Swal.fire({
          position: 'top-end',
          icon: 'success',
          title: 'Backup generado correctamente',
          showConfirmButton: false,
          timer: 1500
        })
      },
      error: (error) => {
        console.log(error);
        Swal.fire({
          icon: 'error',
          title: 'Oops...',
          text: 'Algo salió mal. Intente nuevamente más tarde.'
        })
      }
    })
  }

  recuperarBackup() {
    this.spinner.showSpinner();
    this.backupService.restaurarBackup(this.backupForm.get('backupSeleccionado').value).subscribe({
      next: (subscribe) => {
        this.spinner.hideSpinner();
        Swal.fire({
          position: 'top-end',
          icon: 'success',
          title: 'Backup restaurado correctamente',
          showConfirmButton: false,
          timer: 1500
        })
      },
      error: (error) => {
        this.spinner.hideSpinner();
        console.log(error);
        Swal.fire({
          icon: 'error',
          title: 'Oops...',
          text: 'Algo salió mal. Intente nuevamente más tarde.'
        })
      }
    })
  }

}
