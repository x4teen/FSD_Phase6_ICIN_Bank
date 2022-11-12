import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class TransactionService {

  private url: string = 'http://localhost:8080/transactions';
  constructor(private httpClient: HttpClient) { }

  //Get transaction history
  public getTransactionHistory(accountId:number) {
    return this.httpClient.get(`${this.url}/getHistory/${accountId}`);
  }
}
