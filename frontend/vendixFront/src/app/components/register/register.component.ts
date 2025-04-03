import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { catchError, of, tap } from 'rxjs';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {

  userForm !: FormGroup;
  errorMessage: string = '';

  constructor(private authService: AuthService, private fb: FormBuilder){

    this.userForm = this.fb.group({
      firstName: ['',[Validators.required]],
      lastName: ['',[Validators.required]],
      telefono: ['',[Validators.required, Validators.pattern(/^[0-9]{9}$/)]],
      email: ['',[Validators.required, Validators.email]],
      username: [ '',[Validators.required, Validators.min(5)]],
      password: ['',[Validators.required]],
    })

  }

  ngOnInit(): void{}

  onSubmit(){

    this.authService.register(this.userForm.value).pipe(
      tap(response => {
       if (response.status === 200) {
          this.errorMessage = 'Usuario registrado correctamente'
        }
      }),
      catchError(error => {
        console.log('Error de registro:', error);
        this.errorMessage = error.error;
        return of(null)
      })
    ).subscribe();

  }

}
