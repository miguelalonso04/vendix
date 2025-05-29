import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CestaService {

  private cestaUrl = 'https://vendixx.up.railway.app/api/cesta';

  constructor(private http: HttpClient) { }

  getCesta(idUsuario: Number):Observable<any>{
    return this.http.get(`${this.cestaUrl}/${idUsuario}`)
  }

  addProducto(idUsuario: Number, producto: any):Observable<any>{
    return this.http.put(`${this.cestaUrl}/${idUsuario}/add`, producto);
  }

  vaciarCesta(idUsuario: number):Observable<any>{

    const params = new HttpParams().set('idCesta', idUsuario);

    return this.http.put(`${this.cestaUrl}/vaciar`,null, {params});
  }

  eliminarUnProductoCesta(idProducto: number, idUsuario: number):Observable<any>{

    const params = new HttpParams().set('idCesta', idUsuario);
    return this.http.put(`${this.cestaUrl}/eliminar/${idProducto}`,null, {params});
  }

  getAllProductosCesta(idUsuario: number):Observable<any>{
    return this.http.get(`${this.cestaUrl}/${idUsuario}/productos`);
  }

  getPrecioProducto(idUsuario: number, idProducto: number):Observable<any>{
    return this.http.get(`${this.cestaUrl}/${idUsuario}/productos/precio/${idProducto}`);
  }

  getPrecioTotalCesta(idUsuario: number):Observable<any>{
    return this.http.get(`${this.cestaUrl}/${idUsuario}/productos/precio`);
  }

  sumarCantidadProducto(idUsuario: number, idProducto: number):Observable<any>{
    const params = new HttpParams().set('idCesta', idUsuario);
    return this.http.put(`${this.cestaUrl}/productos/sumar/${idProducto}`,null , {params});
  }

  restarCantidadProducto(idUsuario: number, idProducto: number):Observable<any>{
    const params = new HttpParams().set('idCesta', idUsuario);
    return this.http.put(`${this.cestaUrl}/productos/restar/${idProducto}` ,null, {params});
  }
}
