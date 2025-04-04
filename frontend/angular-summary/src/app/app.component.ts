import { Component } from '@angular/core';
import { CustomDateFormatPipe } from './pipes/custom-date-format.pipe';
import { SummaryTableComponent } from './summary-table/summary-table.component';
import { HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [HttpClientModule, SummaryTableComponent, CustomDateFormatPipe],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'CloudBridge Platform';
}
