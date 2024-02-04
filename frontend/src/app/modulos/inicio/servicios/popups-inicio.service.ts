import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import Swal from 'sweetalert2';

@Injectable({
  providedIn: 'root'
})
export class PopupsInicioService {

  linkReferidoURL = environment.apiUrl + 'cuenta/referirusuario';

  constructor(private httpClient: HttpClient) { }

  obtenerLinkReferido(idCuenta: number) {
    return this.httpClient.get<any>(this.linkReferidoURL + '?idCuenta=' + idCuenta);
  }

  referirAmigos(linkReferido) {
    Swal.fire({
      html: `
        <h2><strong>Invita a tus amigos</strong></h2>
        <h4>¡Cuéntale a tus amigos lo divertido que es Talk2gether!</h4>
        <div id="referido" style="position: relative;">
            <div class="input-container" style="position: relative; display: inline-block;">
                <input type="text" class="form-control" id="enlace"
                style="margin-top: 10px; margin-bottom: 15px; padding-right:35px; box-sizing:border-box;
                white-space: nowrap; overflow: hidden; text-overflow: ellipsis;"
                value="${linkReferido}" readonly>
                <button id="copiar" style="position: absolute; right: 5px; top: 0; bottom: 6px; margin: auto;
                border: none; background: none; cursor: pointer;">
                <i class="fa fa-copy" style="color: #a0a0a0;"></i></button>
            </div>
            <p>Conseguirás logros y sumarás puntos adicionales por cada amigo invitado.</p>
      </div>
      `,
      showCloseButton: true,
      allowEnterKey: false,
      closeButtonHtml: '<i class="fa-regular fa-circle-xmark" style="color: #ffffff; font-size: 25px;"></i>',
      color: '#ffffff',
      background: '#2b6a78',
      width: '35em',
      showConfirmButton: false,
      didRender: function () {
        const enlace = document.getElementById("enlace") as HTMLInputElement;
        const copiar = document.getElementById("copiar");

        copiar.addEventListener("click", function () {
          enlace.select();
          enlace.setSelectionRange(0, 99999);
          document.execCommand("copy");
          Swal.fire({
            text: "¡Enlace copiado al portapapeles!",
            icon: "success",
            toast: true,
            position: "top-end",
            showConfirmButton: false,
            timer: 3000
          });
        });
      }
    })
  }


}
