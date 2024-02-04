import { NgModule } from "@angular/core";
import { LoginChatComponent } from "./login-chat/login-chat.component";
import { RegistroChatComponent } from "./registro-chat/registro-chat.component";
import { PrincipalChatComponent } from "./principal-chat/principal-chat.component";
import { CometChatConversationListScreenPageComponent } from "./comet-chat-conversation-list-screen-page/comet-chat-conversation-list-screen-page.component";
import { CommonModule } from "@angular/common";
import { NavbarModule } from "src/app/compartidos/navbar/navbar.module";
import { FormsModule } from "@angular/forms";
import { ChatPadreComponent } from "./chat-padre.component";
import { CometChatAvatar, CometChatConversationList, CometChatConversationListItem, CometChatConversationListWithMessages, CometChatGroupDetails, CometChatGroupListWithMessages, CometChatImageViewer, CometChatIncomingCall, CometChatMessageThread, CometChatMessages, CometChatNavBar, CometChatOutgoingCall, CometChatUI, CometChatUserDetails, CometChatUserList, CometChatUserListWithMessages } from "src/cometchat-pro-angular-ui-kit/CometChatWorkspace/src/public-api";
import { CometChatOutgoingDirectCall } from "src/cometchat-pro-angular-ui-kit/CometChatWorkspace/src/components/Calls/CometChatOutgoingDirectCall/cometchat-outgoing-direct-call.module";
import { CometChatIncomingDirectCall } from "src/cometchat-pro-angular-ui-kit/CometChatWorkspace/src/components/Calls/CometChatIncomingDirectCall/cometchat-incoming-direct-call.module";
import { ChatPadreAbiertoComponent } from "./chat-padre-abierto.component";





@NgModule({
  declarations: [LoginChatComponent, RegistroChatComponent, PrincipalChatComponent, CometChatConversationListScreenPageComponent, ChatPadreComponent, ChatPadreAbiertoComponent],
  imports: [
    CommonModule,
    CometChatUI,
    NavbarModule,
    CometChatUI,
    CometChatConversationListWithMessages,
    CometChatGroupListWithMessages,
    CometChatUserListWithMessages,
    CometChatConversationList,
    CometChatUserList,
    CometChatAvatar,
    FormsModule,
    CometChatConversationListItem,
    CommonModule,
    CometChatNavBar,
    CometChatMessages,
    CometChatUserDetails,
    CometChatMessageThread,
    CometChatImageViewer,
    CometChatGroupDetails,
    CometChatIncomingCall,
    CometChatOutgoingCall,
    CometChatOutgoingDirectCall,
    CometChatIncomingDirectCall
  ]
})
export class ChatModule { }
