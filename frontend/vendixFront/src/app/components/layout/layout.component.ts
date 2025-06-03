import { Component, HostListener } from '@angular/core';
import { UsersService } from '../../services/users.service';
import { CategoriaService } from '../../services/categoria.service';
import { LocalStorageService } from '../../services/local-storage.service';
import { Router, RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CestaService } from '../../services/cesta.service';

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
  isAsideCollapsed : boolean = false;

  cantidadCesta: number = 0;

  iconosCategoria: { [key: string]: string } = {
    Libros: 'bi-book',
    Electronica: 'bi-phone-fill',   
    Moda: 'bi-shirt',               
    Deportes: 'bi-soccer',          
    Automocion: 'bi-car-front-fill',
    Otros: 'bi-box'
  };

  constructor(private userService: UsersService,
              private categoriaService: CategoriaService, 
              private localStorage: LocalStorageService,
              private cestaService: CestaService,
              private router: Router){ }

  ngOnInit(): void {
    this.rol = this.localStorage.getItem('roles');
    this.idUsuario = this.localStorage.getItem('idUsuario');

    this.isAsideCollapsed = this.localStorage.getItem('asideCollapsed');
    
    console.log(this.rol)
    this.getUsuarioById(this.idUsuario);
    this.getAllCategorias();
    this.getCantidadCesta(this.idUsuario);
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

   private getCantidadCesta(idUsuario: number) {
    this.cestaService.getAllProductosCesta(idUsuario).subscribe(
      data => {
        this.cantidadCesta = Array.isArray(data) ? data.length : 0;
      }
    );
  }

  btnCategoria(idCategoria: number){
    this.router.navigate(['home/productos'],
      {queryParams: {idCategoria: idCategoria} }).then(() => {
    window.location.reload()});

  }

  btnValoraciones(){
    this.router.navigate(['home/productos/valoracion']).then(() => {
      window.location.reload()
    });
  }

  btnBuscarProducto(){
    this.router.navigate(['home/productos'],
    {
      queryParams: {
        nombreProducto : this.nombreProducto,
        buscado : true
      }
    }).then(() => {
    window.location.reload()});
    console.log(this.nombreProducto)
  }

  btnMisProductos(){
    this.router.navigate(['/home/productos'],
      {queryParams: {productosUsuarios: true}}
    ).then(() => {
    window.location.reload()});
  }

  cerrarSesion(){
    localStorage.clear();
    this.router.navigate(['/login'], { replaceUrl: true });
  }

   toggleAside() {
    this.isAsideCollapsed = !this.isAsideCollapsed;
    this.localStorage.setItem('asideCollapsed', this.isAsideCollapsed);
  }

  @HostListener('window:resize', ['$event'])
  onResize(event: any) {
    if (event.target.innerWidth <= 992) {
      this.isAsideCollapsed = true;
    } else {
      this.isAsideCollapsed = false;
    }
  }

}
