import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private authUrl: string = 'http://localhost:8080/auth';

  constructor(private http: HttpClient) { }

  login(user: any):Observable<any>{
    return this.http.post(`${this.authUrl}/login`, user, { observe: 'response'});
  }

  register(user: any):Observable<any>{
    
    return this.http.post(`${this.authUrl}/register`, user, { observe: 'response'});

  }
}

