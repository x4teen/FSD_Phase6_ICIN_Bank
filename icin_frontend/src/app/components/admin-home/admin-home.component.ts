import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AdminService } from 'src/app/services/admin.service';
import { ChequeService } from 'src/app/services/cheque.service';
import { UsersService } from 'src/app/services/users.service';

@Component({
  selector: 'app-admin-home',
  templateUrl: './admin-home.component.html',
  styleUrls: ['./admin-home.component.css']
})
export class AdminHomeComponent implements OnInit {
  chequeRequests;
  updateBlockStatusFailed : boolean = false;
  updateBlockStatusErrorMessage : string = "";
  updateAccessFailed : boolean = false;
  updateAccessErrorMessage : string = "";
  

  constructor(private router:Router, private service:AdminService, private chequeService:ChequeService) { }
  

  ngOnInit(): void {
    this.chequeService.getAllPendingRequests().subscribe(
      response => {
        this.chequeRequests = response;
      },
      error => {
        console.log(error);
      }
    )
  }

  public approveRequest(data) {
    let requestId = data.inlineFormCustomSelect;
    let approveBody = new ChequeApproveBody(requestId, "APPROVED");
    this.chequeService.approveRequest(approveBody).subscribe(
      response => {
        window.location.reload();
      },
      error => {
        console.log(error);
      }
    )
  }

  public updateBlockStatus(data) {
    let firstname = data.firstname;
    let lastname = data.lastname;
    let isBlocked = (data.blockStatus == "block") ? true : false;
    let updateBody = new StatusUpdateBody(firstname, lastname, isBlocked)
    this.service.updateBlockStatus(updateBody).subscribe(
      response => {
        window.location.reload();
      },
      error => {
        this.updateBlockStatusFailed = true;
        this.updateBlockStatusErrorMessage = error.error.errorMessage;
      }
    )
  }

  public updateAccess(data) {
    let firstname = data.firstname;
    let lastname = data.lastname;
    let accessToChange = data.inlineFormCustomSelect;
    let accessible = (data.blockStatus == "block") ? false : true;
    let updateBody = new StatusUpdateBody(firstname, lastname, accessible);
    if (accessToChange == "deposit") {
      this.service.updateDepositAccess(updateBody).subscribe(
        response => {
          window.location.reload();
        },
        error => {
          this.updateAccessFailed = true;
          this.updateAccessErrorMessage = error.error.errorMessage;
        }
      )
    }
    else if (accessToChange == "withdrawal") {
      this.service.updateWithdrawalAccess(updateBody).subscribe(
        response => {
          window.location.reload();
        },
        error => {
          this.updateAccessFailed = true;
          this.updateAccessErrorMessage = error.error.errorMessage;
        }
      )
    }
    else {
      this.service.updateTransferAccess(updateBody).subscribe(
        response => {
          window.location.reload();
        },
        error => {
          this.updateAccessFailed = true;
          this.updateAccessErrorMessage = error.error.errorMessage;
        }
      )
    }
  }

}

class ChequeApproveBody {
  id : number;
  status : string;

  constructor(id : number, status : string) {
    this.id = id;
    this.status = status;
  }
}

class StatusUpdateBody{
  firstname : string;
  lastname : string
  accessible : boolean;

  constructor(firstname : string, lastname : string, accessible : boolean) {
    this.firstname = firstname;
    this.lastname = lastname;
    this.accessible = accessible;
  }
}