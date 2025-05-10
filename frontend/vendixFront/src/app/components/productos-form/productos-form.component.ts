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
  
  selectedFile: File | null = null;
  uploadProgress: number = 0;
  isUploading: boolean = false;
  imagePreview: string | ArrayBuffer | null = null;

  constructor(
    private fb: FormBuilder,
    private productoService: ProductoService,
    private categoriaService: CategoriaService,
    private route: ActivatedRoute,
    private router: Router,
    private http: HttpClient
  ) {
    this.productoForm = this.fb.group({
      nombre: ['', Validators.required],
      descripcion: [''],
      precio: ['', Validators.required],
      stock: [''],
      categoria: ['', Validators.required],
      imagen: ['']
    });
    this.message = '';
  }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.actualizar = params['actualizar'] === 'true';
      this.idProducto = params['idProducto'];
    });

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

  onSubmit() {
    if (this.productoForm.valid) {
      this.isUploading = true;
      
      if (this.selectedFile) {
        this.uploadImage().then(imageUrl => {
          this.createProductoWithImage(imageUrl);
        }).catch(error => {
          this.message = 'Error al subir la imagen: ' + error.message;
          this.isUploading = false;
        });
      } else {
        this.createProducto(this.productoForm.value);
      }
    }
  }

  private uploadImage(): Promise<string> {
    return new Promise((resolve, reject) => {
      const formData = new FormData();
      formData.append('imagen', this.selectedFile!);

      this.http.post<any>(
        `http://tu-backend/api/productos/upload-image`,
        formData,
        { reportProgress: true, observe: 'events' }
      ).subscribe({
        next: (event) => {
          if (event.type === HttpEventType.UploadProgress) {
            this.uploadProgress = Math.round(100 * (event.loaded / (event.total || 1)));
          } else if (event.type === HttpEventType.Response) {
            resolve(event.body.rutaImagen);
          }
        },
        error: (err) => reject(err)
      });
    });
  }

  private createProductoWithImage(imageUrl: string): void {
    const productoData = {
      ...this.productoForm.value,
      imagenUrl: imageUrl,
      categoria: { id: this.productoForm.value.categoria }
    };
    this.createProducto(productoData);
  }

  private createProducto(producto: any): void {
    this.productoService.createProducto(producto).subscribe({
      next: () => {
        this.message = 'Producto creado correctamente. Redirigiendo...';
        setTimeout(() => {
          this.router.navigate(['/home/productos']);
        }, 2000);
      },
      error: (err) => {
        this.message = 'Error al crear producto: ' + err.message;
        this.isUploading = false;
      }
    });
  }

  private getAllCategorias(): void {
    this.categoriaService.getAll().subscribe(
      data => { this.categorias = data; }
    );
  }

  private getCategoria(idCategoria: number): void {
    this.categoriaService.getCategoria(idCategoria).subscribe(
      data => { this.categoria = data; }
    );
  }
}