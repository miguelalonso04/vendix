import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ProductoService } from '../../services/producto.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ValoracionesService } from '../../services/valoraciones.service';
import { LocalStorageService } from '../../services/local-storage.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-valoracion-form',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './valoracion-form.component.html',
  styleUrl: './valoracion-form.component.css'
})
export class ValoracionFormComponent implements OnInit {

  valoracionForm!: FormGroup;
  producto!: any;
  message!: string;
  actualizar!: boolean;
  idProducto !: number;
  idUsuario !: number;

  constructor(private fb: FormBuilder,
    private productoService: ProductoService,
    private valoracionesService: ValoracionesService,
    private localStorage: LocalStorageService,
   private route: ActivatedRoute,
   private router: Router){

   this.valoracionForm = this.fb.group({
     comentario: ['', Validators.required],
     valoracion: ['']
   });
   this.message='';
 }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params =>{
      this.actualizar = params['actualizar']=== 'true';
      this.idProducto = params['idProducto'];
    });

    this.idUsuario = this.localStorage.getItem('idUsuario');
    this.getProducto(this.idProducto);
  }

  onSubmit() {
    if (this.valoracionForm.valid) {
      this.createValoracion(this.valoracionForm.value);
      this.message = 'Valoración creada correctamente. Redirigiendo...';
  
      setTimeout(() => {
        this.router.navigate(['/home/productos/producto'],
          {queryParams: {idProducto: this.idProducto, idUsuario: this.idUsuario}});
      }, 2000);
    }
  
    console.log(this.valoracionForm.value);
  }


  private createValoracion(valoracion: any){
    this.valoracionesService.createValoracion(valoracion,this.idProducto,this.idUsuario).subscribe();
  }

  private getProducto(idProducto:number){
    this.productoService.getProducto(idProducto).subscribe(
      data => {this.producto = data}
    );
  }

}
