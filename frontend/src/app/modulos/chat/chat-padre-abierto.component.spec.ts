import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChatPadreAbiertoComponent } from './chat-padre-abierto.component';

describe('ChatPadreAbiertoComponent', () => {
  let component: ChatPadreAbiertoComponent;
  let fixture: ComponentFixture<ChatPadreAbiertoComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ChatPadreAbiertoComponent]
    });
    fixture = TestBed.createComponent(ChatPadreAbiertoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
