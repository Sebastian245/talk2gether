

import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import { CometChat } from "@cometchat-pro/chat";
import { AppModule } from './app/app.module';
import { COMETCHAT_CONSTANTS } from './CONSTS';


const appID = "245472a631aa053e";
const region = "us";
const appSetting = new CometChat.AppSettingsBuilder()
  .subscribePresenceForAllUsers()
  .setRegion(region)
  .build();
CometChat.init(appID, appSetting).then(
  () => {
    console.log("Initialization completed successfully");
    platformBrowserDynamic().bootstrapModule(AppModule)
    .catch(err => console.error(err));
  },
  (error) => {
    console.log("Initialization failed with error:", error);
  }
);