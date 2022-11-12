import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ChequeService {
  private url: string = 'http://localhost:8080/cheque';
  constructor(private httpClient: HttpClient) { }

  //Get cheque book request by userId
  public getChequeBookRequestsByUserId(userId) {
    return this.httpClient.get(`${this.url}/get/${userId}`);
  }

  //Request cheque book
  public requestChequeBook(chequeRequestBody:any) {
    return this.httpClient.post(`${this.url}/request`, chequeRequestBody);
  }

  //Get all pending requests
  public getAllPendingRequests() {
    return this.httpClient.get(`${this.url}/allPending`);
  }

  //Approve cheque book request
  public approveRequest(approveBody:any) {
    return this.httpClient.post(`${this.url}/approve`, approveBody);
  }
}
