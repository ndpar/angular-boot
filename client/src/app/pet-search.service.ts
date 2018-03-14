import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';

import {Pet} from './pet';
import {environment} from '../environments/environment';

@Injectable()
export class PetSearchService {

  private baseUrl = environment.api_url + '/api/pet';

  constructor(private http: HttpClient) {
  }

  search(term: string): Observable<Pet[]> {
    return this.http
      .get(`${this.baseUrl}/?name=${term}`)
      .map(response => response as Pet[]);
  }
}
