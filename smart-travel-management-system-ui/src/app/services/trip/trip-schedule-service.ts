import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { TripDto } from '../../dto/trip-dto';

@Injectable({
  providedIn: 'root',
})
export class TripScheduleService {

  baseURL="http://localhost:8080/trip/";

  constructor(private httpClient:HttpClient){}

   searchTrips(source?:string,destination?:string,tripDate?:string):Observable<TripDto[]>
   {
    let params = new HttpParams();

    if (source) {
      params = params.set('source', source);
    }

    if (destination) {
      params = params.set('destination', destination);
    }
    if (tripDate) {
      params = params.set('tripDate', tripDate);
    }
    return this.httpClient.get<TripDto[]>(
      this.baseURL + "by-location",
      { params }
    );
   } 
  


   getTripDetailsByTripId(tripId:number)
   {
  return this.httpClient.get<TripDto>(`${this.baseURL}getTripDetailsByTripId/${tripId}`);
   }
}
