import { CommonModule, NgIf } from '@angular/common';
import { Component, Input, ViewEncapsulation } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbCarouselModule } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-modal-foto-perfil',
  standalone: true,
  imports: [
    NgIf,
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
  ],
  templateUrl: './modal-foto-perfil.component.html',
  styleUrls: ['./modal-foto-perfil.component.scss'],
  encapsulation: ViewEncapsulation.None

})
export class ModalFotoPerfilComponent {
  @Input() data: { imageUrl: string };
}
