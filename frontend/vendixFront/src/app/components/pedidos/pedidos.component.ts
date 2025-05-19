import { Component, OnInit } from '@angular/core';
import { LocalStorageService } from '../../services/local-storage.service';
import { PedidoService } from '../../services/pedido.service';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule, formatDate } from '@angular/common';
import { ValoracionesService } from '../../services/valoraciones.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-pedidos',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './pedidos.component.html',
  styleUrl: './pedidos.component.css'
})
export class PedidosComponent implements OnInit {

  rol !: string;
  idUsuario !: number;
  idPedido !: number;
  pedido: any;
  lPedidos!: any[];
  usuario: any;

  errorMessage: string = '';
  searchError: string = '';
  fechaDesde: string = '';
  fechaHasta: string = '';
  isLoading: boolean = false;

  constructor(private localStorage: LocalStorageService,
              private pedidoService: PedidoService,
              private route: ActivatedRoute,
              private router: Router){}

  ngOnInit(): void {

    this.route.queryParams.subscribe(
      params => {this.idPedido = params['idPedido']}
    );
    this.idUsuario = this.localStorage.getItem('idUsuario');
    this.rol = this.localStorage.getItem('roles');

    this.cargarPedidos();
  }

  private cargarPedidos(): void {
    this.isLoading = false;
    if(this.idPedido){
      this.getPedido(this.idPedido);
      this.getUsuarioPedido(this.idPedido);
    }else if(this.rol == 'ROLE_ADMIN'){
      this.pedidoService.getAllPedidos().subscribe(
        data => {this.lPedidos = data}
      );
    }else{
      this.getAllPedidosByUsuario(this.idUsuario);
    }
  }

  btnVerPedido(idPedido: number) {
    this.router.navigate(['/home/pedidos'], { queryParams: { idPedido } }).then(() => {
      window.location.reload();
    });
  }

  btnVolverPedidos(){
    this.router.navigate(['/home/pedidos']).then(() => {
      window.location.reload();
    });
  }

   buscarPorFechas(): void {
    if (!this.fechaDesde || !this.fechaHasta) {
      this.searchError = 'Debes seleccionar ambas fechas';
      return;
    }

    this.isLoading = true;
    this.searchError = '';

    const desde = formatDate(this.fechaDesde, 'yyyy-MM-dd', 'en-US');
    const hasta = formatDate(this.fechaHasta, 'yyyy-MM-dd', 'en-US');

    this.pedidoService.getBeetweenFechas(desde, hasta).subscribe({
      next: (data) => {
        this.lPedidos = data;
        if (this.lPedidos.length === 0) {
          this.searchError = 'No se encontraron pedidos en el rango de fechas seleccionado';
        }
        this.isLoading = false;
      },
      error: (err) => {
        this.searchError = 'Error al buscar pedidos por fechas';
        this.isLoading = false;
      }
    });
  }

  resetBusqueda(): void {
    this.fechaDesde = '';
    this.fechaHasta = '';
    this.searchError = '';
    this.cargarPedidos();
  }
  
  private getPedido(idPedido: number){
    this.pedidoService.getPedido(idPedido).subscribe(
      data => {this.pedido = data
        console.log(data)
      } 
     );
  }

  private getUsuarioPedido(idPedido: number){
    this.pedidoService.getUsuario(idPedido).subscribe(
      data => {this.usuario = data}
    );
  }

  private getAllPedidosByUsuario(idUsuario: number){
    this.pedidoService.getAllPedidosByUsuario(idUsuario).subscribe(
      data => {this.lPedidos = data}
    );
  }

  confirmarPedido(idPedido: number): void {
    if (confirm('¿Estás seguro de confirmar este pedido?')) {
      this.pedidoService.confirmarPedido(idPedido).subscribe({
        next: () => {
          const pedido = this.lPedidos.find(p => p.id === idPedido);
          if (pedido) {
            pedido.estado = 'CONFIRMADO';
          }
          if (this.pedido?.id === idPedido) {
            this.pedido.estado = 'CONFIRMADO';
          }
        },
        error: (err) => {
          this.errorMessage = 'Error al confirmar el pedido';
        }
      });
    }
  }

  cancelarPedido(idPedido: number): void {
    if (confirm('¿Estás seguro de cancelar este pedido?')) {
      this.pedidoService.cancelarPedido(idPedido).subscribe({
        next: () => {
          const pedido = this.lPedidos.find(p => p.id === idPedido);
          if (pedido) {
            pedido.estado = 'CANCELADO';
          }
          if (this.pedido?.id === idPedido) {
            this.pedido.estado = 'CANCELADO';
          }
        },
        error: (err) => {
          this.errorMessage = 'Error al cancelar el pedido';
        }
      });
    }
  }

}
