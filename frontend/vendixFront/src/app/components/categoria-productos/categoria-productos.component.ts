import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CategoriaService } from '../../services/categoria.service';
import { ProductoService } from '../../services/producto.service';
import { CommonModule } from '@angular/common';
import { CestaService } from '../../services/cesta.service';
import { LocalStorageService } from '../../services/local-storage.service';

@Component({
  selector: 'app-categoria-productos',
  imports: [CommonModule],
  templateUrl: './categoria-productos.component.html',
  styleUrl: './categoria-productos.component.css'
})
export class CategoriaProductosComponent implements OnInit{

  categoria !: any;
  idCategoria !: number;
  idUsuario !: number;
  productos !: any[];

  constructor(private route: ActivatedRoute,
              private localStorage: LocalStorageService,
              private categoriaService: CategoriaService,
              private productoService: ProductoService,
              private cestaService: CestaService){}


  ngOnInit(): void {
    this.route.queryParams.subscribe(params =>{
      this.idCategoria = params['idCategoria'];
    });

    this.idUsuario = this.localStorage.getItem('idUsuario');
    this.getCategoria(this.idCategoria);
    this.getProductosByCategoria(this.idCategoria);
  }

  private getCategoria(idCategoria: number){
    this.categoriaService.getCategoria(idCategoria).subscribe(
      data => {this.categoria = data}
    );
  }

  private getProductosByCategoria(idCategoria: number){
    this.productoService.getAllByCategoria(idCategoria).subscribe(
      data => {this.productos = data}
    );
  }

  addACesta(producto: any){
    this.cestaService.addProducto(this.idUsuario,producto).subscribe();

  }

  getEstrellas(rating: number): string[] {
    const estrellas = [];
    for (let i = 1; i <= 5; i++) {
      if (rating >= i) {
        estrellas.push('full');
      } else if (rating > i - 1 && rating < i) {
        estrellas.push('half');
      } else {
        estrellas.push('empty');
      }
    }
    return estrellas;
  }
  
}
