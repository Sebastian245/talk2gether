import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CometChatConversationListScreenPageComponent } from './comet-chat-conversation-list-screen-page.component';

describe('CometChatConversationListScreenPageComponent', () => {
  let component: CometChatConversationListScreenPageComponent;
  let fixture: ComponentFixture<CometChatConversationListScreenPageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CometChatConversationListScreenPageComponent]
    });
    fixture = TestBed.createComponent(CometChatConversationListScreenPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
