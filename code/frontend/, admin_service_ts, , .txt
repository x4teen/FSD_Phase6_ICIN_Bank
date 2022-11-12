import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  private url: string = 'http://localhost:8080/admin';
  constructor(private httpClient: HttpClient) { }

  // Login
  public login(user:any) {
    return this.httpClient.post(`${this.url}/login`, user);
  }

  //Update block status
  public updateBlockStatus(updateBody:any) {
    return this.httpClient.post(`${this.url}/updateBlockStatus`, updateBody);
  }

  //Update deposit access
  public updateDepositAccess(updateBody:any) {
    return this.httpClient.post(`${this.url}/updateDepositAccess`, updateBody);
  }

  //Update withdrawal access
  public updateWithdrawalAccess(updateBody:any) {
    return this.httpClient.post(`${this.url}/updateWithdrawalAccess`, updateBody);
  }

  //Update transfer access
  public updateTransferAccess(updateBody:any) {
    return this.httpClient.post(`${this.url}/updateTransferAccess`, updateBody);
  }
}
