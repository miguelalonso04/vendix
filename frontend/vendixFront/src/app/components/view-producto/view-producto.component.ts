import { Component, OnInit } from '@angular/core';
import { ProductoService } from '../../services/producto.service';
import { ActivatedRoute, Router } from '@angular/router';
import { CestaService } from '../../services/cesta.service';
import { LocalStorageService } from '../../services/local-storage.service';
import { CommonModule } from '@angular/common';
import { ValoracionesService } from '../../services/valoraciones.service';

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
  valoraciones!: any[];
  mediaValoracion!: number;
  indiceActual: number = 0;
  urlImagen!: string;
  showAddToCartMessage = false;

  constructor(private productoService: ProductoService,
              private route: ActivatedRoute,
              private cestaService: CestaService,
              private valoracionesService: ValoracionesService,
              private localStorage: LocalStorageService,
              private router: Router){}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params =>{
      this.idProducto = params['idProducto'];
    });

    this.rol = this.localStorage.getItem('roles');
    this.idUsuario = this.localStorage.getItem('idUsuario');
    if(this.idProducto){
      this.getProducto(this.idProducto);
      this.getAllByProducto(this.idProducto);
      this.cargarMediasValoracion(this.idProducto);
      this.getImagenUrl(this.idProducto);
    }

  }

  private getProducto(idProducto:number){
    this.productoService.getProducto(idProducto).subscribe(
      data => {this.productoElegido = data}
    );
  }
  private getAllByProducto(idProducto: number){
    this.valoracionesService.getAllByProducto(idProducto).subscribe(
      data => {this.valoraciones = data}
    );
  }
  private cargarMediasValoracion(idProducto: number) {
    this.valoracionesService.mediaValoracionesXProducto(idProducto).subscribe(
      data => { this.mediaValoracion = data; }
    );
  }

  private getImagenUrl(idProducto: number) {
    this.productoService.getRutaImagen(idProducto).subscribe(
      data => {
        this.urlImagen = data;
      });
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
    this.showAddToCartMessage = true;
    this.cestaService.getAllProductosCesta(this.idUsuario).subscribe(
      data => {
        const cantidad = data.length;
        this.cestaService.actualizarCantidadCesta(cantidad);
      }
    );
    setTimeout(() => {
      this.showAddToCartMessage = false;
    }, 3000);
  }

  updateProducto(Producto: any){
    this.router.navigate(['home/productos/form'],
      {queryParams: {
        actualizar : true,
        idProducto: this.idProducto} });
  }

  deleteProducto(idProducto: number){
    if (confirm('¿Estás seguro de que quieres borrar este producto?')) {
      this.productoService.deleteProducto(idProducto).subscribe({
        next: () => {
          alert('Producto eliminado correctamente.');
          this.router.navigate(['/home/productos']);
        },
        error: err => {
          console.error('Error al eliminar producto:', err);
          alert('Ocurrió un error al borrar el producto.');
        }
      });
    }
  }

  dejarValoracion(){
    this.router.navigate(['home/productos/valoracion/form'],
      {queryParams: {
        idProducto: this.idProducto} }
    );
  }

  anterior() {
    if (this.indiceActual > 0) {
      this.indiceActual--;
    }
  }
  
  siguiente() {
    if (this.indiceActual < this.valoraciones.length - 1) {
      this.indiceActual++;
    }
  }
}
