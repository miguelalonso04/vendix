import { Component, OnInit } from '@angular/core';
import { HomeComponent } from "../home/home.component";
import { CommonModule } from '@angular/common';
import { ProductoService } from '../../services/producto.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-productos',
  imports: [CommonModule],
  templateUrl: './productos.component.html',
  styleUrl: './productos.component.css'
})
export class ProductosComponent implements OnInit{

  productos!: any[];

  constructor(private productoService: ProductoService, private router: Router){}

  ngOnInit(): void {
    this.getAllProductos();
  }


  private getAllProductos(){
    this.productoService.getAll().subscribe(
      data => {this.productos = data} 
    );
  }

  btnProducto(idProducto: number){
    this.router.navigate(['productos/producto'],
      {queryParams: {idProducto : idProducto} });

  }

}
