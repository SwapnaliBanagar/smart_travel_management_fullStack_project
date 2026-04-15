import { ChangeDetectorRef, Component, NgModule, OnInit } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { TripDto } from '../../../dto/trip-dto';
import { ActivatedRoute, Router } from '@angular/router';
import { TripScheduleService } from '../../../services/trip/trip-schedule-service';
import { MatCardModule } from '@angular/material/card';
import { MatIcon } from '@angular/material/icon';
import { FormsModule } from '@angular/forms';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { CommonModule } from '@angular/common';
import Swal from 'sweetalert2';
import { SeatDto } from '../../../dto/seat-dto';
import { SeatService } from '../../../services/seat/seat-service';
@Component({
  selector: 'app-search-trip',
  standalone: true,
  imports: [MatCardModule, MatButtonModule, MatFormFieldModule,
    MatInputModule, FormsModule, MatDatepickerModule, CommonModule,
    MatNativeDateModule],
  templateUrl: './search-trip.html',
  styleUrl: './search-trip.css',
})
export class SearchTrip implements OnInit {

  source: string = '';
  destination: string = '';
  tripDate?: string;

  trips: TripDto[] = [];

  constructor(private router: Router, private route: ActivatedRoute, private cdr: ChangeDetectorRef, private tripService: TripScheduleService) { }

  ngOnInit() {
  

    this.route.queryParams.subscribe(params => {

      this.source = params['source'] || '';
      this.destination = params['destination'] || '';
      this.tripDate = params['tripDate'] || '';

      console.log("Params:", this.source, this.destination, this.tripDate);
      this.searchTripSchedule();
    });
  }


  searchTripSchedule() {

    this.tripService
      .searchTrips(this.source, this.destination, this.tripDate)
      .subscribe((data: TripDto[]) => {

        console.log("Trip Dto:", data);
        if (!data || data.length === 0) {

          this.trips = [];

          Swal.fire({
            icon: 'info',
            title: 'No Buses Available',
            text: 'Please select different route or date.',
            confirmButtonText: 'OK',
          });

        } else {

          this.trips = data;

        }
        this.cdr.detectChanges();

      },
        (error) => {
          console.error("Error:", error);
        });
  }


  // -----------------------------------------------------------

  openSelectSeatPage(trip: TripDto) {
    console.log("select seat button clicked on search-trip.ts page.");
    this.router.navigate(["/selectSeat"]);

    // Pass trip id or relevant data as query params
    this.router.navigate(['/selectSeat'], {
      queryParams: {
        tripId: trip.tripId,
        seaterSeatPrice: trip.seaterSeatPrice,
        sleeperSeatPrice: trip.sleeperSeatPrice,
        totalBookedSeats: trip.totalBookedSeats,
        source: trip.source,
        destination: trip.destination,
        tripDate: trip.tripDate,
        stops: JSON.stringify(trip.stops)   // ✅ convert to string
      }
    });
  }
}
