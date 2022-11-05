import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormGroup, FormBuilder } from '@angular/forms';
import { AccountsService } from 'src/app/services/accounts.service';
import { ChequeService } from 'src/app/services/cheque.service';
import { TransactionService } from 'src/app/services/transaction.service';

@Component({
  selector: 'app-saving-account-details',
  templateUrl: './saving-account-details.component.html',
  styleUrls: ['./saving-account-details.component.css']
})
export class SavingAccountDetailsComponent implements OnInit {

  constructor(private router:Router, private formBuilder:FormBuilder, private service:AccountsService,  private chequeService:ChequeService, private transactionService:TransactionService) { }

  accountId;
  balance;
  userFirstname;
  saving = true;
  userId;
  depositForm : FormGroup;
  withdrawalFailed : boolean = false;
  withdrawalErrorMessage : string = "";
  transferFailed : boolean = false;
  transferErrorMessage : string = "";
  transactionHistory;

  ngOnInit(): void {
    this.accountId = sessionStorage.getItem("savingAccountId");
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
