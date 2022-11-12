import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';
import { UsersService } from 'src/app/services/users.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  public registerForm: FormGroup;
  public submitted: boolean = false;
  public show:boolean = false;
  public success:boolean = false;

  constructor(private fromBuilder: FormBuilder, private userSrv: UsersService) {
    this.registerForm = this.fromBuilder.group({
      firstname: ['', [Validators.required, Validators.minLength(3),Validators.maxLength(15)]],
      lastname: ['',[Validators.required, Validators.minLength(3),Validators.maxLength(15)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6),Validators.maxLength(15)]],
      username: ['', [Validators.required, Validators.minLength(3),Validators.maxLength(15)]]
    });
  }

  ngOnInit(): void {
  }

  public onSubmit(loginForm: any) {
    if (loginForm.valid) {
      this.submitted = true;
      this.show = true;
      this.success = true;
      console.log(this.registerForm.value);
      this.register(this.registerForm.value);
    } else {
      console.log("Invalid Form !");
      this.validateForm(loginForm);
    }
  }

  public validateForm(form: any) {
    Object.keys(form.controls).forEach(field => {
      const control = form.controls[field];
      if (control instanceof FormControl) {
        control.markAsTouched({ onlySelf: true });
      } else{
        this.validateForm(control)
      }
    })
  }

  public register(registerBody:any) {
    this.userSrv.addUser(registerBody).subscribe(
      (res) => {
        console.log(res);
      },
      (error) => {
        console.log(error);
      }
    )
  }

  hasError(field: any) {
    return (this.registerForm.get(field)?.invalid && this.registerForm.get(field)?.touched && this.registerForm.get(field)?.errors);
  }
  get f() {
    return this.registerForm.controls;
  }
}
