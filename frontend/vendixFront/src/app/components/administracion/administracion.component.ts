import { Component, OnInit } from '@angular/core';
import { UsersService } from '../../services/users.service';
import { LocalStorageService } from '../../services/local-storage.service';
import { DireccionService } from '../../services/direccion.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-administracion',
  imports: [CommonModule],
  templateUrl: './administracion.component.html',
  styleUrl: './administracion.component.css'
})
export class AdministracionComponent implements OnInit{

  idUsuario !: number;
  rol !: string;
  mostrarPassword!: boolean;

  lUsuarios !: any[];
  usuario : any;
  direccion : any;

  constructor(private usersService: UsersService,
              private localStorage: LocalStorageService,
            private direccionService: DireccionService){}
              
  ngOnInit(): void {
    this.rol = this.localStorage.getItem('roles');
    this.idUsuario = this.localStorage.getItem('idUsuario');

    if(this.rol == 'ROLE_USER'){
      this.getUsuario();
    }else{
      this.getAllUsers();
    }

    this.mostrarPassword = false;

  }

  private getUsuario(){
    this.usersService.getUser(this.idUsuario).subscribe(
      data => {this.usuario = data}
    );
  }

  private getAllUsers(){
    this.usersService.getAll().subscribe(
      data => {this.lUsuarios = data}
    );
  }

  addDireccion(){
    this.direccionService.addDireccion(this.idUsuario,this.direccion).subscribe();
  }

  togglePassword(){
    this.mostrarPassword = !this.mostrarPassword;
  }

}
