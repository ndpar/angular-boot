import { Component, OnInit } from '@angular/core';
import { Router }            from '@angular/router';
import { Observable }        from 'rxjs/Observable';
import { Subject }           from 'rxjs/Subject';

import { PetSearchService } from './pet-search.service';
import { Pet } from './pet';

@Component({
  moduleId: module.id,
  selector: 'pet-search',
  templateUrl: 'pet-search.component.html',
  styleUrls: [ 'pet-search.component.css' ],
  providers: [PetSearchService]
})
export class PetSearchComponent implements OnInit {
  pets: Observable<Pet[]>;
  private searchTerms = new Subject<string>();

  constructor(
    private petSearchService: PetSearchService,
    private router: Router) {}

  // Push a search term into the observable stream.
  search(term: string): void {
    this.searchTerms.next(term);
  }

  ngOnInit(): void {
    this.pets = this.searchTerms
      .debounceTime(300)        // wait for 300ms pause in events
      .distinctUntilChanged()   // ignore if next search term is same as previous
      .switchMap(term => term   // switch to new observable each time
        // return the http search observable
        ? this.petSearchService.search(term)
        // or the observable of empty pets if no search term
        : Observable.of<Pet[]>([]))
      .catch(error => {
        // TODO: real error handling
        console.log(error);
        return Observable.of<Pet[]>([]);
      });
  }

  gotoDetail(pet: Pet): void {
    let link = ['/detail', pet.id];
    this.router.navigate(link);
  }
}
