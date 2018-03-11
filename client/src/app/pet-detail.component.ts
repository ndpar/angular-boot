import { Component, OnInit }        from '@angular/core';
import { ActivatedRoute, Params }   from '@angular/router';
import { Location }                 from '@angular/common';

import { PetService } from './pet.service';
import { Pet } from './pet';

@Component({
  moduleId: module.id,
  selector: 'my-pet-detail',
  templateUrl: 'pet-detail.component.html',
  styleUrls: [ 'pet-detail.component.css' ]
})
export class PetDetailComponent implements OnInit {

  pet: Pet;

  constructor(
    private petService: PetService,
    private route: ActivatedRoute,
    private location: Location
  ) {}

  ngOnInit(): void {
    this.route.params.forEach((params: Params) => {
      let id = +params['id'];
      this.petService.getPet(id).then(pet => this.pet = pet);
    });
  }

  goBack(): void {
    this.location.back();
  }

  save(): void {
    this.petService.update(this.pet).then(() => this.goBack());
  }
}
