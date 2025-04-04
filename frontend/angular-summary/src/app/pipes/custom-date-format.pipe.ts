import { Pipe, PipeTransform } from '@angular/core';
import { DatePipe } from '@angular/common';

/**
 * CustomDateFormatPipe transforms an ISO date string into a "MM/DD/YYYY HH.MM" format.
 *
 * Example:
 *   Input: "2025-04-04T05:48:27.283936Z"
 *   Output: "04/04/2025 05.48"
 *
 * This pipe uses Angular's DatePipe internally.
 */
@Pipe({
  name: 'customDateFormat',
  standalone: true
})
export class CustomDateFormatPipe implements PipeTransform {

  transform(value: string, ...args: any[]): string | null {
    if (!value) {
      return null;
    }
    // Create an instance of Angular's DatePipe.
    const datePipe = new DatePipe('en-US');
    // Transform the ISO date string to the desired format.
    // Angular DatePipe format tokens:
    // - "MM" for month, "dd" for day, "yyyy" for year, "HH" for hours, "mm" for minutes.
    // We want a period instead of a colon between hours and minutes, so we replace it manually.
    let formatted = datePipe.transform(value, 'MM/dd/yyyy HH:mm');
    if (formatted) {
      // Replace the colon with a period.
      formatted = formatted.replace(':', '.');
    }
    return formatted;
  }
}
