import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VisualizarMotivoComponent } from './visualizar-motivo.component';

describe('VisualizarMotivoComponent', () => {
  let component: VisualizarMotivoComponent;
  let fixture: ComponentFixture<VisualizarMotivoComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [VisualizarMotivoComponent]
    });
    fixture = TestBed.createComponent(VisualizarMotivoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
