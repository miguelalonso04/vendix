import { Component, OnInit } from '@angular/core';
import { HomeComponent } from "../home/home.component";
import { CommonModule } from '@angular/common';
import { ProductoService } from '../../services/producto.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ValoracionesService } from '../../services/valoraciones.service';
import { CategoriaService } from '../../services/categoria.service';
import { CestaService } from '../../services/cesta.service';
import { LocalStorageService } from '../../services/local-storage.service';

@Component({
  selector: 'app-productos',
  imports: [CommonModule],
  templateUrl: './productos.component.html',
  styleUrl: './productos.component.css'
})
export class ProductosComponent implements OnInit{

  productos!: any[];
  categoria !: any;

  nombreProducto!: string;
  buscado : boolean = false;
  idCategoria !: number;
  idUsuario !: number;
  mensaje: string = '';
  mediasValoracion: { [idProducto: number]: number } = {};

  constructor(private productoService: ProductoService,
              private router: Router,
              private valoracionesService: ValoracionesService,
              private categoriaService: CategoriaService,
              private cestaService: CestaService,
              private localStorage: LocalStorageService,
              private route: ActivatedRoute){}

  ngOnInit(): void {

    this.route.queryParams.subscribe(params => {
      this.nombreProducto = params['nombreProducto'];
      this.buscado = params['buscado'] === 'true';
      this.idCategoria = params['idCategoria'];
    });

    this.idUsuario = this.localStorage.getItem('idUsuario');

    this.controlProductos();
  }

  btnProducto(idProducto: number){
    this.router.navigate(['productos/producto'],
      {queryParams: {idProducto : idProducto} });

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

  addACesta(producto: any){
    this.cestaService.addProducto(this.idUsuario,producto).subscribe();

  }

  // METODOS PRIVADOS

  private controlProductos(){
    if(this.buscado){
      this.mensaje = `Mostrando resultados para '${this.nombreProducto}'`;
      this.getProductosByNombre();
    }
    else if(this.idCategoria){
      console.log("ajsdjawdjawj")
      this.getCategoria(this.idCategoria);
      this.getProductosByCategoria(this.idCategoria);      
    }
    else{
      this.mensaje = 'Todos los productos';
      this.getAllProductos();
    }
  }

  private getAllProductos(){
    this.productoService.getAll().subscribe(
      data => {
        this.productos = data
        this.cargarMediasValoracion();
      } 
    );
  }

  private getProductosByNombre(){
    this.productoService.getAllByNombre(this.nombreProducto).subscribe(
      data => {
        this.productos = data
        this.cargarMediasValoracion();
      }
    );
  }

  private getCategoria(idCategoria: number){
    this.categoriaService.getCategoria(idCategoria).subscribe(
      data => {
        this.categoria = data
        this.mensaje = `${this.categoria.nombre} : ${this.categoria.descripcion}`;
      }
    );
  }

  private getProductosByCategoria(idCategoria: number){
    this.productoService.getAllByCategoria(idCategoria).subscribe(
      data => {this.productos = data}
    );
  }
  
  private cargarMediasValoracion() {
    this.productos.forEach(p => {
      this.valoracionesService.mediaValoracionesXProducto(p.id).subscribe(
        media => {
          this.mediasValoracion[p.id] = media;
        }
      );
    });
  }
  
  

}
