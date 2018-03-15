import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  template: `
    <h1>{{title}}</h1>
    <nav>
      <a routerLink="/dashboard" routerLinkActive="active">Dashboard</a>
      <a routerLink="/pets" routerLinkActive="active">Pets</a>
    </nav>
    <router-outlet></router-outlet>
    <app-messages></app-messages>
  `,
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Petstore';
}
