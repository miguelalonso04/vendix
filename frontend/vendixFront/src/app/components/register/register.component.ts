import { Component } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, ReactiveFormsModule, ValidationErrors, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { catchError, of, tap } from 'rxjs';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { routes } from '../../app.routes';

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
  successMessage: string = '';

  showPassword = false;
  showConfirmPassword = false;

  passwordStrengthClass: string = '';
  passwordStrengthText: string = '';
  passwordStrengthIcon: string = '';
  hasMinLength: boolean = false;
  hasUpperCase: boolean = false;
  hasLowerCase: boolean = false;
  hasNumber: boolean = false;
  hasSpecialChar: boolean = false;

  constructor(private authService: AuthService, private fb: FormBuilder,private router: Router) {

    this.userForm = this.fb.group({
      firstName: ['',[Validators.required]],
      lastName: ['',[Validators.required]],
      telefono: ['',[Validators.required, Validators.pattern(/^[0-9]{9}$/)]],
      email: ['',[Validators.required, Validators.email]],
      username: [ '',[Validators.required, Validators.min(5)]],
      password: ['', [
        Validators.required,
        Validators.minLength(8),
        Validators.pattern(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/)
      ]],
      confirmPassword: ['', Validators.required]},
      { validators: this.passwordMatchValidator }
    );

  }

  ngOnInit(): void{}

  onSubmit(){

    this.authService.register(this.userForm.value).pipe(
      tap(response => {
       if (response.status === 200) {
          this.successMessage = 'Usuario registrado correctamente'
          this.router.navigate(['/login']);
        }
      }),
      catchError(error => {
        console.log('Error de registro:', error);
        this.errorMessage = error.error;
        return of(null)
      })
    ).subscribe();

  }

  passwordMatchValidator(control: AbstractControl): ValidationErrors | null {
  const password = control.get('password');
  const confirmPassword = control.get('confirmPassword');

  return password && confirmPassword && password.value === confirmPassword.value 
    ? null 
    : { mismatch: true };
  }

  checkPasswordStrength() {
  const password = this.userForm.get('password')?.value || '';
  
  this.hasMinLength = password.length >= 8;
  this.hasUpperCase = /[A-Z]/.test(password);
  this.hasLowerCase = /[a-z]/.test(password);
  this.hasNumber = /[0-9]/.test(password);
  this.hasSpecialChar = /[!@#$%^&*(),.?":{}|<>]/.test(password);
  
  let strength = 0;
  if (this.hasMinLength) strength++;
  if (this.hasUpperCase) strength++;
  if (this.hasLowerCase) strength++;
  if (this.hasNumber) strength++;
  if (this.hasSpecialChar) strength++;
  
  if (password.length === 0) {
    this.passwordStrengthClass = '';
    this.passwordStrengthText = '';
    this.passwordStrengthIcon = '';
  } else if (password.length < 4 || strength <= 2) {
    this.passwordStrengthClass = 'weak';
    this.passwordStrengthText = 'Débil';
    this.passwordStrengthIcon = '⚠️';
  } else if (strength <= 3) {
    this.passwordStrengthClass = 'medium';
    this.passwordStrengthText = 'Media';
    this.passwordStrengthIcon = '🔐';
  } else {
    this.passwordStrengthClass = 'strong';
    this.passwordStrengthText = 'Fuerte';
    this.passwordStrengthIcon = '🔒';
  }
}

togglePasswordVisibility() {
  this.showPassword = !this.showPassword;
}

toggleConfirmPasswordVisibility() {
  this.showConfirmPassword = !this.showConfirmPassword;
}

}
