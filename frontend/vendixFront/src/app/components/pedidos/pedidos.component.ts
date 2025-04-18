import { Component, OnInit } from '@angular/core';
import { HomeComponent } from "../home/home.component";
import { LocalStorageService } from '../../services/local-storage.service';
import { PedidoService } from '../../services/pedido.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-pedidos',
  imports: [HomeComponent],
  templateUrl: './pedidos.component.html',
  styleUrl: './pedidos.component.css'
})
export class PedidosComponent implements OnInit {

  idUsuario !: number;
  idPedido !: number;
  pedido: any;

  constructor(private localStorage: LocalStorageService, private pedidoService: PedidoService, private route: ActivatedRoute){}

  ngOnInit(): void {

    this.route.queryParams.subscribe(
      params => {this.idPedido = params['idPedido']}
    );
    console.log('idPedido :' +this.idPedido)
    this.idUsuario = this.localStorage.getItem('idUsuario');
    this.getPedido(this.idPedido);
  }

  private getPedido(idPedido: number){
    this.pedidoService.getPedido(idPedido).subscribe(
      data => {this.pedido = data} 
     );
  }



}
