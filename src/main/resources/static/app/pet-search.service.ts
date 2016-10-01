import { Injectable }     from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable }     from 'rxjs';

import { Pet }           from './pet';

@Injectable()
export class PetSearchService {

  private baseUrl = 'api/pet';

  constructor(private http: Http) {}

  search(term: string): Observable<Pet[]> {
    return this.http
               .get(`${this.baseUrl}/?name=${term}`)
               .map((r: Response) => r.json() as Pet[]);
  }
}
