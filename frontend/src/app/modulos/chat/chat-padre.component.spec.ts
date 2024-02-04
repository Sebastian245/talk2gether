import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChatPadreComponent } from './chat-padre.component';

describe('ChatPadreComponent', () => {
  let component: ChatPadreComponent;
  let fixture: ComponentFixture<ChatPadreComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ChatPadreComponent]
    });
    fixture = TestBed.createComponent(ChatPadreComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
