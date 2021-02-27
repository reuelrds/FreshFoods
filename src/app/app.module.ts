import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SignupComponent } from './views/auth/signup/signup.component';
import { LoginComponent } from './views/auth/login/login.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ReactiveFormsModule } from '@angular/forms';

import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { ProfileComponent } from './views/account/profile/profile.component';
import { NgxMaskModule } from 'ngx-mask';
import { NavbarComponent } from './views/shared/navbar/navbar.component';

import { MatTooltipModule } from '@angular/material/tooltip';
import { StoreComponent } from './views/store/store.component';
import { MatNativeDateModule, MatRippleModule } from '@angular/material/core';
import { CartComponent } from './views/cart/cart.component';
import { MatBadgeModule } from '@angular/material/badge';
import { RecipeComponent } from './views/recipe/recipe.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatMenuModule } from '@angular/material/menu';
import { AuthInterceptor } from './core/auth.interceptor';
import { OrderComponent } from './views/order/order.component';
import { MatStepperModule } from '@angular/material/stepper';
import { AddressComponent } from './views/order/address/address.component';
import { STEPPER_GLOBAL_OPTIONS } from '@angular/cdk/stepper';
import { OptionsComponent } from './views/order/options/options.component';
import { MatRadioModule } from '@angular/material/radio';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { PaymentComponent } from './views/order/payment/payment.component';
import { DetailsComponent } from './views/order/details/details.component';
import { MatListModule } from '@angular/material/list';
import { AngularRaveModule } from 'angular-rave';
import { environment } from 'src/environments/environment';
import { OrderDetailsComponent } from './views/account/order-details/order-details.component';
import { ScrollingModule } from '@angular/cdk/scrolling';

@NgModule({
  declarations: [
    AppComponent,
    SignupComponent,
    LoginComponent,
    ProfileComponent,
    NavbarComponent,
    StoreComponent,
    CartComponent,
    RecipeComponent,
    OrderComponent,
    AddressComponent,
    OptionsComponent,
    PaymentComponent,
    DetailsComponent,
    OrderDetailsComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatButtonModule,
    MatTooltipModule,
    MatBadgeModule,
    MatRippleModule,
    MatProgressSpinnerModule,
    MatStepperModule,
    MatMenuModule,
    MatRadioModule,
    MatListModule,
    MatDatepickerModule,
    MatNativeDateModule,
    ScrollingModule,
    NgxMaskModule.forRoot(),
    AngularRaveModule.forRoot(environment.RAVE_API_KEY),
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
    {
      provide: STEPPER_GLOBAL_OPTIONS,
      useValue: { displayDefaultIndicatorType: false },
    },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
