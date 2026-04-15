import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PaypalService } from '../../../services/paypal/paypal-service';
import { TripDto } from '../../../dto/trip-dto';
import { SeatService } from '../../../services/seat/seat-service';
import { TravelService } from '../../../services/travel/travel-service';  // ✅ ADD THIS
import { TravelDto } from '../../../dto/travel-dto';
import { TripScheduleService } from '../../../services/trip/trip-schedule-service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-success-page',
  imports: [CommonModule],
  templateUrl: './success-page.html',
  styleUrl: './success-page.css',
})
export class SuccessPage implements OnInit {

  paymentStatus: 'pending' | 'success' | 'failed' = 'pending';
  orderId: string = '';

  selectedSeats: any[] = [];
  tripId!: number;

  travelDto: TravelDto = {} as TravelDto;
  tripDto: TripDto = {} as TripDto;



  constructor(
    private route: ActivatedRoute,
    private paypalService: PaypalService,
    private router: Router,
    private seatService: SeatService,
    private travelService: TravelService,
      private cd: ChangeDetectorRef,private tripScheduleService:TripScheduleService
  ) { }

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      const orderId = params['token'];  // PayPal returns token for order
      this.orderId = orderId;

      console.log("SuccessPage called:" + this.orderId)

      this.tripId = +params['tripId'];

      this.selectedSeats = JSON.parse(params['selectedSeats'] || '[]');

      

      console.log("TripId:", this.tripId);
      console.log("Selected Seats:", this.selectedSeats);

      // fetch travel Details                
      //this.getTravelsDetails(this.tripId);

      this.getTripDetails(this.tripId);
      

      if (orderId) {
        this.paypalService.captureOrder(orderId).subscribe({
          next: res => {
            console.log('Payment captured:', res);
            this.paymentStatus = 'success';
          },
          error: err => {
            console.error('Capture failed', err);
            this.paymentStatus = 'failed';
            this.router.navigate(['/payment/failedPage']); // navigate only if failed
          }
        });
      } else {
        // No token returned, treat as failed
        this.paymentStatus = 'failed';
        this.router.navigate(['/payment/failedPage']);
      }

    });
  }


  // -------------------------------------------------------

  

  //-------------------------------------------------------
  

  getTripDetails(tripId:number)
  {
              this.tripScheduleService.getTripDetailsByTripId(tripId).subscribe({
                   next:(data)=>{
                    this.tripDto=data; //store data
                    console.log("Trip Data On Success Page:", data);
                    this.cd.detectChanges();
                   },error: (err) => {
      console.error("Error fetching trip details:", err);
    }
              });
  }

//----------------------  get time basis of source and destination
getSourceTime(): string {
  return this.tripDto?.stops?.[this.tripDto?.source] || '';
}

getDestinationTime(): string {
  return this.tripDto?.stops?.[this.tripDto?.destination] || '';
}


}
