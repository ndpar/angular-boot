import './rxjs-extensions';

import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule }   from '@angular/forms';
import { HttpModule }    from '@angular/http';

import { AppComponent }        from './app.component';
import { DashboardComponent }  from './dashboard.component';
import { PetDetailComponent } from './pet-detail.component';
import { PetSearchComponent } from './pet-search.component';
import { PetsComponent }     from './pets.component';
import { PetService }         from './pet.service';

import { routing } from './app.routing';

@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    routing
  ],
  declarations: [
    AppComponent,
    DashboardComponent,
    PetDetailComponent,
    PetSearchComponent,
    PetsComponent
  ],
  providers: [ PetService ],
  bootstrap: [ AppComponent ]
})
export class AppModule { }
