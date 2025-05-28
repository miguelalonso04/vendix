import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProductoService {

  private productoUrl = 'https://vendix.up.railway.app/api/productos';

  constructor(private http: HttpClient) { }

  getAll():Observable<any>{
    return this.http.get(this.productoUrl);
  }

  getProducto(idProducto: number):Observable<any>{
    return this.http.get(`${this.productoUrl}/${idProducto}`);
  }

  createProducto(producto: any, idUsuario:number):Observable<any>{
    const params = new HttpParams().set('idUsuario', idUsuario);
    return this.http.post(this.productoUrl, producto, {params});
  }

  updateProducto(idProducto: number, producto: any):Observable<any>{
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

  uploadImage(productId: number, file: File): Observable<any> {
    const formData = new FormData();
    formData.append('imagen', file);
    return this.http.post(`${this.productoUrl}/${productId}/upload-image`, formData);
  }

  getRutaImagen(id: number): Observable<string> {
    return this.http.get<string>(`${this.productoUrl}/${id}/imagen`, { responseType: 'text' as 'json' });
  }

  getProductosByUsuario(usuarioId: number): Observable<any> {
    return this.http.get(`${this.productoUrl}/usuario/${usuarioId}`);
  }
}
