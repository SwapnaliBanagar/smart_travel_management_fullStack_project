import { Injectable } from "@angular/core";
import { NativeDateAdapter } from "@angular/material/core";
@Injectable()
export class CustomDateFormat extends NativeDateAdapter{
    override format(date: Date, displayFormat: Object): string {
    const day = ('0' + date.getDate()).slice(-2);
    const month = ('0' + (date.getMonth() + 1)).slice(-2);
    const year = date.getFullYear();
    return `${day}-${month}-${year}`; // dd-MM-yyyy
  }

  override parse(value: any): Date | null {
    if (!value) return null;
    const parts = value.split('-');
    if (parts.length === 3) {
      const day = Number(parts[0]);
      const month = Number(parts[1]) - 1;
      const year = Number(parts[2]);
      return new Date(year, month, day);
    }
    return null;
  }
}


// This class CustomDateFormat is a custom date adapter for Angular Material Datepicker. Its job is to control how dates are shown and read.

// format() – Controls how a Date object is displayed in the input field.

// Converts a Date like 2026-03-22 into "22-03-2026" (dd-MM-yyyy).

// parse() – Controls how a string from the input is converted back to a Date object.

// Converts "22-03-2026" into a Date object that Angular/JS can understand.

// Without this, Angular would use its default format (yyyy-MM-dd), which may not match your backend or your desired format.