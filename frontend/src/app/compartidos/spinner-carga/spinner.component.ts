import { Component, Input } from '@angular/core';
import { Observable, of } from 'rxjs';
import { SpinnerService } from './spinner.service';

@Component({
  selector: 'app-spinner',
  templateUrl: './spinner.component.html',
  styleUrls: ['./spinner.component.scss']
})
export class SpinnerComponent {
  showSpinner$: Observable<boolean>;

  @Input() showTinySpinner: boolean;

  constructor(
    protected spinnerService: SpinnerService
  ) { }

  ngOnInit(): void {
    this.showSpinner$ = this.showTinySpinner ? of(this.showTinySpinner) : this.spinnerService.getSpinnerState();
  }
}
