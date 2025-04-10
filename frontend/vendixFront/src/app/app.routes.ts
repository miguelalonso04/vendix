import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { HomeComponent } from './components/home/home.component';
import { CategoriaProductosComponent } from './components/categoria-productos/categoria-productos.component';
import { ProductosFormComponent } from './components/productos-form/productos-form.component';
import { ViewProductoComponent } from './components/view-producto/view-producto.component';
import { CestaComponent } from './components/cesta/cesta.component';

export const routes: Routes = [
    { path: '', component: LoginComponent },
    { path: 'login', component: LoginComponent },
    { path: 'register', component: RegisterComponent },
    { path: 'home', component: HomeComponent },
    { path: 'categoria/productos', component: CategoriaProductosComponent},
    { path: 'productos/form', component: ProductosFormComponent},
    { path: 'productos/producto', component: ViewProductoComponent},
    { path: 'usuario/cesta', component: CestaComponent},
    { path: '**', redirectTo: '/login' }
];
