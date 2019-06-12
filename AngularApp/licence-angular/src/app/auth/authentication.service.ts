import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })
export class AuthenticationService {
  baseUrl: string;
  constructor(private http: HttpClient) {
    this.baseUrl = `${environment.urlAddress}`;
  }

  login(name: string, password: string) {
    return this.http
      .post<any>(`${this.baseUrl}/authentication`, { name, password });
  }

  logout() {
    localStorage.removeItem('user');
  }
}
