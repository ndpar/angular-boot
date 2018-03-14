import {Injectable} from '@angular/core';
import {HttpHeaders, HttpClient} from '@angular/common/http';

import {Pet} from './pet';
import {environment} from '../environments/environment';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable()
export class PetService {

  private baseUrl = environment.api_url + '/api/pet';  // URL to web API

  constructor(private http: HttpClient) {
  }

  create(pet: Pet): Promise<Pet> {
    return this.http
      .post(this.baseUrl, pet, httpOptions)
      .toPromise()
      .then(response => response as Pet)
      .catch(this.handleError);
  }

  getPets(): Promise<Pet[]> {
    return this.http
      .get(this.baseUrl)
      .toPromise()
      .then(response => response as Pet[])
      .catch(this.handleError);
  }

  update(pet: Pet): Promise<Pet> {
    return this.http
      .put(this.baseUrl, pet, httpOptions)
      .toPromise()
      .then(() => pet)
      .catch(this.handleError);
  }

  getPet(id: number): Promise<Pet> {
    return this.http
      .get(`${this.baseUrl}/${id}`)
      .toPromise()
      .then(response => response as Pet)
      .catch(this.handleError);
  }

  delete(id: number): Promise<void> {
    return this.http
      .delete(`${this.baseUrl}/${id}`, httpOptions)
      .toPromise()
      .then(() => null)
      .catch(this.handleError);
  }

  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error); // for demo purposes only
    return Promise.reject(error.message || error);
  }
}
