import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProductoService {

  private productoUrl = 'http://localhost:8080/api/productos';

  constructor(private http: HttpClient) { }

  getAll():Observable<any>{
    return this.http.get(this.productoUrl);
  }

  getProducto(idProducto: number):Observable<any>{
    return this.http.get(`${this.productoUrl}/${idProducto}`);
  }

  createProducto(producto: any):Observable<any>{
    return this.http.post(this.productoUrl, producto);
  }

  updatProducto(idProducto: number, producto: any):Observable<any>{
    return this.http.put(`${this.productoUrl}/${idProducto}`, producto)
  }

  deleteProducto(idProducto: number):Observable<any>{
    return this.http.delete(`${this.productoUrl}/${idProducto}`);
  }

  getBeetweenPrinceRange(min: number, max: number):Observable<any>{

    const params = new HttpParams()
                  .set('min', min)
                  .set('max', max);
          
     return this.http.get(`${this.productoUrl}/precio`, {params});
  }

  getNumeroTotalProductos():Observable<any>{
    return this.http.get(`${this.productoUrl}/totalCount`);
  }

  getAllByCategoria(idCategoria: number):Observable<any>{
    const params = new HttpParams().set('idCategoria', idCategoria);

    return this.http.get(`${this.productoUrl}/categoria`, {params});
  }

  getAllByNombre(nombreProducto: string): Observable<any>{

    return this.http.get(`${this.productoUrl}/nombre/${nombreProducto}`)

  }
}
