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
  isProcessing: boolean = false;

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
    this.getDireccion(this.idUsuario);
  }

  async btnPedido() {
    if (this.direccion.length > 0) {
      console.log("Creando pedido...");
      this.isProcessing = true;
      this.getProductosCesta(this.idUsuario);
      
      try {
        await this.delay(3000);
        await this.crearPedido();
        console.log("vaciando cesta");
      } catch (error) {
        console.error("Error al procesar el pedido:", error);
      } finally {
        this.isProcessing = false;
      }
    } else {
      console.error("No hay direcciones disponibles para crear el pedido.");
      if (confirm("No hay direcciones disponibles. Desea añadir una?")) {
        this.router.navigate(['/home/administracion'],
          { queryParams: { idUsuario: this.idUsuario } });
      }else {
        alert("No se puede crear el pedido sin una dirección.");
      }
    }
  }

  private delay(ms: number): Promise<void> {
    return new Promise(resolve => setTimeout(resolve, ms));
  }

  eliminarProducto(producto: any): void {
    this.cestaService.eliminarUnProductoCesta(producto.id, this.idUsuario).subscribe({
      next: () => {this.getCesta(this.idUsuario);
      this.cestaService.getAllProductosCesta(this.idUsuario).subscribe(
        data => {
          const cantidad = data.length;
          this.cestaService.actualizarCantidadCesta(cantidad);
        }
      );
    },
      error: err => console.error('Error al eliminar producto:', err)
    });
  }

  restarCantidad(producto: any): void {
    if (producto.cantidad > 1) {
      this.cestaService.restarCantidadProducto(this.idUsuario, producto.id).subscribe({
        next: () => this.getCesta(this.idUsuario),
        error: err => console.error('Error al restar cantidad:', err)
      });
    }
  }

  sumarCantidad(producto: any): void {
    this.cestaService.sumarCantidadProducto(this.idUsuario, producto.id).subscribe({
      next: () => this.getCesta(this.idUsuario),
      error: err => console.error('Error al sumar cantidad:', err)
    });
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

  private getDireccion(idUsuario: number){
    this.userService.getAllDirecciones(idUsuario).subscribe(
      data => {this.direccion = data}
    );
  }

  private crearPedido(){
    this.pedido = {
      productos:  this.productos,
      direccion: this.direccion[0],
      nombreUsuario: this.nombreUsuario,
      precioTotalPedido: this.cesta.total
    };
    
    this.pedidoService.createPedido(this.pedido,this.idUsuario).subscribe(
      id => {
        this.idPedido = id;
        console.log("Pedido creado con id:", this.idPedido);
        this.router.navigate(
          ['/home/pedidos'], 
          { queryParams: { idPedido: this.idPedido} }
        );
      }


    );
  }

}

