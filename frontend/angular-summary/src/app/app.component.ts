import { Component } from '@angular/core';
import { SummaryTableComponent } from './summary-table/summary-table.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [SummaryTableComponent],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent { }
