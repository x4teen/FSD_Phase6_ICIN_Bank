import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AccountsService } from 'src/app/services/accounts.service';
import { ChequeService } from 'src/app/services/cheque.service';


@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.css']
})
export class AccountComponent implements OnInit {

  constructor(private router:Router, private service:AccountsService, private chequeService:ChequeService) { }
  checkingAccountBalance;
  savingAccountBalance;
  userFirstname;
  userId;
  requests;

  ngOnInit(): void {

    this.userFirstname = sessionStorage.getItem("firstname");
    this.userId = sessionStorage.getItem("id");
    
    var checkingAccountId = parseInt(this.userId.toString() + "01");
    var savingAccountId = parseInt(this.userId.toString() + "02");

    this.savingAccountBalance = sessionStorage.getItem("savingAccountBalance")
    this.service.getAccountBalance(checkingAccountId).subscribe(
      response => {
        this.checkingAccountBalance = response;
        sessionStorage.setItem("checkingAccountId", checkingAccountId.toString());
      },
      error =>{
        console.log(error);
      }
    )
    this.service.getAccountBalance(savingAccountId).subscribe(
      response => {
        this.savingAccountBalance = response;
        sessionStorage.setItem("savingAccountId", savingAccountId.toString());
      },
      error =>{
        console.log(error);
      }
    )
    
    this.chequeService.getChequeBookRequestsByUserId(this.userId).subscribe(
      response => {
        this.requests = response;
      },
      error => {
        console.log(error);
      }
    )
  }

  public viewCheckingAccountDetails() {
    this.router.navigate(["checking"]);
  }

  public viewSavingAccountDetails() {
    this.router.navigate(["saving"]);
  }

  public requestChequeBook(data) {
    let quantity = data.quantity;
    let accountFor = data.accountFor;
    let toSaving = (data.accountFoor == "saving") ? true : false;
    let cheque = new RequestChequeBookBody(Number(this.userId), quantity, toSaving);
    this.chequeService.requestChequeBook(cheque).subscribe(
      response => {
        window.location.reload();
      },
      error => {
        console.log(error);
      }
    )
  }
}

class RequestChequeBookBody {
  userId : number;
  quantity : number;
  saving : boolean;

  constructor(userId : number, quantity : number, saving : boolean) {
    this.userId = userId;
    this.quantity = quantity;
    this.saving = saving;
  }
}