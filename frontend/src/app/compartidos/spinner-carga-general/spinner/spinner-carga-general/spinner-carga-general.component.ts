import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import { SpinnerServiceGeneral } from '../../spinner.service';

@Component({
  selector: 'app-spinner-carga-general',
  templateUrl: './spinner-carga-general.component.html',
  styleUrls: ['./spinner-carga-general.component.scss']
})
export class SpinnerCargaGeneralComponent {
  showSpinner$: Observable<boolean>;

  constructor(
    protected spinnerServiceGeneral: SpinnerServiceGeneral
  ) { }

  ngOnInit(): void {
    this.showSpinner$ = this.spinnerServiceGeneral.getSpinnerState();
  }
}
