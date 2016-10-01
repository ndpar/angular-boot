import { Injectable }    from '@angular/core';
import { Headers, Http } from '@angular/http';

import { Pet } from './pet';

@Injectable()
export class PetService {

  private baseUrl = 'api/pet';  // URL to web api
  private headers = new Headers({'Content-Type': 'application/json'});

  constructor(private http: Http) { }

  create(name: string): Promise<Pet> {
    return this.http
      .post(this.baseUrl, JSON.stringify({name: name}), {headers: this.headers})
      .toPromise()
      .then(res => res.json().data)
      .catch(this.handleError);
  }

  getPets(): Promise<Pet[]> {
    return this.http.get(this.baseUrl)
               .toPromise()
               .then(response => response.json().data as Pet[])
               .catch(this.handleError);
  }

  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error); // for demo purposes only
    return Promise.reject(error.message || error);
  }

  update(pet: Pet): Promise<Pet> {
    const url = `${this.baseUrl}/${pet.id}`;
    return this.http
      .put(url, JSON.stringify(pet), {headers: this.headers})
      .toPromise()
      .then(() => pet)
      .catch(this.handleError);
  }

  getPet(id: number): Promise<Pet> {
    return this.getPets().then(pets => pets.find(pet => pet.id === id));
  }

  delete(id: number): Promise<void> {
    const url = `${this.baseUrl}/${id}`;
    return this.http.delete(url, {headers: this.headers})
      .toPromise()
      .then(() => null)
      .catch(this.handleError);
  }
}
