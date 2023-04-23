import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './core/auth.guard';
import { OrderDetailsComponent } from './views/account/order-details/order-details.component';

import { ProfileComponent } from './views/account/profile/profile.component';
import { LoginComponent } from './views/auth/login/login.component';
import { SignupComponent } from './views/auth/signup/signup.component';
import { CartComponent } from './views/cart/cart.component';
import { OrderComponent } from './views/order/order.component';
import { RecipeComponent } from './views/recipe/recipe.component';
import { StoreComponent } from './views/store/store.component';

const routes: Routes = [
  { path: '', component: LoginComponent },
  { path: 'signup', component: SignupComponent },
  { path: 'store', component: StoreComponent, canActivate: [AuthGuard] },
  { path: 'recipes', component: RecipeComponent, canActivate: [AuthGuard] },
  { path: 'cart', component: CartComponent, canActivate: [AuthGuard] },
  { path: 'profile', component: ProfileComponent, canActivate: [AuthGuard] },
  { path: 'order', component: OrderComponent, canActivate: [AuthGuard] },
  {
    path: 'orders',
    component: OrderDetailsComponent,
    canActivate: [AuthGuard],
  },
  { path: 'login', redirectTo: '', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
