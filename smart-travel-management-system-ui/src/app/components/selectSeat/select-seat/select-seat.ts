import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { TripDto } from '../../../dto/trip-dto';
import { ActivatedRoute, Router } from '@angular/router';
import { TravelService } from '../../../services/travel/travel-service';
import { TravelDto } from '../../../dto/travel-dto';
import { CommonModule } from '@angular/common';
import { SeatService } from '../../../services/seat/seat-service';
import { SeatDto } from '../../../dto/seat-dto';
import { FormsModule } from '@angular/forms';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-select-seat',
  imports: [CommonModule, FormsModule],
  templateUrl: './select-seat.html',
  styleUrl: './select-seat.css',
})
export class SelectSeat implements OnInit {
  // Trip info
  tripDto: TripDto = {} as TripDto; // to store selected trip details
  totalBookedSeats: number = 0;
  totalSeats: number = 0;

  // Travel info
  travelDto: TravelDto = {} as TravelDto;
  // All seats
  seatDto: SeatDto[] = [];

  // Seat groups
  lowerSeaterCount: SeatDto[] = [];
  lowerSleeperCount: SeatDto[] = [];
  upperSeaterCount: SeatDto[] = [];
  upperSleeperCount: SeatDto[] = [];

  columnCount: number = 1;
  columnCountUpper: number = 1;

  //select seat
  // 1. Array to hold multiple selected seats
  selectedSeats: any[] = [];

  // 2. Function to handle selection logic
  toggleSeat(seat: any) {
    // If the seat is already booked, do nothing
    if (seat.status === 'BOOKED') return;

    // Check if seat is already selected
    const index = this.selectedSeats.findIndex(s => s.seatNumber === seat.seatNumber);

    if (index !== -1) {
      // If already selected, remove it (Deselect)
      this.selectedSeats.splice(index, 1);
    } else {
      // Otherwise, add it to the list
      this.selectedSeats.push(seat);
    }
  }
  // 3. Helper to check if a seat is in the selected list (for CSS)
  isSeatSelected(seat: any): boolean {
    return this.selectedSeats.some(s => s.seatNumber === seat.seatNumber);
  }

  // 4. Calculate total price
  getTotalPrice() {
    let totalAmount = 0;

    this.selectedSeats.forEach(seat => {
      if (seat.seatNumber.startsWith('SL') || seat.seatNumber.startsWith('SU')) {
        // Sleeper
        totalAmount += this.tripDto.sleeperSeatPrice;
      } else {
        // Seater (L, U)
        totalAmount += this.tripDto.seaterSeatPrice;
      }
    });

    return totalAmount;
  }




  // Points
  activeTab: string = 'boarding';

  boardingPoints: any = {};
  droppingPoints: any = {};

  setActive(tab: string) {
    this.activeTab = tab;
  }


  // Stops
  stopsArray: any[] = [];

  // select stops
  selectedBoardingPoint: any = null;
  selectedroppingPoint: any = null;


  onSelectBoardingPoint(stop: any) {
    this.selectedBoardingPoint = stop;
    console.log("Selected Boarding Point:", this.selectedBoardingPoint);
  }
  onselectedroppingPoint(stop: any) {
    this.selectedroppingPoint = stop;
    console.log("Selected Dropping Point:", this.selectedroppingPoint);
  }

  // bus full
  isBusFull: boolean = false;

  constructor(private route: ActivatedRoute, private travelService: TravelService, private cd: ChangeDetectorRef, private seatService: SeatService, private router: Router) { }


  ngOnInit(): void {

// ✅ Close popup on browser back button
    window.addEventListener('popstate', () => {
      Swal.close();
    });

    this.route.queryParams.subscribe(params => {
      this.tripDto.tripId = +params['tripId'];
      this.tripDto.seaterSeatPrice = +params['seaterSeatPrice'];
      this.tripDto.sleeperSeatPrice = +params['sleeperSeatPrice'];
      this.tripDto.totalBookedSeats = +params['totalBookedSeats'];
      this.tripDto.source = params['source'];
      this.tripDto.destination = params['destination'];
      this.tripDto.tripDate = params['tripDate'];



      // 1. Convert the object to an array
      this.tripDto.stops = JSON.parse(params['stops']);

      this.stopsArray = Object.entries(this.tripDto.stops).map(([place, time]) => ({
        place,
        time: time as string
      }));

      // 2. Sort starting from started time
      this.stopsArray.sort((a, b) => {
        const getMinutes = (timeStr: string) => {
          const [hours, minutes] = timeStr.split(':').map(Number);
          let totalMinutes = hours * 60 + minutes;

          // Logic: If time is earlier than 16:00 (like 02:00 or 05:00), 
          // it belongs to the next day. We add 24 hours (1440 mins).
          if (totalMinutes < 16 * 60) {
            totalMinutes += 24 * 60;
          }
          return totalMinutes;
        };

        return getMinutes(a.time) - getMinutes(b.time);
      });


      //console.log("Trip Details on SelectSeat page:", this.tripDto);

      // Fetch travel details by tripId
      this.getTravelsDetails(this.tripDto.tripId);

      // seat details
      this.getSeatDetails(this.tripDto.tripId);
    });
  }


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


  getSeatDetails(tripId: number): void {
    this.seatService.getSeats(tripId).subscribe({
      next: (data) => {
        this.seatDto = data;

        console.log("Seat Details:", this.seatDto);

        // ✅ TOTAL SEATS (from array OR travel)
        this.totalSeats = this.seatDto.length;

        // ✅ TOTAL BOOKED SEATS
        this.totalBookedSeats = this.seatDto.filter(seat => seat.status === 'BOOKED').length;

        console.log("Total Seats:", this.totalSeats);
        console.log("Total Booked Seats:", this.totalBookedSeats);

        if ((this.totalSeats) === (this.totalBookedSeats)) {
          this.isBusFull = true;
          Swal.fire({
            icon: 'error',
            title: 'No Seat Available',
            text: 'All seats are already booked.Please select different travels(bus).',
            confirmButtonText: 'OK',
            allowOutsideClick: false,
            allowEscapeKey: false
          }).then(() => {
            this.router.navigate(['/searchTrips']);
          });
        }

        // 👉 OPTIONAL: store in tripDto
        this.tripDto.totalBookedSeats = this.totalBookedSeats;
        //console.log("Total Booked seats after save in tripDto:",this.tripDto.totalBookedSeats)

        this.lowerSleeperCount =
          this.seatDto.filter(seat => seat.seatNumber.startsWith('SL'));

        this.upperSleeperCount =
          this.seatDto.filter(seat => seat.seatNumber.startsWith('SU'));

        this.lowerSeaterCount =
          this.seatDto.filter(seat =>
            seat.seatNumber.startsWith('L') && !seat.seatNumber.startsWith('SL')
          );

        this.upperSeaterCount =
          this.seatDto.filter(seat =>
            seat.seatNumber.startsWith('U') && !seat.seatNumber.startsWith('SU')
          );

        // 🔥 calculate here rows for Lower
        if (this.lowerSleeperCount.length === 0) {
          this.columnCount = Math.ceil(this.lowerSeaterCount.length / 3);
        }
        else {
          this.columnCount = Math.ceil(this.lowerSeaterCount.length / 2);
        }

        // console.log("Lower Column Count:", this.columnCount);

        // 🔥 calculate here rows for Upper
        if (this.upperSeaterCount.length === 0) {
          this.columnCountUpper = Math.ceil(this.upperSleeperCount.length / 3);
        } else {
          this.columnCountUpper = Math.ceil(this.upperSleeperCount.length / 2);
        }
        //console.log("Upper Column Count:", this.columnCountUpper)


        this.cd.detectChanges();
        // console.log("lowerSeaterCount", this.lowerSeaterCount);
      },
      error: (err) => {
        console.error("Error fetching travel details", err);
      }
    });
  }

// ------------------------------------------------------------

backButtonClick()
{
   console.log("select Seat back button working....");
   this.router.navigate(['/searchTrips']);
}








  continueToBooking() {
    console.log("continueToBooking button clicked....");
    this.router.navigate(['/showUserSelectedData'], {
      queryParams: {
        tripId: this.tripDto.tripId,
        seaterSeatPrice: this.tripDto.seaterSeatPrice,
        sleeperSeatPrice: this.tripDto.sleeperSeatPrice,
        source: this.tripDto.source,
        destination: this.tripDto.destination,
        tripDate: this.tripDto.tripDate,

        // ✅ important data
        selectedSeats: JSON.stringify(this.selectedSeats),
        boardingPoint: JSON.stringify(this.selectedBoardingPoint),
        droppingPoint: JSON.stringify(this.selectedroppingPoint),
        totalAmount: this.getTotalPrice(),

        stops: JSON.stringify(this.tripDto.stops)
      }
    });
    console.log("Selected seat total amount:" + this.getTotalPrice())
  }

}







