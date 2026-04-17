import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { DateAdapter, MAT_DATE_FORMATS, MatNativeDateModule } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { Router, RouterModule } from '@angular/router';
import { CustomDateFormat } from '../../shared/custom-date-format';
import { DD_MM_YYYY_FORMATS } from '../../shared/dd-mm-yyyy-formats';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-home-component',
  standalone: true,
  imports: [
    RouterModule, FormsModule, MatInputModule,CommonModule,
    MatIconModule,
    MatFormFieldModule,
    MatButtonModule, MatDatepickerModule,
    MatNativeDateModule],
  templateUrl: './home-component.html',
  styleUrl: './home-component.css',
  providers: [
    { provide: DateAdapter, useClass: CustomDateFormat },
    { provide: MAT_DATE_FORMATS, useValue: DD_MM_YYYY_FORMATS }
  ]
})
export class HomeComponent {

  source: string = '';
  destination: string = '';
  tripDate!: Date;

  constructor(private router: Router) { }

  homeSearchButton() {

    // Format Date as dd-MM-yyyy
    const formattedDate = this.tripDate
      ? `${('0' + this.tripDate.getDate()).slice(-2)}-${('0' + (this.tripDate.getMonth() + 1)).slice(-2)}-${this.tripDate.getFullYear()}`
      : null;

    this.router.navigate(['/searchTrips'], {
      queryParams: {
        source: this.source || '',
    destination: this.destination || '',
    tripDate: formattedDate || ''
      }
    });
  }
}
