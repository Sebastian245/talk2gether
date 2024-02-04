import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModificarRolComponent } from './modificar-rol.component';

describe('ModificarRolComponent', () => {
  let component: ModificarRolComponent;
  let fixture: ComponentFixture<ModificarRolComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ModificarRolComponent]
    });
    fixture = TestBed.createComponent(ModificarRolComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
