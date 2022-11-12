import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UsersService } from 'src/app/services/users.service';


export class User {
  constructor( 
    private id:number,
    private firstname:String,
    private lastname:String,
    private email:String,
    private username:String,
    private password:String,
    private blocked:boolean,
    private transferAccess:boolean,
    private depositAccess:boolean,
    private withdrawalAccess:boolean) 
    { }
 
}

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  public login :Login = {
    email: '',
    password: ''
  };
  public submitted:boolean = false;
  public show:boolean = false;
  public loginSuccess:boolean = false;
  public loginFailedMessage : string = "";

  constructor(private userSrv:UsersService, private router:Router) { }

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
    
    this.userSrv.login(loginBody).subscribe(
      response => {
        console.log(response),
        sessionStorage.removeItem("password");
        sessionStorage.setItem("id", response["id"]);
        sessionStorage.setItem("firstname", response["firstname"]);
        sessionStorage.setItem("lastname", response["lastname"]);
        sessionStorage.setItem("email", response["email"]);
        sessionStorage.setItem("username", response["username"]);
        sessionStorage.setItem("blocked", response["blocked"]);
        sessionStorage.setItem("transferAccess", response["transferAccess"]);
        sessionStorage.setItem("depositAccess", response["depositAccess"]);
        sessionStorage.setItem("withdrawalAccess", response["withdrawalAccess"]);
        console.log("Json object is ")
        sessionStorage.setItem("userObj",JSON.stringify(response));
        this.loginSuccess = true;
        this.router.navigate(["accounts"]);
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
