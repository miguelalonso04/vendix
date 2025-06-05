import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ProductoService } from '../../services/producto.service';
import { CommonModule } from '@angular/common';
import { CategoriaService } from '../../services/categoria.service';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient, HttpEventType } from '@angular/common/http';

@Component({
  selector: 'app-productos-form',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './productos-form.component.html',
  styleUrl: './productos-form.component.css'
})
export class ProductosFormComponent implements OnInit {

  productoForm!: FormGroup;
  categorias!: any[];
  categoria!: any;
  message: string;
  actualizar!: boolean;
  idProducto!: number;
  idUsuario: number = Number(localStorage.getItem('idUsuario'));
  showSuccessMessage = false;
  successMessage = '';
  
  selectedFile!: File;
  uploadProgress: number = 0;
  isUploading: boolean = false;
  imagePreview: string | ArrayBuffer | null = null;

  constructor(
    private fb: FormBuilder,
    private productoService: ProductoService,
    private categoriaService: CategoriaService,
    private route: ActivatedRoute,
    private router: Router,
  ) {
    this.productoForm = this.fb.group({
      nombre: ['', Validators.required],
      descripcion: [''],
      precio: [0, [Validators.required, Validators.min(0)]],
      stock: [0, [Validators.required, Validators.min(0)]],
      categoria: [null as number | null, Validators.required],
      imagen: ['']
    });
    this.message = '';
  }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.actualizar = params['actualizar'] === 'true';
      this.idProducto = params['idProducto'];
    });

    if(this.actualizar) {
      this.productoService.getProducto(this.idProducto).subscribe(
        data => {
          this.productoForm.patchValue({
            nombre: data.nombre,
            descripcion: data.descripcion,
            precio: data.precio,
            stock: data.stock,
            categoria: data.categoria.idCategoria
          });
          this.imagePreview = data.imagen;
        }
      );
    }

    this.getAllCategorias();
  }

  onFileSelected(event: any): void {
    const file = event.target.files[0];
    if (file) {
      if (!file.type.match('image.*')) {
        this.message = 'Solo se permiten archivos de imagen';
        return;
      }

      if (file.size > 5 * 1024 * 1024) {
        this.message = 'La imagen no debe exceder los 5MB';
        return;
      }

      this.selectedFile = file;
      this.productoForm.patchValue({ imagen: file });

      const reader = new FileReader();
      reader.onload = () => this.imagePreview = reader.result;
      reader.readAsDataURL(file);
    }
  }

  async onSubmit() {
    if (this.productoForm.valid) {
      this.isUploading = true;
      try {
        if (this.actualizar) {
          this.productoService.updateProducto(this.idProducto, this.productoForm.value).subscribe();
          this.successMessage = this.actualizar 
          ? `Producto "${this.productoForm.value.nombre}" actualizado correctamente` 
          : `Producto "${this.productoForm.value.nombre}" creado correctamente`;
      
        this.showSuccessMessage = true;

        } else {
          const idProducto = await this.createProducto(this.productoForm.value, this.idUsuario);
          this.idProducto = idProducto;
          this.message = 'Producto creado correctamente. Redirigiendo...';
        }

        if (this.selectedFile) {
          await this.uploadImage();
        }

        setTimeout(() => this.router.navigate(['/home/productos']), 2000);
        } catch (error: any) {
          this.message = 'Error: ' + error.message;
          this.isUploading = false;
          
        }

        if (!this.actualizar) {
          this.productoForm.reset();
          this.imagePreview = null;
        }

    } else {
      console.log(this.productoForm.value);
    }
  }

  removeImage(fileInput: HTMLInputElement) {
    this.imagePreview = null;
    fileInput.value = '';
  }

  private createProducto(producto: any, idUsuario: number): Promise<number> {
    return new Promise((resolve) => {
      this.productoService.createProducto(producto,idUsuario).subscribe({
        next: (idProducto: number) => resolve(idProducto),
      });
    });
  }


  private uploadImage(): Promise<string> {
    return new Promise((resolve) => {
      const formData = new FormData();
      formData.append('imagen', this.selectedFile!);

      this.productoService.uploadImage(this.idProducto,this.selectedFile).subscribe();
    });
  }

  private getAllCategorias(): void {
    this.categoriaService.getAll().subscribe(
      data => { this.categorias = data; }
    );
  }

  btnCancelar() {
    this.router.navigate(['/home/productos']);
  }

}