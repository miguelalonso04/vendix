import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { LocalStorageService } from '../../services/local-storage.service';
import { catchError, tap } from 'rxjs';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  userForm!: FormGroup;
  errorMessage: string = '';

  constructor(private authService: AuthService, private router: Router, private fb: FormBuilder, private localStorage: LocalStorageService ){

    this.userForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    })

  } 

  onSubmit(){
        
    this.authService.login(this.userForm.value).pipe(
      tap(response => {
        if (response && response.status === 200) {
          this.errorMessage = '';

          this.localStorage.setItem('roles', response.body.roles);
          this.localStorage.setItem('idUsuario', response.body.id);

          this.router.navigate(['/home'])
        }
      }),
      catchError(error => {
        console.log('Error de login:', error);
        return this.errorMessage = 'Usuario o contraseña incorrecta';
      })
    ).subscribe();
  }

}
