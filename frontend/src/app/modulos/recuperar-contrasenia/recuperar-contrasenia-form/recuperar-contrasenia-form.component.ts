import { ChangeDetectionStrategy, Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-recuperar-contrasenia-form',
  templateUrl: './recuperar-contrasenia-form.component.html',
  styleUrls: ['./recuperar-contrasenia-form.component.scss'],
})
export class RecuperarContraseniaFormComponent 
implements OnInit 
{

  recuperarForm: FormGroup;
  patternEmail = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|.(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
  submitForm: boolean = false;

  @Input() errorService: boolean;
  @Input() messageError: string;
  @Output() dataEmail: EventEmitter<string> = new EventEmitter();
  constructor(private router: Router) { }


  ngOnInit(): void {
    this.recuperarForm = new FormGroup({
      email: new FormControl('', [Validators.pattern(this.patternEmail), Validators.required]),
    }, {
      updateOn: 'change'
    });
  }
  
  enviarDatos(){
    this.submitForm = true;
    if (this.recuperarForm.invalid) return;
    this.dataEmail.emit(this.recuperarForm.get('email').value);
  }

  returnLogin(){
    this.router.navigate(['login']);
  }
}
