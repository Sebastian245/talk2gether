import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegistroChatComponent } from './registro-chat.component';

describe('RegistroChatComponent', () => {
  let component: RegistroChatComponent;
  let fixture: ComponentFixture<RegistroChatComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RegistroChatComponent]
    });
    fixture = TestBed.createComponent(RegistroChatComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
