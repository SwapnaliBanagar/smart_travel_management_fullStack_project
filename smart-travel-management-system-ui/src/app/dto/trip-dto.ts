export class TripDto {
  tripId!: number;
  tripDate!: Date;
  source!: string;
  destination!: string;
  seaterSeatPrice!: number;
  sleeperSeatPrice!: number;
  totalBookedSeats!: number;
  travelNumber!: string;
  stops!: { [key: string]: string };

  travel!: {
    travelId: number;
    travelName: string;
    travelNumber: string;
    busType: string;
    seatType: string;
    totalSeats: number;
  };
}
