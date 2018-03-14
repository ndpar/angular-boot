import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';

import {Pet} from './pet';
import {PetService} from './pet.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: 'dashboard.component.html',
  styleUrls: ['dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  pets: Pet[] = [];

  constructor(private router: Router,
              private petService: PetService) {
  }

  ngOnInit(): void {
    this.petService.getPets()
      .then(pets => this.pets = pets.slice(0, 4));
  }

  gotoDetail(pet: Pet): void {
    const link = ['/detail', pet.id];
    this.router.navigate(link);
  }
}
