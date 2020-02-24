import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../../environments/environment';
import { Observable } from 'rxjs';
import { timeout } from 'rxjs/operators';

@Injectable()
export class ApiService {

  constructor(private http: HttpClient) { }

  private _endpoint(resource: string): string {
    return `${environment.base_url}${resource}`;
  }

  get<T>(resource: string): Observable<T> {
    return this.http.get<T>(this._endpoint(resource))
    .pipe(timeout(environment.http_timeout));
  }

  post<T>(resource: string, body: any): Observable<T> {
    return this.http.post<T>(this._endpoint(resource), body)
    .pipe(timeout(environment.http_timeout));
  }

  put<T>(resource: string, body: any): Observable<T> {
    return this.http.put<T>(this._endpoint(resource), body)
    .pipe(timeout(environment.http_timeout));
  }

  delete<T>(resource: string): Observable<T> {
    return this.http.delete<T>(this._endpoint(resource))
    .pipe(timeout(environment.http_timeout));
  }

  patch<T>(resource: string, body: any): Observable<T> {
    return this.http.patch<T>(this._endpoint(resource), body)
    .pipe(timeout(environment.http_timeout));
  }

}
