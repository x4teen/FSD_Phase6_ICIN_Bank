import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AccountComponent } from './components/account/account.component';
import { AdminHomeComponent } from './components/admin-home/admin-home.component';
import { AdminLoginComponent } from './components/admin-login/admin-login.component';
import { CheckingAccountDetailsComponent } from './components/checking-account-details/checking-account-details.component';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { SavingAccountDetailsComponent } from './components/saving-account-details/saving-account-details.component';
const routes: Routes = [
  { path:"", redirectTo:'/home', pathMatch:'full'},
  { path:"home", component:HomeComponent},
  { path:"login", component:LoginComponent},
  { path:"accounts", component:AccountComponent},
  { path:"register", component:RegisterComponent},
  { path:"saving", component:SavingAccountDetailsComponent},
  { path:"checking", component:CheckingAccountDetailsComponent},
  { path: "adminLogin", component:AdminLoginComponent},
  { path:"admin", component:AdminHomeComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
