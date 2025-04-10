import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ProductoService } from '../../services/producto.service';
import { CommonModule } from '@angular/common';
import { CategoriaService } from '../../services/categoria.service';

@Component({
  selector: 'app-productos-form',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './productos-form.component.html',
  styleUrl: './productos-form.component.css'
})
export class ProductosFormComponent implements OnInit {

  productoForm!: FormGroup;
  categorias!: any[]
  categoria !: any;
  message: string;

  constructor(private fb: FormBuilder, private productoService: ProductoService, private categoriaService: CategoriaService){
    this.productoForm = this.fb.group({
      nombre: ['', Validators.required],
      descripcion: [''],
      precio: ['',Validators.required],
      stock: [''],
      categoria: ['',Validators.required]
    });
    this.message='';

  }
  ngOnInit(): void {
    this.getAllCategorias();
  }

  onSubmit(){ 
    if(this.productoForm.valid){
      this.createProducto(this.productoForm.value);
      this.message = 'Producto creado correctamente';
    }
    console.log(this.productoForm.value)
    
  }

  private createProducto(producto: any){
    this.productoService.createProducto(producto).subscribe();
  }

  private getAllCategorias(){
    this.categoriaService.getAll().subscribe(
      data => {this.categorias = data}
    );
  }

  private getCategoria(idCategoria: number){
   this.categoriaService.getCategoria(idCategoria).subscribe(
    data => { this.categoria = data}
   );
  }

}
