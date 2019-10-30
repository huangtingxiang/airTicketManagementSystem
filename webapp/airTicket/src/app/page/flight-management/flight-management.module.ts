import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {FlightManagementRoutingModule} from './flight-management-routing.module';
import {FlightManagementIndexComponent} from './flight-management-index/flight-management-index.component';
import {FlightManagementAddComponent} from './flight-management-add/flight-management-add.component';
import {ShareModule} from '../../share/share.module';


@NgModule({
  declarations: [FlightManagementIndexComponent, FlightManagementAddComponent],
  imports: [
    CommonModule,
    ShareModule,
    FlightManagementRoutingModule
  ]
})
export class FlightManagementModule {
}
