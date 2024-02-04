import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CometChat } from '@cometchat-pro/chat';
import { COMETCHAT_CONSTANTS } from 'src/CONSTS';
import { ChatService } from '../services/chat-service.service';

@Component({
  selector: 'app-registro-chat',
  templateUrl: './registro-chat.component.html',
  styleUrls: ['./registro-chat.component.scss']
})
export class RegistroChatComponent {
  UID: string = "user3";
  name: string = "Kevin";
  avatar:string = 'https://firebasestorage.googleapis.com/v0/b/talk2gether-a8b61.appspot.com/o/images%2Froman.jpg?alt=media&token=8e4db5bf-1497-4067-8aeb-d70d7489c45f'
  link: string =  'perfilUsuario/:id'
  user = new CometChat.User(this.UID,this.name,this.avatar);
  constructor(private router:Router,private registroService:ChatService){}
  ngOnInit(){
    
  }


  // registro(){
  //   CometChat.createUser(this.user, COMETCHAT_CONSTANTS.AUTH_KEY).then(
  //       (user: CometChat.User) => {
  //           console.log("user created", user);
  //       }, (error: CometChat.CometChatException) => {
  //           console.log("error", error);
  //       }
  //   )
  // }

}
