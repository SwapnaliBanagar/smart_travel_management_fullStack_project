import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';

@Injectable({
  providedIn: 'root',
})
export class PaypalService {
  
  private baseUrl = 'http://localhost:8080/paypal';

  constructor(private httpClient: HttpClient) {}

  // ✅ Create PayPal Order (Get Approval URL)
  createOrder(amount: number, seatIds: number[]) {
    return this.httpClient.post<any>(
      `${this.baseUrl}/create-order`,
      {
        amount: amount,
      seatIds: seatIds
      },
     
    );
  }

// ✅ Capture(track payment details) PayPal Order 
  captureOrder(orderId: string) {
  return this.httpClient.post(
    `${this.baseUrl}/capture-order?orderId=${orderId}`,
    {},
    { responseType: 'text' as 'json' }
  );
}

}
