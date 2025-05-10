import { Component, OnInit } from '@angular/core';
import { ValoracionesService } from '../../services/valoraciones.service';
import { LocalStorageService } from '../../services/local-storage.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';


@Component({
  selector: 'app-valoracion',
  imports: [CommonModule, FormsModule],
  templateUrl: './valoracion.component.html',
  styleUrl: './valoracion.component.css'
})
export class ValoracionComponent implements OnInit{

  valoraciones!: any[];
  idUsuario !: number;
  rol !: string;
  idCategoria !: number;
  idProducto !: number;
  usernameBuscado!: string;
  mensajeError: string = '';

  constructor(private valoracionesService: ValoracionesService,
              private localStorage: LocalStorageService){}

  ngOnInit(): void {
    this.rol=this.localStorage.getItem('roles');
    this.idUsuario = this.localStorage.getItem('idUsuario');

    this.getValoraciones();
 }

 buscarPorUsername() {
  const username = this.usernameBuscado.trim();

  if (!username) return;

  this.getValoracionesByUsername(username);
}

  private getValoraciones() {
    this.valoracionesService.getAll().subscribe(
      data => { this.valoraciones = data; }
    );
  }

  eliminarValoracion(idValoracion: number) {
    this.valoracionesService.deleteValoracion(idValoracion).subscribe(
      data => { this.valoraciones = data; }
    );
  }

  private getValoracionesByProducto(idProducto: number) {
    this.valoracionesService.getAllByProducto(idProducto).subscribe(
      data => { this.valoraciones = data; }
    );
  }

  private getValoracionesByUsername(username: string) {
    this.valoracionesService.getlAllByUsername(username).subscribe({
      next: (data) => {
        this.valoraciones = data;
        this.mensajeError = ''; 
      },
      error: (err) => {
        console.error('Error al obtener valoraciones:', err);
        this.valoraciones = [];
        this.mensajeError = 'No se pudieron cargar las valoraciones. Verifique el nombre de usuario o intente más tarde.';
      }
    });
  }


}
