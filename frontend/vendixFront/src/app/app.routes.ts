import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { HomeComponent } from './components/home/home.component';
import { ProductosFormComponent } from './components/productos-form/productos-form.component';
import { ViewProductoComponent } from './components/view-producto/view-producto.component';
import { CestaComponent } from './components/cesta/cesta.component';
import { ProductosComponent } from './components/productos/productos.component';
import { PedidosComponent } from './components/pedidos/pedidos.component';
import { LayoutComponent } from './components/layout/layout.component';
import { AdministracionComponent } from './components/administracion/administracion.component';
import { ValoracionComponent } from './components/valoracion/valoracion.component';
import { ValoracionFormComponent } from './components/valoracion-form/valoracion-form.component';

export const routes: Routes = [
  // Rutas sin layout
  { path: '', component: LoginComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },

  // Rutas con layout (todo lo que debe verse con el header/nav/aside)
  {
    path: 'home',
    component: LayoutComponent,
    children: [
      { path: '', component: ProductosComponent },
      { path: 'productos/form', component: ProductosFormComponent },
      { path: 'productos/producto', component: ViewProductoComponent },
      { path: 'productos', component: ProductosComponent },
      { path: 'pedidos', component: PedidosComponent },
      { path: 'administracion', component: AdministracionComponent},
      { path: 'productos/valoracion', component: ValoracionComponent },
      { path: 'productos/valoracion/form', component: ValoracionFormComponent },
    ]
  },
  {
    path: 'usuario',
    component: LayoutComponent,
    children: [
      { path: 'cesta', component: CestaComponent }
    ]
  },

  // Redirección de rutas no encontradas
  { path: '**', redirectTo: '/login' }
];
