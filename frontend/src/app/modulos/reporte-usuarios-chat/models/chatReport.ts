export class ChatReport {
    conversationId: string;
    entities: {
      receiver: {
        entity: {
          uid: string;
          name: string;
          avatar:string;
        };
      };
      sender: {
        entity: {
          uid: string;
          name: string;
          avatar:string;
        };
      };
    };
    text: string;
    deletedAt: number;
    id: string;
    sender: string;
    sentAt: number;
    type: string;
    updatedAt: number;
  }
  