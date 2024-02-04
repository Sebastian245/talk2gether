import { Component } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {

  currentUrl: string;

  constructor(private router: Router) { }

  title = 'frontend-talk2gether';

  ngOnInit() {
  this.router.events.subscribe(event => {
    if (event instanceof NavigationEnd) {
      this.currentUrl = event.url.split('?')[0];
    }
  });
  }

}
