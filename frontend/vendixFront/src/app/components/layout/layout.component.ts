import { Component } from '@angular/core';
import { ProductoService } from '../../services/producto.service';
import { UsersService } from '../../services/users.service';
import { CategoriaService } from '../../services/categoria.service';
import { LocalStorageService } from '../../services/local-storage.service';
import { CestaService } from '../../services/cesta.service';
import { Router, RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-layout',
  imports: [CommonModule, RouterOutlet],
  templateUrl: './layout.component.html',
  styleUrl: './layout.component.css'
})
export class LayoutComponent {

rol!: string;
  idUsuario!: number;

  usuario!: any;
  productos!: any[];
  categorias!: any[];

  constructor(private productoService: ProductoService,
              private userService: UsersService,
              private categoriaService: CategoriaService, 
              private localStorage: LocalStorageService,
              private cestaService: CestaService,
              private router: Router){ }

  ngOnInit(): void {
    this.rol = this.localStorage.getItem('roles');
    this.idUsuario = this.localStorage.getItem('idUsuario');
    
    console.log(this.rol)
    this.getAllProductos();
    this.getUsuarioById(this.idUsuario);
    this.getAllCategorias();
  }

  private getAllProductos(){
    this.productoService.getAll().subscribe(
      data => {this.productos = data} 
    );
  }

  private getUsuarioById(idUsuario: number){
    this.userService.getUser(idUsuario).subscribe(
      data => {this.usuario = data}
    );
  }

  private getAllCategorias(){
    this.categoriaService.getAll().subscribe(
      data => {this.categorias = data}
    );
  }

  btnCategoria(idCategoria: number){
    this.router.navigate(['home/categoria/productos'],
      {queryParams: {idCategoria: idCategoria} });

  }

  btnProducto(idProducto: number){
    this.router.navigate(['productos/producto'],
      {queryParams: {idProducto : idProducto} });

  }

  buscarProducto(){
    
  }

}
