import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class PedidoService {

  private pedidoUrl = 'https://vendixx.up.railway.app/api/pedidos';

  constructor(private http: HttpClient) { }

  createPedido(pedido: any, idUsuario: number):Observable<any>{

    const params = new HttpParams().set('idUsuario', idUsuario);

    return this.http.post(`${this.pedidoUrl}/crear`, pedido, {params});
  }

  getPedido(idPedido: number):Observable<any>{
    return this.http.get(`${this.pedidoUrl}/${idPedido}`);
  }

  updatePedido(idPedido: number, pedido: any):Observable<any>{
    return this.http.put(`${this.pedidoUrl}/${idPedido}`, pedido);
  }

  deleteProducto(idPedido: number):Observable<any>{
    return this.http.delete(`${this.pedidoUrl}/${idPedido}`);
  }

  getAllPedidos():Observable<any>{
    return this.http.get(this.pedidoUrl);
  }

  deleteAllPedidos():Observable<any>{
    return this.http.delete(this.pedidoUrl);
  }

  getBeetweenFechas(desde: string , hasta: string):Observable<any>{
    const params = new HttpParams()
      .set('desde', desde)
      .set('hasta', hasta);

      return this.http.get(`${this.pedidoUrl}/fechas`, {params});
  }

  getBeetweenFechasUsuario(idUsuario: number, desde: string , hasta: string):Observable<any>{
    const params = new HttpParams()
      .set('desde', desde)
      .set('hasta', hasta);

      return this.http.get(`${this.pedidoUrl}/fechas/${idUsuario}`, {params});
  }

  confirmarPedido(idPedido: number):Observable<any>{
    const params = new HttpParams().set('id', idPedido);
    return this.http.put(`${this.pedidoUrl}/confirmar`, params);
  }

  cancelarPedido(idPedido: number):Observable<any>{
    const params = new HttpParams().set('id', idPedido);
    return this.http.put(`${this.pedidoUrl}/cancelar`, params);
  }

  getUsuario(idPedido: number):Observable<any>{
    return this.http.get(`${this.pedidoUrl}/${idPedido}/usuario`);
  }

  getAllPedidosByUsuario(idUsuario: number): Observable<any>{
    return this.http.get(`${this.pedidoUrl}/usuario/${idUsuario}`);
  }
}
