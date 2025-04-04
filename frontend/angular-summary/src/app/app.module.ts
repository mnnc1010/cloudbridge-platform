import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatTableModule } from '@angular/material/table';

import { AppComponent } from './app.component';
import { SummaryTableComponent } from './summary-table/summary-table.component';
import { CustomDateFormatPipe } from './pipes/custom-date-format.pipe';


@NgModule({
  declarations: [
    AppComponent,
    SummaryTableComponent,
    CustomDateFormatPipe
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatTableModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
