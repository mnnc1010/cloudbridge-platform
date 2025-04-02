import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Resource } from '../models/resource';

/**
 * SummaryTableComponent fetches resource data from the backend
 * and displays it in an Angular Material table.
 *
 * This component is responsible for:
 *  - Fetching data via HTTP from the endpoint `/api/resources`
 *  - Storing the data in a local variable (`dataSource`)
 *  - Displaying the data using Angular Material table with defined columns.
 */
@Component({
  selector: 'app-summary-table',
  templateUrl: './summary-table.component.html',
  styleUrls: ['./summary-table.component.css']
})
export class SummaryTableComponent implements OnInit {
  // Define the columns that will be displayed in the table.
  displayedColumns: string[] = ['fileName', 'fileType', 'fileStorage', 'dateInserted', 'dateModified'];

  // The data source for the table, initially an empty array.
  dataSource: Resource[] = [];

  /**
   * Constructor that injects HttpClient for making HTTP requests.
   *
   * @param http - Angular's HttpClient for fetching data from the backend.
   */
  constructor(private http: HttpClient) { }

  /**
   * Lifecycle hook called on component initialization.
   * It fetches resource data from the backend and assigns it to dataSource.
   */
  ngOnInit(): void {
    this.fetchResources().subscribe(
      (data: Resource[]) => this.dataSource = data,
      error => console.error('Error fetching resources:', error)
    );
  }

  /**
   * Fetches resource data from the backend.
   *
   * The backend endpoint `http://localhost:8080/api/resources` is expected to return an array of Resource objects.
   *
   * @returns An Observable that emits an array of Resource objects.
   */
  fetchResources(): Observable<Resource[]> {
    return this.http.get<Resource[]>('http://localhost:8080/api/resources');
  }
}
