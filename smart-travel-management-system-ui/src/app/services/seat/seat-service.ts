import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { SeatDto } from '../../dto/seat-dto';

@Injectable({
  providedIn: 'root',
})
export class SeatService {
baseURL="http://localhost:8080/seats";

  constructor(private http: HttpClient) {}

  getSeats(tripId:number):Observable<SeatDto[]>{
    return this.http.get<SeatDto[]>(`${this.baseURL}/${tripId}`);
  }
}
