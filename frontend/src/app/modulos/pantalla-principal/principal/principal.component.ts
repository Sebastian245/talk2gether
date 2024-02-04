import { Component, OnInit } from '@angular/core';
import { SpinnerServiceGeneral } from 'src/app/compartidos/spinner-carga-general/spinner.service';

@Component({
  selector: 'app-principal',
  templateUrl: './principal.component.html',
  styleUrls: ['./principal.component.scss']
})
export class PrincipalComponent implements OnInit{
  constructor(private spinnerGeneral: SpinnerServiceGeneral){}

  ngOnInit(): void {
      this.spinnerGeneral.hideSpinner()
  }
}
