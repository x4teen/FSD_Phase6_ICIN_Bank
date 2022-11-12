import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators} from '@angular/forms';
import { Router } from '@angular/router';
import { from } from 'rxjs';
import { AccountsService } from 'src/app/services/accounts.service';
import { ChequeService } from 'src/app/services/cheque.service';
import { TransactionService } from 'src/app/services/transaction.service';

@Component({
  selector: 'app-checking-account-details',
  templateUrl: './checking-account-details.component.html',
  styleUrls: ['./checking-account-details.component.css']
})
export class CheckingAccountDetailsComponent implements OnInit {

  constructor(private router:Router, private formBuilder:FormBuilder, private service:AccountsService,  private chequeService:ChequeService, private transactionService:TransactionService) { }
  accountId;
  balance;
  userFirstname;
  saving = false;
  userId;
  depositForm : FormGroup;
  withdrawalFailed : boolean = false;
  depositFailed : boolean = false;
  withdrawalErrorMessage : string = "";
  transferFailed : boolean = false;
  transferErrorMessage : string = "";
  depositErrorMessage : string = "";
  transactionHistory;

  ngOnInit(): void {
    this.accountId = sessionStorage.getItem("checkingAccountId");
    this.userFirstname = sessionStorage.getItem("firstname");
    this.userId = sessionStorage.getItem("id");

    this.service.getAccountBalance(this.accountId).subscribe(
      response => {
        this.balance = response;
      },
      error =>{
        console.log(error);
      }
    )
    
    this.transactionService.getTransactionHistory(Number(this.accountId)).subscribe(
      response => {
        this.transactionHistory = response;
      },
      error => {
        console.log(error);
      }
    )
  }

  public makeDeposit(data) {
    let depositAmount = data.depositAmount;
    let db = new DepositBody(Number(this.userId), depositAmount, this.saving);
    this.service.deposit(db).subscribe(
      response => {
        window.location.reload();
      },
      error => {
        this.depositFailed = true;
        this.depositErrorMessage = error.error.errorMessage;
        console.log(error);
      }
    )
  }

  public makeWithdrawal(data) {
    let withdrawalAmount = data.withdrawalAmount;
    let wb = new WithdrawalBody(Number(this.userId), withdrawalAmount, this.saving);
    this.service.withdrawal(wb).subscribe(
      response => {
        window.location.reload();
      },
      error => {
        this.withdrawalFailed = true;
        console.log(error);
        this.withdrawalErrorMessage = error.error.errorMessage;
      }
    )
  }

  public makeTransfer(data) {
    let firstnameTo = data.firstnameTo;
    let lastnameTo = data.lastnameTo;
    let transferAmount = data.transferAmount;
    let toSaving = (data.accountTo == "saving") ? true : false;

    let tb = new TransferBody(Number(this.userId), firstnameTo, lastnameTo, transferAmount, this.saving, toSaving);

    this.service.transfer(tb).subscribe(
      response => {
        window.location.reload();
      },
      error => {
        this.transferFailed = true;
        console.log(error);
        this.transferErrorMessage = error.error.errorMessage;
      }
    )
  }

}

class TransferBody {
  userIdFrom : number;
  firstnameTo : string;
  lastnameTo : string;
  amount : number;
  fromSaving : boolean;
  toSaving : boolean;

  constructor(userId : number, firstnameTo : string, lastnameTo : string, amount : number, fromSaving : boolean, toSaving : boolean) {
    this.userIdFrom = userId;
    this.firstnameTo = firstnameTo;
    this.lastnameTo = lastnameTo;
    this.amount = amount;
    this.fromSaving = fromSaving;
    this.toSaving = toSaving;
  }
}

class DepositBody {
  userId : number;
  amount : number;
  saving : boolean;
  constructor(userId : number, amount : number, saving : boolean) {
    this.userId = userId;
    this.amount = amount;
    this.saving = saving;
  }
}

class WithdrawalBody {
  userId : number;
  amount : number;
  saving : boolean;
  constructor(userId : number, amount : number, saving : boolean) {
    this.userId = userId;
    this.amount = amount;
    this.saving = saving;
  }
}

