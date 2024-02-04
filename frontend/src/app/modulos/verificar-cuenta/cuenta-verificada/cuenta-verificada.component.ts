import { Component } from '@angular/core';
import {  Router, ActivatedRoute } from '@angular/router';
import { VerificarCuentaService } from '../servicios/verificar-cuenta.service';
import Swal from 'sweetalert2';
import { ChatService } from '../../chat/services/chat-service.service';

@Component({
  selector: 'app-cuenta-verificada',
  templateUrl: './cuenta-verificada.component.html',
  styleUrls: ['./cuenta-verificada.component.scss']
})
export class CuentaVerificadaComponent {

  verificando: boolean = true;
  verificacionRealizada: boolean;
  verificacionCorrecta: boolean;

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private verficiarCuentaService: VerificarCuentaService,
    private registroChat:ChatService
  ) {}

  ngOnInit(){
    let timerInterval
    Swal.fire({
      title: 'Estamos verificando su cuenta',
      html: 'El proceso terminará en <b>3000</b> milisegundos.',
      timer: 3000,
      timerProgressBar: true,
      didOpen: () => {
        Swal.showLoading()
        const b = Swal.getHtmlContainer().querySelector('b');
        timerInterval = setInterval(() => {
          b.textContent = Swal.getTimerLeft().toString();
        }, 100)
      },
      willClose: () => {
        clearInterval(timerInterval)
      }
    }).then(() => {
      this.verificando = false;
    })
    this.activatedRoute.queryParams.subscribe(params => {
      const token = params['token'];
      this.verficiarCuentaService.verificarCuenta(token).subscribe(
        response => {
          this.verificacionCorrecta = true;
          this.verificacionRealizada = true;
        },
        error => {
          this.verificacionCorrecta = false;
          this.verificacionRealizada = true;

        }
      );
    });
  }

  irAPantallaPrincipal(){
    this.router.navigate(['']);
  }

}
