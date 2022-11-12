import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AdminService } from 'src/app/services/admin.service';

@Component({
  selector: 'app-admin-login',
  templateUrl: './admin-login.component.html',
  styleUrls: ['./admin-login.component.css']
})
export class AdminLoginComponent implements OnInit {

  public login :Login = {
    email: '',
    password: ''
  };
  public submitted:boolean = false;
  public show:boolean = false;
  public loginSuccess:boolean = false;
  public loginFailedMessage : string = "";

  constructor(private adminSrv:AdminService, private router:Router) { }

  ngOnInit(): void {
  }

  public onSubmit(loginForm:any) {
    if(loginForm.valid) {
      this.submitted = true;
      this.show = true;
      console.log(this.login);
      this.logIn(this.login)
    } else{ 
      console.log("Invalid Form !");
      this.validateForm(loginForm);
    }
  }

  public validateForm(form:any){
      Object.keys(form.controls).forEach(field => {
        const control = form.controls[field];
        control.markAsTouched({ onlySelf: true});
      })
  }

  hasError(field:any){
    return (field.invalid && field.touched && field.errors);
  }

  public logIn(loginBody:Login) {
    this.adminSrv.login(loginBody).subscribe(
      response => {
        this.loginSuccess = true;
        this.router.navigate(["admin"]);
      },
      error => {
        this.loginSuccess = false;
        this.loginFailedMessage = error.error.errorMessage;
        console.log(error);
      }
    )
  }

}


interface Login {
  email:string;
  password:string;
}
