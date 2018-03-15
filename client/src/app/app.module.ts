import './rxjs-extensions';

import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {FormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';

import {AppComponent} from './app.component';
import {DashboardComponent} from './dashboard.component';
import {PetDetailComponent} from './pet-detail.component';
import {PetSearchComponent} from './pet-search.component';
import {PetsComponent} from './pets.component';
import {PetService} from './pet.service';

import {routing} from './app.routing';
import {MessageService} from './message.service';
import {MessagesComponent} from './messages/messages.component';

@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    routing
  ],
  declarations: [
    AppComponent,
    DashboardComponent,
    MessagesComponent,
    PetDetailComponent,
    PetSearchComponent,
    PetsComponent
  ],
  providers: [HttpClientModule, MessageService, PetService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
