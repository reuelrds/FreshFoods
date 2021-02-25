import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { ProfileComponent } from './views/account/profile/profile.component';
import { SignupComponent } from './views/auth/signup/signup.component';
import { CartComponent } from './views/cart/cart.component';
import { RecipeComponent } from './views/recipe/recipe.component';
import { StoreComponent } from './views/store/store.component';

const routes: Routes = [
  { path: 'signup', component: SignupComponent },
  { path: 'store', component: StoreComponent },
  { path: 'recipes', component: RecipeComponent },
  { path: 'cart', component: CartComponent },
  { path: 'profile', component: ProfileComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
