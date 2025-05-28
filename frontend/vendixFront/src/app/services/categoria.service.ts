import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CategoriaService {

  private caregoriaUrl: string = 'https://vendix.up.railway.app/api/categoria'


  constructor(private http: HttpClient) { }

  getAll():Observable<any>{
    return this.http.get(this.caregoriaUrl);
  }

  getCategoria(id:Number):Observable<any>{
    return this.http.get(`${this.caregoriaUrl}/${id}`);
  }

  createCategoria(categoria: any):Observable<any>{
    return this.http.post(this.caregoriaUrl, categoria);
  }

  updateCategoria(categoria: any, idCategoria: Number):Observable<any>{
    return this.http.put(`${this.caregoriaUrl}/${idCategoria}`, categoria);
  }

  deleteCategoria(id:Number):Observable<any>{
    return this.http.delete(`${this.caregoriaUrl}/${id}`);
  }

}
