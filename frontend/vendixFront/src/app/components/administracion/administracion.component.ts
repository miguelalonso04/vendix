import { Component, OnInit } from '@angular/core';
import { UsersService } from '../../services/users.service';
import { LocalStorageService } from '../../services/local-storage.service';
import { DireccionService } from '../../services/direccion.service';
import { CommonModule } from '@angular/common';
import { Form, FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'app-administracion',
  imports: [CommonModule,ReactiveFormsModule],
  templateUrl: './administracion.component.html',
  styleUrl: './administracion.component.css'
})
export class AdministracionComponent implements OnInit{

  idUsuario !: number;
  rol !: string;

  lUsuarios !: any[];
  usuario : any;
  direcciones !: any[];
  direccionForm !: FormGroup;
  mostrarFormularioDireccion: boolean = false;
  direccionEditando: any = null;

  constructor(private usersService: UsersService,
              private localStorage: LocalStorageService,
              private direccionService: DireccionService,
              private fb: FormBuilder){
    this.direccionForm = this.fb.group({
    calle: ['', Validators.required],
    ciudad: ['', Validators.required],
    provincia: ['', Validators.required],
    codigoPostal: ['', Validators.required],
    pais: ['', Validators.required]});

    }
              
  ngOnInit(): void {
    this.rol = this.localStorage.getItem('roles');
    this.idUsuario = this.localStorage.getItem('idUsuario');

    if(this.rol == 'ROLE_USER'){
      this.getUsuario();
    }else{
      this.getAllUsers();
    }

    this.getAllDirecciones(this.idUsuario);

  }

  onSUbmit(){
    if(this.direccionForm.valid){
      this.guardarDireccion();
      this.direccionForm.reset();
      this.mostrarFormularioDireccion = false;
    }else{
      alert('Error al añadir la dirección');
    }
  }

  private getUsuario(){
    this.usersService.getUser(this.idUsuario).subscribe(
      data => {this.usuario = data}
    );
  }

  private getAllUsers(){
    this.usersService.getAll().subscribe(
      data => {this.lUsuarios = data}
    );
  }

  private getAllDirecciones(idUsuario: number){
    this.usersService.getAllDirecciones(idUsuario).subscribe(
      data => {this.direcciones = data}
    );
  }

  deleteDireccion(idDireccion: number){
    this.usersService.deleteDireccion(idDireccion).subscribe(
      data => {this.direcciones = data}
    );
  }


  editarDireccion(direccion: any) {
    this.direccionEditando = direccion;
    this.direccionForm.patchValue({
        calle: direccion.calle,
        ciudad: direccion.ciudad,
        provincia: direccion.provincia,
        codigoPostal: direccion.codigoPostal,
        pais: direccion.pais
    });
    this.mostrarFormularioDireccion = true;
  }

  guardarDireccion() {
    if (this.direccionForm.valid) {
        if (this.direccionEditando) {
            // Primero elimina la dirección antigua
            this.usersService.deleteDireccion(this.direccionEditando.id).subscribe({
                next: () => {
                    // Luego crea la nueva
                    this.crearNuevaDireccion();
                },
                error: (err) => console.error('Error al eliminar dirección', err)
            });
        } else {
            this.crearNuevaDireccion();
        }
    }
  }

  crearNuevaDireccion() {
    this.usersService.addDireccion(this.usuario.id, this.direccionForm.value)
        .subscribe({
            next: (nuevaDireccion) => {
                this.actualizarListaDirecciones();
                this.resetFormulario();
            },
            error: (err) => console.error('Error al crear dirección', err)
        });
  }

  actualizarListaDirecciones() {
    this.usersService.getAllDirecciones(this.usuario.id).subscribe(
        direcciones => this.direcciones = direcciones
    );
  }

  resetFormulario() {
    this.direccionForm.reset();
    this.direccionEditando = null;
    this.mostrarFormularioDireccion = false;
  }


}
