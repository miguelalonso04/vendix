import { Component, OnInit } from '@angular/core';
import { ProductoService } from '../../services/producto.service';
import { ActivatedRoute } from '@angular/router';
import { CestaService } from '../../services/cesta.service';
import { LocalStorageService } from '../../services/local-storage.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-view-producto',
  imports: [CommonModule],
  templateUrl: './view-producto.component.html',
  styleUrl: './view-producto.component.css'
})
export class ViewProductoComponent implements OnInit {

  idProducto!: number;
  productoElegido!: any;
  idUsuario!: number;
  rol!: string;

  constructor(private productoService: ProductoService, private route: ActivatedRoute, private cestaService: CestaService, private localStorage: LocalStorageService){}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params =>{
      this.idProducto = params['idProducto'];
    });

    this.rol = this.localStorage.getItem('roles');
    this.idUsuario = this.localStorage.getItem('idUsuario');
    this.getProducto(this.idProducto);
  }

  private getProducto(idProducto:number){
    this.productoService.getProducto(idProducto).subscribe(
      data => {this.productoElegido = data}
    );
  }

  addACesta(producto: any){
    this.cestaService.addProducto(this.idUsuario,producto).subscribe();

  }


}
