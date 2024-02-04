import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InfoPaisComponent } from './info-pais.component';

describe('InfoPaisComponent', () => {
  let component: InfoPaisComponent;
  let fixture: ComponentFixture<InfoPaisComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [InfoPaisComponent]
    });
    fixture = TestBed.createComponent(InfoPaisComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
