import { Component, OnInit } from '@angular/core';
import { LocalStorageService } from '../../services/local-storage.service';
import { PedidoService } from '../../services/pedido.service';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ValoracionesService } from '../../services/valoraciones.service';

@Component({
  selector: 'app-pedidos',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './pedidos.component.html',
  styleUrl: './pedidos.component.css'
})
export class PedidosComponent implements OnInit {

  idUsuario !: number;
  idPedido !: number;
  pedido: any;
  lPedidos!: any[];
  usuario: any;

  constructor(private localStorage: LocalStorageService,
              private pedidoService: PedidoService,
              private route: ActivatedRoute,
              private router: Router){}

  ngOnInit(): void {

    this.route.queryParams.subscribe(
      params => {this.idPedido = params['idPedido']}
    );
    this.idUsuario = this.localStorage.getItem('idUsuario');

    if(this.idPedido){
      this.getPedido(this.idPedido);
      this.getUsuarioPedido(this.idPedido);
    }else{
      this.getAllPedidosByUsuario(this.idUsuario);
    }
  }

  btnVerPedido(idPedido: number){
    this.router.navigate(
      ['/home/pedidos'], 
      { queryParams: { idPedido: idPedido} }
    );
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

}
