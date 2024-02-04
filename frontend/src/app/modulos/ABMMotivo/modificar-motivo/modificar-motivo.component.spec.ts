import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModificarMotivoComponent } from './modificar-motivo.component';

describe('ModificarMotivoComponent', () => {
  let component: ModificarMotivoComponent;
  let fixture: ComponentFixture<ModificarMotivoComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ModificarMotivoComponent]
    });
    fixture = TestBed.createComponent(ModificarMotivoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
