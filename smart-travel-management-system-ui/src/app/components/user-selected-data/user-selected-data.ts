import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TravelService } from '../../services/travel/travel-service';
import { TripDto } from '../../dto/trip-dto';
import { TravelDto } from '../../dto/travel-dto';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatSelectModule } from '@angular/material/select';
import { FormsModule } from '@angular/forms';
import { PaypalService } from '../../services/paypal/paypal-service';

@Component({
  selector: 'app-user-selected-data',
  imports: [CommonModule, MatCardModule, MatFormFieldModule,
    MatInputModule, MatIconModule, MatSelectModule, FormsModule],
  templateUrl: './user-selected-data.html',
  styleUrl: './user-selected-data.css',
})
export class UserSelectedData implements OnInit {
  tripDto: TripDto = {} as TripDto;
  travelDto: TravelDto = {} as TravelDto;

  selectedSeats: any[] = [];
  boardingPoint: any;
  droppingPoint: any;
  totalAmount: number = 0;
  stopsArray: any[] = [];
  filteredStops: any[] = [];

  contactDetails = {
    mobile: '',
    email: ''
  };


  submitted = false;
  isLocked = false;

  arrivalDate: Date = new Date();

  // paypal
  // orderId: string;
  // approveUrl: string;

  constructor(private route: ActivatedRoute, private travelService: TravelService, private cd: ChangeDetectorRef, private router: Router, private paypalService: PaypalService) { }


  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {

      this.tripDto.tripId = params['tripId'];


      // 1. Manually parse the DD-MM-YYYY string into a Date object
      const dateStr = params['tripDate']; // "19-03-2026"
      const [day, month, year] = dateStr.split('-').map(Number);
      this.tripDto.tripDate = new Date(year, month - 1, day);

      // 2. Safely get the time strings from your parsed objects
      this.boardingPoint = JSON.parse(params['boardingPoint'] || '{}');
      this.droppingPoint = JSON.parse(params['droppingPoint'] || '{}');

      const depTimeStr = this.boardingPoint.time || '00:00';
      const arrTimeStr = this.droppingPoint.time || '00:00';

      const depHour = parseInt(depTimeStr.split(':')[0]);
      const arrHour = parseInt(arrTimeStr.split(':')[0]);

      // 3. Initialize arrivalDate correctly using the valid tripDate
      this.arrivalDate = new Date(this.tripDto.tripDate);

      // 4. If arrival hour is numerically less than departure (e.g., 05 < 16), add 1 day
      if (arrHour < depHour) {
        this.arrivalDate.setDate(this.arrivalDate.getDate() + 1);
      }





      // When parsing seats, map them to include the passenger fields
      const rawSeats = JSON.parse(params['selectedSeats'] || '[]');
      this.selectedSeats = rawSeats.map((seat: any) => ({
        ...seat, // keeps existing seatNumber
        passengerName: '',
        age: '',
        gender: ''
      }));


      // this.selectedSeats = JSON.parse(params['selectedSeats'] || '[]');
      this.boardingPoint = JSON.parse(params['boardingPoint'] || '{}');
      this.droppingPoint = JSON.parse(params['droppingPoint'] || '{}');
      this.totalAmount = +params['totalAmount'];

      const stopsObj = JSON.parse(params['stops'] || '{}');
      this.stopsArray = Object.entries(stopsObj).map(([place, time]) => ({
        place,
        time: time as string
      }));

      this.stopsArray.sort((a, b) => {
        const getMinutes = (timeStr: string) => {
          const [h, m] = timeStr.split(':').map(Number);
          let total = h * 60 + m;

          if (total < 16 * 60) total += 1440; // next day logic
          return total;
        };
        return getMinutes(a.time) - getMinutes(b.time);
      });

      // filtered stops
      this.filterStopsBetweenPoints();

      // Fetch travel details by tripId
      this.getTravelsDetails(this.tripDto.tripId);
    });
  }
  // --------------------------------------------------------------------

  isSleeper(seatNumber: string): boolean {
    return seatNumber.startsWith('SL') || seatNumber.startsWith('SU');
  }

  // ----------------------------------------------------------------------
  filterStopsBetweenPoints() {

    const boardingIndex = this.stopsArray.findIndex(
      s => s.place === this.boardingPoint.place
    );

    const droppingIndex = this.stopsArray.findIndex(
      s => s.place === this.droppingPoint.place
    );

    if (boardingIndex !== -1 && droppingIndex !== -1) {
      this.filteredStops = this.stopsArray.slice(
        boardingIndex,
        droppingIndex + 1   // include dropping point
      );
    }

    console.log("Filtered Stops:", this.filteredStops);
  }


  // ----------------------------------------------------------------------

  getTravelsDetails(tripId: number): void {
    this.travelService.getTravelDetailsByTripId(tripId).subscribe({
      next: (data) => {
        this.travelDto = data;  // assign response to travelDto
        this.cd.detectChanges();
        console.log("Travel Details:", this.travelDto);
      },
      error: (err) => {
        console.error("Error fetching travel details", err);
      }
    });
  }



  // ----------------------------------------------------------------------------

 
  onContinueToPayment() {

    this.submitted = true;

    const finalBookingData = {
      trip: this.tripDto,
      contact: this.contactDetails,
      passengers: this.selectedSeats
    };

    // Validation
    if (!this.contactDetails.mobile || this.selectedSeats.some(s => !s.passengerName || !s.gender)) {
      alert("Please fill in all required details!");
      return;
    }

    console.log("Final Data to Save:", finalBookingData);

    // ✅ Lock form
    this.isLocked = true;

    //Call PayPal only if validation passed
    console.log("Total Amount:", this.totalAmount);
    const seatIds: number[] = this.selectedSeats.map(s => s.seatId);
    console.log("Selected Seats:", seatIds);

    this.paypalService.createOrder(this.totalAmount, seatIds).subscribe(res => {
      console.log("Order Id:" + res.orderId);
      console.log("Approve URL:" + res.approveUrl);
      // Redirect to PayPal approval page
      // window.location.href = res.approveUrl; ---- Payment Page

      this.router.navigate(['/payment/successPage'],
        {
          queryParams: {
            token: res.orderId,
            tripId: this.tripDto.tripId,
            selectedSeats: JSON.stringify(this.selectedSeats)
          }
        });
    },
      (err) => {
        console.error("Error creating PayPal order:", err);
        this.router.navigate(['/payment/failedPage']);
      }
    );
  }

}





