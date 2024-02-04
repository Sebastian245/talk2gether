import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModificarInteresComponent } from './modificar-interes.component';

describe('ModificarInteresComponent', () => {
  let component: ModificarInteresComponent;
  let fixture: ComponentFixture<ModificarInteresComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ModificarInteresComponent]
    });
    fixture = TestBed.createComponent(ModificarInteresComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
