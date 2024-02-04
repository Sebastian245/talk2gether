import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CuentaEliminadaComponent } from './cuenta-eliminada.component';

describe('CuentaEliminadaComponent', () => {
  let component: CuentaEliminadaComponent;
  let fixture: ComponentFixture<CuentaEliminadaComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CuentaEliminadaComponent]
    });
    fixture = TestBed.createComponent(CuentaEliminadaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
