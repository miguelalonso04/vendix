import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CategoriaService } from '../../services/categoria.service';
import { ProductoService } from '../../services/producto.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-categoria-productos',
  imports: [CommonModule],
  templateUrl: './categoria-productos.component.html',
  styleUrl: './categoria-productos.component.css'
})
export class CategoriaProductosComponent implements OnInit{

  categoria !: any;
  idCategoria !: number;
  productos !: any[];

  constructor(private route: ActivatedRoute, private categoriaService: CategoriaService, private productoService: ProductoService){}


  ngOnInit(): void {
    this.route.queryParams.subscribe(params =>{
      this.idCategoria = params['idCategoria'];
    });
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

}
