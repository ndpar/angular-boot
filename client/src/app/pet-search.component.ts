import {Component, OnInit} from '@angular/core';

import {Observable} from 'rxjs/Observable';
import {Subject} from 'rxjs/Subject';
import {debounceTime, distinctUntilChanged, switchMap} from 'rxjs/operators';

import {Pet} from './pet';
import {PetSearchService} from './pet-search.service';

@Component({
  moduleId: module.id,
  selector: 'app-pet-search',
  templateUrl: 'pet-search.component.html',
  styleUrls: ['pet-search.component.css'],
  providers: [PetSearchService]
})
export class PetSearchComponent implements OnInit {

  pets$: Observable<Pet[]>;
  private searchTerms = new Subject<string>();

  constructor(private petSearchService: PetSearchService) {
  }

  // Push a search term into the observable stream.
  search(term: string): void {
    this.searchTerms.next(term);
  }

  ngOnInit(): void {
    this.pets$ = this.searchTerms.pipe(
      // wait 300ms after each keystroke before considering the term
      debounceTime(300),

      // ignore new term if same as previous term
      distinctUntilChanged(),

      // switch to new search observable each time the term changes
      switchMap((term: string) => this.petSearchService.search(term)),
    );
  }
}
