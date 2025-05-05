import { Component } from '@angular/core';
import { UsersService } from '../../services/users.service';
import { CategoriaService } from '../../services/categoria.service';
import { LocalStorageService } from '../../services/local-storage.service';
import { Router, RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-layout',
  imports: [CommonModule, RouterOutlet, FormsModule],
  templateUrl: './layout.component.html',
  styleUrl: './layout.component.css'
})
export class LayoutComponent {

  rol!: string;
  idUsuario!: number;

  usuario!: any;
  categorias!: any[];
  nombreProducto !: string;

  constructor(private userService: UsersService,
              private categoriaService: CategoriaService, 
              private localStorage: LocalStorageService,
              private router: Router){ }

  ngOnInit(): void {
    this.rol = this.localStorage.getItem('roles');
    this.idUsuario = this.localStorage.getItem('idUsuario');
    
    console.log(this.rol)
    this.getUsuarioById(this.idUsuario);
    this.getAllCategorias();
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
    this.router.navigate(['home/productos'],
      {queryParams: {idCategoria: idCategoria} });

  }

  btnBuscarProducto(){
    this.router.navigate(['home/productos'],
    {
      queryParams: {
        nombreProducto : this.nombreProducto,
        buscado : true
      }
    });
    console.log(this.nombreProducto)
  }

  btnMisProductos(){
    this.router.navigate(['/home/productos'],
      {queryParams: {productosUsuarios: true}}
    );
  }

  cerrarSesion(){
    localStorage.clear();
    this.router.navigate(['/login'], { replaceUrl: true });
  }

}
