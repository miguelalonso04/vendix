import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DireccionService {

  private direccionUrl = 'https://vendix-production.up.railway.app/api/direccion'

  constructor(private http: HttpClient) { }

  addDireccion(idUsuario: number, direccion: any):Observable<any>{
    return this.http.post(`${this.direccionUrl}/user/${idUsuario}`, direccion);
  }
}
