import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CalificarModalComponent } from './calificar-modal.component';

describe('CalificarModalComponent', () => {
  let component: CalificarModalComponent;
  let fixture: ComponentFixture<CalificarModalComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CalificarModalComponent]
    });
    fixture = TestBed.createComponent(CalificarModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
