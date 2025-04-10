import { Component, OnInit } from '@angular/core';
import { CestaService } from '../../services/cesta.service';
import { LocalStorageService } from '../../services/local-storage.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-cesta',
  imports: [CommonModule],
  templateUrl: './cesta.component.html',
  styleUrl: './cesta.component.css'
})
export class CestaComponent implements OnInit {

  idUsuario!: number;
  cesta: any = {
    id: null,
    total: 0,
    productos: [] // aquí irán los productos procesados
  };
  
  constructor(
    private cestaService: CestaService,
    private localStorage: LocalStorageService
  ) {}

  ngOnInit(): void {
    this.idUsuario = this.localStorage.getItem('idUsuario');
    this.getCesta(this.idUsuario);
  }

  private getCesta(idUsuario: number) {
    this.cestaService.getCesta(idUsuario).subscribe(data => {
      // Inicializamos cesta
      this.cesta.id = data.id;
      this.cesta.total = data.total;
      this.cesta.productos = [];

      // Recorremos las claves para encontrar productos
      for (let key in data) {
        if (key !== 'id' && key !== 'total') {
          this.cesta.productos.push({
            producto: key,     // el string toString() del producto
            cantidad: data[key]      // valor del map (la cantidad)
          });
        }
      }

      console.log(this.cesta);
    });
  }

}

