import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AccountsService {

  private url: string = 'http://localhost:8080/accounts';
  constructor(private httpClient: HttpClient) { }

  //Get balance
  public getAccountBalance(accountId:number) {
    return this.httpClient.get(`${this.url}/balance/${accountId}`);
  }

  public deposit(depositBody:any) {
    return this.httpClient.post(`${this.url}/deposit`, depositBody);
  }

  public withdrawal(withdrawalBody:any) {
    return this.httpClient.post(`${this.url}/withdrawal`, withdrawalBody);
  }

  public transfer(transferBody:any) {
    return this.httpClient.post(`${this.url}/transfer`, transferBody);
  }
  
}
