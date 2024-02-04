import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VisualizarInteresComponent } from './visualizar-interes.component';

describe('VisualizarInteresComponent', () => {
  let component: VisualizarInteresComponent;
  let fixture: ComponentFixture<VisualizarInteresComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [VisualizarInteresComponent]
    });
    fixture = TestBed.createComponent(VisualizarInteresComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
