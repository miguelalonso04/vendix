import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UsersService {

  private userUrl: string = 'http://localhost:8080/api/usuario'

  constructor(private http: HttpClient) { }

  getAll():Observable<any>{
    return this.http.get(this.userUrl);
  }

  getUser(id: Number): Observable<any>{
    return this.http.get(`${this.userUrl}/${id}`);
  }

  getUserByUsername(username: string):Observable<any>{
    return this.http.get(`${this.userUrl}/username`);
  }

  deleteUser(id:Number): Observable<any>{
    return this.http.delete(`${this.userUrl}/${id}`);
  }

  deshabilitarUser(id: Number):Observable<any>{
    return this.http.put(`${this.userUrl}/deshabilitar`, id);
  }

  habilitarUser(id: Number):Observable<any>{
    return this.http.put(`${this.userUrl}/habilitar`, id);
  }

  changePassword(idUsuario: Number, newPassw: string){
    return this.http.put(`${this.userUrl}/${idUsuario}/passwd`, newPassw);
  }

  //DIRECCIONES

  getDireccion(idUsuario: Number, idDireccion: Number):Observable<any>{
    return this.http.get(`${this.userUrl}/${idUsuario}/direccion/${idDireccion}`);
  }

  getAllDirecciones(idUsuario:Number):Observable<any>{
    return this.http.get(`${this.userUrl}/${idUsuario}/direcciones`);
  }

  addDireccion(idUsuario: Number, direccion: any):Observable<any>{
    return this.http.put(`${this.userUrl}/${idUsuario}/direccion`, direccion);
  }

  deleteDireccion(idDireccion: Number): Observable<any>{
    return this.http.delete(`${this.userUrl}/direccion/${idDireccion}`);
  }

  //REVISAR BACK
  getRol(idUsuario: Number): Observable<any>{
    return this.http.get(`${this.userUrl}/${idUsuario}/rol`)
  }

}
