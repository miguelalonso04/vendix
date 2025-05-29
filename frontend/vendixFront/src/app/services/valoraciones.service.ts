import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ValoracionesService {

  private valoracionesUrl = 'https://vendix-production.up.railway.app/api/valoraciones';

  constructor(private http: HttpClient) { }

  getAll():Observable<any>{
    return this.http.get(this.valoracionesUrl);
  }

  getValoracion(idvaloracion: number):Observable<any>{
    return this.http.get(`${this.valoracionesUrl}/${idvaloracion}`);
  }

  createValoracion(valoracion: any, idProducto: number, idUsuario: number){

    const params = new HttpParams()
                .set('idProducto', idProducto)
                .set('idUsuario', idUsuario);

    return this.http.post(this.valoracionesUrl, valoracion, {params});
  }

  updateValoracion(valoracion: any, idvaloracion: number): Observable<any>{
    return this.http.put(`${this.valoracionesUrl}/${idvaloracion}`, valoracion);
  }

  deleteValoracion(idvaloracion: number): Observable<any>{
    return this.http.delete(`${this.valoracionesUrl}/${idvaloracion}`);
  }

  getAllByProducto(idProducto: number): Observable<any>{
    return this.http.get(`${this.valoracionesUrl}/productos/${idProducto}`);
  }

  getlAllByUsername(username: string): Observable<any>{

    const params = new HttpParams().set('username', username);

    return this.http.get(`${this.valoracionesUrl}/productos`, {params});
  }

  numeroValoracionesXProducto(idProducto: number): Observable<any>{
    return this.http.get(`${this.valoracionesUrl}/productos/${idProducto}/count`);
  }

  mediaValoracionesXProducto(idProducto: number): Observable<any>{
    return this.http.get(`${this.valoracionesUrl}/productos/${idProducto}/media`);
  }
}
