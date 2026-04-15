import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { TravelDto } from '../../dto/travel-dto';

@Injectable({
  providedIn: 'root',
})
export class TravelService {
  
  baseURL="http://localhost:8080/travel";
  
 constructor(private http: HttpClient) {}

 getTravelDetailsByTripId(tripId: number): Observable<TravelDto> {
    return this.http.get<TravelDto>(`${this.baseURL}/getTravelByTripId/${tripId}`);
  }
}
