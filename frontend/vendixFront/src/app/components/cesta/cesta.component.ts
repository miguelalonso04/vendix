import { Component, OnInit } from '@angular/core';
import { CestaService } from '../../services/cesta.service';
import { LocalStorageService } from '../../services/local-storage.service';
import { CommonModule } from '@angular/common';
import { HomeComponent } from "../home/home.component";
import { PedidoService } from '../../services/pedido.service';
import { Router } from '@angular/router';
import { UsersService } from '../../services/users.service';

@Component({
  selector: 'app-cesta',
  imports: [CommonModule, HomeComponent],
  templateUrl: './cesta.component.html',
  styleUrl: './cesta.component.css'
})
export class CestaComponent implements OnInit {

  idUsuario!: number;
  idPedido!: number;
  idDireccion!: number;

  cesta: any;
  pedido !: any;
  productos!: any[];
  direccion!: any[];
  nombreUsuario !: string;

  constructor(
    private cestaService: CestaService,
    private localStorage: LocalStorageService,
    private pedidoService: PedidoService,
    private userService: UsersService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.idUsuario = this.localStorage.getItem('idUsuario');
    this.getCesta(this.idUsuario);
    this.getProductosCesta(this.idUsuario);
    this.getNombreUsuario(this.idUsuario);
  }

  btnPedido(){
    this.crearPedido();  
  }  

  /*

  METODOS PRIVADOS

  */

  private getCesta(idUsuario: number) {
    this.cestaService.getCesta(idUsuario).subscribe(data => {
      this.cesta = data;
      console.log(this.cesta);
    });
  }

  private getProductosCesta(idUsuario: number){
   this.cestaService.getAllProductosCesta(idUsuario).subscribe(
      
      data => {this.productos = data}
    );
  }
  
  private getNombreUsuario(idUsuario: number){
    this.userService.getUser(idUsuario).subscribe(
      data => {this.nombreUsuario = data.username}
    );
  }

  private crearPedido(){

    this.pedido = {
      productos:  this.productos,
      direccion: this.direccion,
      nombreUsuario: this.nombreUsuario,
      precioTotalPedido: this.cesta.total
    };
    console.log(this.pedido);
    this.pedidoService.createPedido(this.pedido,this.idUsuario).subscribe(
      id => {
        this.idPedido = id;
        console.log("Pedido creado con id:", this.idPedido);

        this.router.navigate(
          ['/home/pedidos'], 
          { queryParams: { idPedido: this.idPedido } }
        );
      }
    );
  }

}

