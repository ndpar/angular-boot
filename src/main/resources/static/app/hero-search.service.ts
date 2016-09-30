import { Injectable }     from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable }     from 'rxjs';

import { Hero }           from './hero';

@Injectable()
export class HeroSearchService {

  private baseUrl = 'app/heroes';

  constructor(private http: Http) {}

  search(term: string): Observable<Hero[]> {
    return this.http
               .get(`${this.baseUrl}/?name=${term}`)
               .map((r: Response) => r.json().data as Hero[]);
  }
}
