import {Injectable} from '@angular/core';
import {HttpHeaders, HttpClient} from '@angular/common/http';

import {Observable} from 'rxjs/Observable';
import {of} from 'rxjs/observable/of';
import {catchError, tap} from 'rxjs/operators';

import {MessageService} from './message.service';
import {Pet} from './pet';
import {environment} from '../environments/environment';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable()
export class PetService {

  private baseUrl = environment.api_url + '/api/pet';  // URL to web API

  constructor(private http: HttpClient, private messageService: MessageService) {
  }

  create(pet: Pet): Observable<Pet> {
    return this.http.post<Pet>(this.baseUrl, pet, httpOptions).pipe(
      tap((p: Pet) => this.log(`Created pet w/ id=${p.id}`)),
      catchError(this.handleError<Pet>('create'))
    );
  }

  getPets(): Observable<Pet[]> {
    return this.http.get<Pet[]>(this.baseUrl).pipe(
      tap(_ => this.log(`Fetched pets`)),
      catchError(this.handleError('getPets', []))
    );
  }

  update(pet: Pet): Observable<Pet> {
    return this.http.put<Pet>(this.baseUrl, pet, httpOptions).pipe(
      tap(_ => this.log(`Updated pet id=${pet.id}`)),
      catchError(this.handleError<any>('update'))
    );
  }

  getPet(id: number): Observable<Pet> {
    return this.http.get<Pet>(`${this.baseUrl}/${id}`).pipe(
      tap(_ => this.log(`Fetched pet id=${id}`)),
      catchError(this.handleError<Pet>(`getPet id=${id}`))
    );
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`, httpOptions).pipe(
      tap(_ => this.log(`Deleted pet id=${id}`)),
      catchError(this.handleError<any>('delete'))
    );
  }

  /**
   * Handle Http operation that failed.
   * Let the app continue.
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // TODO: better job of transforming error for user consumption
      this.log(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }

  private log(message: string) {
    this.messageService.add('PetService: ' + message);
  }
}
