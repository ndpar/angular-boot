import { ModuleWithProviders }  from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { DashboardComponent }   from './dashboard.component';
import { PetDetailComponent }  from './pet-detail.component';
import { PetsComponent }      from './pets.component';

const appRoutes: Routes = [
  {
    path: '',
    redirectTo: '/dashboard',
    pathMatch: 'full'
  },
  {
    path: 'dashboard',
    component: DashboardComponent
  },
  {
    path: 'detail/:id',
    component: PetDetailComponent
  },
  {
    path: 'pets',
    component: PetsComponent
  }
];
export const routing: ModuleWithProviders = RouterModule.forRoot(appRoutes);
