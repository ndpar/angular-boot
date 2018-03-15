import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Params} from '@angular/router';
import {Location} from '@angular/common';

import {Pet} from './pet';
import {PetService} from './pet.service';

@Component({
  selector: 'app-pet-detail',
  templateUrl: 'pet-detail.component.html',
  styleUrls: ['pet-detail.component.css']
})
export class PetDetailComponent implements OnInit {

  pet: Pet;

  constructor(private petService: PetService,
              private route: ActivatedRoute,
              private location: Location) {
  }

  ngOnInit(): void {
    this.getHero();
  }

  getHero(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.petService.getPet(id).subscribe(pet => this.pet = pet);
  }

  goBack(): void {
    this.location.back();
  }

  save(): void {
    this.petService.update(this.pet).subscribe(() => this.goBack());
  }
}
