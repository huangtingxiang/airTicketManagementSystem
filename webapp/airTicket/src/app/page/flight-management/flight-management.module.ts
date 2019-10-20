import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { FlightManagementRoutingModule } from './flight-management-routing.module';
import { FlightManagementIndexComponent } from './flight-management-index/flight-management-index.component';


@NgModule({
  declarations: [FlightManagementIndexComponent],
  imports: [
    CommonModule,
    FlightManagementRoutingModule
  ]
})
export class FlightManagementModule { }
